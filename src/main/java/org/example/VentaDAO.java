package org.example;

import java.time.LocalDate;
import java.util.List;

public interface VentaDAO {
    void insertar(Venta v);

    Venta obtenerPorFormaDePago(String formaDePago);

    List<Venta> obtenerVentas();

    void actualizar(Venta v);

    void eliminarPorFecha(LocalDate fechaDeVenta);
}
