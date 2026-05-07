package org.example;

import org.example.exceptions.AppException;

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

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            try {
                throw new AppException("ERROR: El cliente debe tener un nombre");
            } catch (AppException e) {
                System.err.println(e.getMessage());
            }
        }
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        if (apellido1 == null || apellido1.isBlank()) {
            try {
                throw new AppException("ERROR: El cliente debe tener un primer apellido");
            } catch (AppException e) {
                System.err.println(e.getMessage());
            }
        }
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        if (apellido2 == null || apellido2.isBlank()) {
            try {
                throw new AppException("ERROR: El cliente debe tener un segundo apellido");
            } catch (AppException e) {
                System.err.println(e.getMessage());
            }
        }
        this.apellido2 = apellido2;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 16) {
            try {
                throw new AppException("ERROR: La edad debe ser mayor o igual a 16");
            } catch (AppException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente='" + idCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", edad=" + edad +
                '}';
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
