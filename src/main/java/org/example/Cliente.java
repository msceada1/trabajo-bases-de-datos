package org.example;

import java.util.Objects;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int edad;

    public Cliente(String nombre, String apellido1, String apellido2, int edad) {
        setNombre(nombre);
        setApellido1(apellido1);
        setApellido2(apellido2);
        setEdad(edad);
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        if (apellido1 == null || apellido1.isBlank()) {
            throw new IllegalArgumentException("El primer apellido es obligatorio.");
        }
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        if (apellido2 == null || apellido2.isBlank()) {
            throw new IllegalArgumentException("El segundo apellido es obligatorio");
        }
    }

    public void setEdad(int edad) {
        if (edad < 16) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad (16+).");
        }
        this.edad = edad;
    }


    public int getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getApellido1() { return apellido1; }
    public String getApellido2() { return apellido2; }
    public int getEdad() { return edad; }

    @Override
    public String toString() {
        return "Cliente{" + "id='" + idCliente + "', nombre='" + nombre + "', edad=" + edad + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(idCliente, cliente.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCliente);
    }
}