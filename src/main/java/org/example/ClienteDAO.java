package org.example;

import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente c);

    Cliente obtenerPorNombreCompleto(String nombre, String apellido1, String apellido2);

    List<Cliente> obtenerClientes();

    void actualizar(Cliente c);

    void eliminarPorNombreCompleto(String nombre, String apellido1, String apellido2);
}
