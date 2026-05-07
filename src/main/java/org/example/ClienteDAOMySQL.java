package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMySQL implements ClienteDAO {
    @Override
    public void insertar(Cliente c) {
        String sql = "INSERT INTO cliente (nombre, apellido1, apellido2, edad) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Rellenamos los interrogantes con los getters del cliente
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido1());
            ps.setString(3, c.getApellido2());
            ps.setInt(4, c.getEdad());

            ps.executeUpdate();
            System.out.println("Cliente insertado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al insertar el cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente obtenerPorNombreCompleto(String nombre, String apellido1, String apellido2) {
        Cliente cliente = null;
        // El SQL es el mismo: queremos todas las columnas (*) de los que coincidan con el nombre
        String sql = "SELECT * FROM cliente WHERE nombre = ? AND apellido1 = ? AND apellido2 = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido1);
            ps.setString(3, apellido2);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 1. Creamos el objeto con el constructor que ya tienes (sin ID)
                    cliente = new Cliente(
                            rs.getString("nombre"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2"),
                            rs.getInt("edad")
                    );

                    // 2. USAMOS EL NUEVO SETTER:
                    // Ahora recuperamos el ID de la columna "idCliente" y se lo asignamos al objeto
                    cliente.setIdCliente(rs.getInt("idCliente"));
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
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getInt("edad")
                );

                c.setIdCliente(rs.getInt("idCliente"));

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
        String sql = "UPDATE cliente SET nombre = ?, apellido1 = ?, apellido2 = ?, edad = ? WHERE idCliente = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido1());
            ps.setString(3, c.getApellido2());
            ps.setInt(4, c.getEdad());
            ps.setInt(5, c.getIdCliente());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPorNombreCompleto(String nombre, String apellido1, String apellido2) {

    }
}
