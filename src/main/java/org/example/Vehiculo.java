package org.example;

import java.util.regex.Pattern;

public class Vehiculo {

    private String matricula;
    private double precio;
    private String marca;
    private String modelo;
    private double velocidadMax;

    public Vehiculo(String matricula, double precio, String marca, String modelo, double velocidadMax) {
        setMatricula(matricula);
        setPrecio(precio);
        setMarca(marca);
        setModelo(modelo);
        setVelocidadMax(velocidadMax);
    }

    public void setMatricula(String matricula) {
        Pattern patronMatricula = Pattern.compile("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$");


        if (matricula == null || !patronMatricula.matcher(matricula.toUpperCase()).matches()) {
            throw new IllegalArgumentException("La matrícula tiene que tener este patrón:(4 números y 3 consonantes)");
        }
        this.matricula = matricula.toUpperCase();
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser un valor positivo.");
        }
        this.precio = precio;
    }

    public void setVelocidadMax(double velocidadMax) {
        if (velocidadMax < 45 || velocidadMax > 500) {
            throw new IllegalArgumentException("La velocidad máxima debe estar entre 45 y 500 km/h.");
        }
        this.velocidadMax = velocidadMax;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("La marca es obligatoria.");
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("El modelo es obligatorio.");
        this.modelo = modelo;
    }

    public String getMatricula() { return matricula; }
    public double getPrecio() { return precio; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getVelocidadMax() { return velocidadMax; }

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
        return "Vehiculo{" + "matricula='" + matricula + "', marca='" + marca + "', velocidadMax=" + velocidadMax + '}';
    }
}