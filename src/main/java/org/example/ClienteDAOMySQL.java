package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMySQL implements ClienteDAO {
    @Override
    public void insertar(Cliente c) {
        String sql = "INSERT INTO cliente (nombre, apellido_1, apellido_2, edad) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Rellenamos los interrogantes con los getters del cliente
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido_1());
            ps.setString(3, c.getApellido_2());
            ps.setInt(4, c.getEdad());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al insertar el cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente obtenerPorNombreCompleto(String nombre, String apellido_1, String apellido_2) {
        Cliente cliente = null;
        // El SQL es el mismo: queremos todas las columnas (*) de los que coincidan con el nombre
        String sql = "SELECT * FROM cliente WHERE nombre = ? AND apellido_1 = ? AND apellido_2 = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido_1);
            ps.setString(3, apellido_2);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 1. Creamos el objeto con el constructor que ya tienes (sin ID)
                    cliente = new Cliente(
                            rs.getString("nombre"),
                            rs.getString("apellido_1"),
                            rs.getString("apellido_2"),
                            rs.getInt("edad")
                    );

                    // 2. USAMOS EL NUEVO SETTER:
                    // Ahora recuperamos el ID de la columna id_iente" y se lo asignamos al objeto
                    cliente.setId_cliente(rs.getInt("id_cliente"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
        }
        return cliente;
    }

    @Override
    public List<Cliente> obtenerClientes() {
        // Creamos la lista vacía donde guardaremos todos los clientes
        List<Cliente> lista = new ArrayList<>();
        // La consulta no lleva WHERE porque queremos traerlos a todos
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // El bucle se repetirá por cada cliente (fila) que haya en la tabla
            while (rs.next()) {
                // 1. Creamos el cliente con los datos básicos
                Cliente c = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido_1"),
                        rs.getString("apellido_2"),
                        rs.getInt("edad")
                );

                c.setId_cliente(rs.getInt("id_cliente"));

                // 3. Lo añadimos a nuestra lista de Java
                lista.add(c);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE cliente SET nombre = ?, apellido_1 = ?, apellido_2 = ?, edad = ? WHERE id_cliente = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido_1());
            ps.setString(3, c.getApellido_2());
            ps.setInt(4, c.getEdad());
            ps.setInt(5, c.getId_cliente());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPorNombreCompleto(String nombre, String apellido_1, String apellido_2) {
        // 1. Obtenemos el cliente (asegúrate de que en este método el SQL use 'apellido_1' y 'apellido_2')
        Cliente cliente = obtenerPorNombreCompleto(nombre, apellido_1, apellido_2);

        if (cliente == null) {
            return;
        }

        int id_cliente = cliente.getId_cliente();

        // Consultas SQL con los nombres EXACTOS de tu imagen
        String sqlSelectMatriculas = "SELECT matricula FROM venta WHERE id_cliente = ?";
        String sqlDeleteVentas = "DELETE FROM venta WHERE id_cliente = ?";
        String sqlDeleteVehiculos = "DELETE FROM vehiculo WHERE matricula = ?";
        String sqlDeleteCliente = "DELETE FROM cliente WHERE id_cliente = ?";

        Connection conn = null;

        try {
            conn = ConexionDB.getConnection();

            // ¡Iniciamos la transacción!
            conn.setAutoCommit(false);

            // PASO 1: Rescatar las matrículas ANTES de borrar las ventas
            List<String> matriculasAEliminar = new ArrayList<>();
            try (PreparedStatement psSelect = conn.prepareStatement(sqlSelectMatriculas)) {
                psSelect.setInt(1, id_cliente);
                try (ResultSet rs = psSelect.executeQuery()) {
                    while (rs.next()) {
                        // Guardamos las matrículas en la lista de Java
                        matriculasAEliminar.add(rs.getString("matricula"));
                    }
                }
            }

            // PASO 2: Borramos las VENTAS (Liberamos las claves foráneas)
            try (PreparedStatement psVenta = conn.prepareStatement(sqlDeleteVentas)) {
                psVenta.setInt(1, id_cliente);
                psVenta.executeUpdate();
            }

            // PASO 3: Borramos los VEHÍCULOS usando la lista que guardamos antes
            if (!matriculasAEliminar.isEmpty()) {
                try (PreparedStatement psVehiculo = conn.prepareStatement(sqlDeleteVehiculos)) {
                    for (String matricula : matriculasAEliminar) {
                        psVehiculo.setString(1, matricula);
                        // addBatch() agrupa varios DELETE para ejecutarlos de golpe de forma eficiente
                        psVehiculo.addBatch();
                    }
                    psVehiculo.executeBatch(); // Ejecutamos todos los DELETE de vehículos
                }
            }

            // PASO 4: Borramos el CLIENTE
            try (PreparedStatement psCliente = conn.prepareStatement(sqlDeleteCliente)) {
                psCliente.setInt(1, id_cliente);
                psCliente.executeUpdate();
            }

            // SI LLEGAMOS AQUÍ, TODO HA IDO PERFECTO. Confirmamos en la base de datos.
            conn.commit();

        } catch (Exception e) {

            // SI ALGO FALLA, DESHACEMOS TODO
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al intentar hacer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();

        } finally {
            // Restauramos el autoCommit y cerramos la conexión
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                }
            }
        }
    }
}
