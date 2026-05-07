package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOMySQL implements VentaDAO {

    @Override
    public void insertar(Venta v) {
        String sql = "INSERT INTO venta (fecha_venta, matricula, forma_pago, id_cliente) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(v.getFechaVenta()));
            ps.setString(2, v.getMatricula());
            ps.setString(3, v.getFormaPago());
            ps.setInt(4, v.getIdCliente());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al insertar la venta: " + e.getMessage());
        }
    }

    @Override
    public Venta obtenerPorFormaDePago(String formaDePago) {
        Venta venta = null;
        String sql = "SELECT * FROM venta WHERE forma_pago = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, formaDePago);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    venta = new Venta(
                            rs.getDate("fecha_venta").toLocalDate(),
                            rs.getString("matricula"),
                            rs.getString("forma_pago")
                    );

                    venta.setCodigoVenta(rs.getInt("codigo_venta"));
                    venta.setIdCliente(rs.getInt("id_cliente"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la venta por forma de pago: " + e.getMessage());
        }

        return venta;
    }

    @Override
    public List<Venta> obtenerVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        String sql = "SELECT * FROM venta";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta(
                        rs.getDate("fecha_venta").toLocalDate(),
                        rs.getString("matricula"),
                        rs.getString("forma_pago")
                );

                v.setCodigoVenta(rs.getInt("codigo_venta"));
                v.setIdCliente(rs.getInt("id_cliente"));

                listaVentas.add(v);
            }
        } catch (Exception e) {
            System.err.println("Error al listar las ventas: " + e.getMessage());
        }

        return listaVentas;
    }

    @Override
    public void actualizar(Venta v) {
        String sql = "UPDATE venta SET fecha_venta = ?, matricula = ?, forma_pago = ?, id_cliente = ? WHERE codigo_venta = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(v.getFechaVenta()));
            ps.setString(2, v.getMatricula());
            ps.setString(3, v.getFormaPago());
            ps.setInt(4, v.getIdCliente());

            ps.setInt(5, v.getCodigoVenta());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al actualizar la venta: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPorFecha(LocalDate fechaDeVenta) {
        String sql = "DELETE FROM venta WHERE fecha_venta = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(fechaDeVenta));

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al eliminar ventas por fecha: " + e.getMessage());
        }
    }
}
