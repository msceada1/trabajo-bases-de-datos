package org.example;

public class Vehiculo {

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
        return "org.example.Vehiculo{" +
                "matricula='" + matricula + '\'' +
                ", precio=" + precio +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", velocidadMax=" + velocidadMax +
                '}';
    }
}
