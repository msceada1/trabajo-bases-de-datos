package org.example;

import com.mysql.cj.util.DnsSrv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo implements VehiculoDAO {

    private String matricula;
    private double precio;
    private String marca;
    private String modelo;
    private double velocidadMax;

    public Vehiculo(String matricula, double precio, String marca, String modelo, double velocidadMax) {
        this.matricula = matricula;
        this.precio = precio;
        this.marca = marca;
        this.modelo = modelo;
        this.velocidadMax = velocidadMax;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getVelocidadMax() {
        return velocidadMax;
    }

    public void setVelocidadMax(double velocidadMax) {
        this.velocidadMax = velocidadMax;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Vehiculo vehiculo)) return false;

        return matricula.equals(vehiculo.matricula);
    }

    @Override
    public int hashCode() {
        return matricula.hashCode();
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "matricula='" + matricula + '\'' +
                ", precio=" + precio +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", velocidadMax=" + velocidadMax +
                '}';
    }

    @Override
    public void insertar(Vehiculo v) {
        String sql = "INSERT INTO vehiculo (matricula, precio, marca, modelo, velocidadMax) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setString(1, v.getMatricula());
            ps.setDouble(2, v.getPrecio());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setDouble(5, v.getVelocidadMax());

            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al insertar el vehiculo " + e.getMessage());
        }
    }

    @Override
    public Vehiculo obtenerPorMatricula(String matricula) {
        Vehiculo v = null;
        String sql = "SELECT FROM Vehiculo WHERE matricula = ?";

        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);) {

            try (ResultSet rs = ps.executeQuery()) {
                //el metodo next verifica si encontro el vehiculo
                if (rs.next()) {
                    v = new Vehiculo(
                            rs.getString("matricula"),
                            rs.getDouble("precio"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getDouble("velocidadMax")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return v;
    }

    @Override
    public List<Vehiculo> listarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";

        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo(
                        rs.getString("matricula"),
                        rs.getDouble("precio"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getDouble("velocidadMax")
                );
                lista.add(v);
            }
        } catch (Exception e) {
            System.err.println("Error al listar los vehículos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculo SET precio = ?, marca = ?, modelo = ?, velocidadMax = ? WHERE matricula = ?";

        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, vehiculo.getPrecio());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setDouble(4, vehiculo.getVelocidadMax());
            ps.setString(5, vehiculo.getMatricula());

            ps.executeUpdate();
            System.out.println("Vehículo con matrícula " + vehiculo.getMatricula() + " actualizado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al actualizar el vehículo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String matricula) {
        String sql = "DELETE FROM vehiculo WHERE matricula = ?";

        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, matricula);

            ps.executeUpdate();
            System.out.println("Vehículo eliminado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar el vehículo: " + e.getMessage());
        }
    }
}
