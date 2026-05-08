package org.example;

import org.example.exceptions.AppException;

import java.util.Objects;

public class Cliente {

    private int id_cliente;
    private String nombre;
    private String apellido_1;
    private String apellido_2;
    private int edad;

    public Cliente(String nombre, String apellido_1, String apellido_2, int edad) throws AppException {
        setNombre(nombre);
        setApellido_1(apellido_1);
        setApellido_2(apellido_2);
        setEdad(edad);
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setNombre(String nombre) throws AppException {
        if (nombre == null || nombre.isBlank()) {
            throw new AppException("ERROR: El cliente debe tener un nombre");
        }
        this.nombre = nombre;
    }

    public String getApellido_1() {
        return apellido_1;
    }

    public void setApellido_1(String apellido_1) throws AppException {
        if (apellido_1 == null || apellido_1.isBlank()) {
            throw new AppException("ERROR: El cliente debe tener un primer apellido");
        }
        this.apellido_1 = apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public void setApellido_2(String apellido_2) throws AppException {
        if (apellido_2 == null || apellido_2.isBlank()) {
            throw new AppException("ERROR: El cliente debe tener un segundo apellido");
        }
        this.apellido_2 = apellido_2;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) throws AppException {
        if (edad < 16) {
            throw new AppException("ERROR: La edad debe ser mayor o igual a 16");
        }
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id_cliente='" + id_cliente + '\'' + ", nombre='" + nombre + '\'' + ", apellido1='" + apellido_1 + '\'' + ", apellido2='" + apellido_2 + '\'' + ", edad=" + edad + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(id_cliente, cliente.id_cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_cliente);
    }
}
