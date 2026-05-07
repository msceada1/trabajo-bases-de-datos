package org.example;

import com.mysql.cj.util.DnsSrv;
import org.example.exceptions.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Vehiculo {

    private static final Pattern PATRON_MATRICULA = Pattern.compile("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$");

    private String matricula;
    private double precio;
    private String marca;
    private String modelo;
    private double velocidadMax;

    public Vehiculo(String matricula, double precio, String marca, String modelo, double velocidadMax) throws AppException {
        setMatricula(matricula);
        setPrecio(precio);
        setMarca(marca);
        setModelo(modelo);
        setVelocidadMax(velocidadMax);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) throws AppException {
        if (matricula == null || matricula.isBlank() || !PATRON_MATRICULA.matcher(matricula.trim().toUpperCase()).matches()) {
            throw new AppException("ERROR: La matricula debe tener 4 numeros y tres letras consonantes");
        }
        this.matricula = matricula;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) throws AppException {
        if (precio <= 0) {
            throw new AppException("ERROR: El vehiculo debe tener un precio mayor a cero");
        }
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) throws AppException {
        if (marca == null || marca.isBlank()) {
            throw new AppException("ERROR: El vehiculo debe tener marca");
        }
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) throws AppException {
        if (modelo == null || modelo.isBlank()) {
            throw new AppException("ERROR: El behiculo debe tener un modelo");
        }
        this.modelo = modelo;
    }

    public double getVelocidadMax() {
        return velocidadMax;
    }

    public void setVelocidadMax(double velocidadMax) throws AppException {
        if (velocidadMax < 30) {
            throw new AppException("ERROR: El vehiculo debe tener una velocidad minima de 30");
        }
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

}
