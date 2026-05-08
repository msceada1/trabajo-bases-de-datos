package org.example;

import org.example.exceptions.AppException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Venta {

    private static final Pattern PATRON_MATRICULA = Pattern.compile("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$");
    // Se ha eliminado PATRON_FECHA porque usar LocalDate ya hace que sea imposible tener fechas inválidas (como 32/13/2026)
    private static final List<String> FORMAS_PAGO = List.of("Efectivo", "Transferencia", "Bizum", "A plazos");

    private int codigoVenta;
    private LocalDate fechaVenta;
    private String matricula;
    private String formaPago;
    private int id_cliente;

    public Venta(LocalDate fechaVenta, String matricula, String formaPago) throws AppException {
        setFechaVenta(fechaVenta);
        setMatricula(matricula);
        setFormaPago(formaPago);
    }

    public int getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(int codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) throws AppException {
        // Solo necesitamos comprobar que la fecha no venga nula
        if (fechaVenta == null) {
            throw new AppException("ERROR: La fecha de venta no puede estar vacía.");
        }

        // Opcional: Descomenta estas líneas si quieres evitar que registren ventas en el futuro
        /*
        if (fechaVenta.isAfter(LocalDate.now())) {
            throw new AppException("ERROR: La fecha de venta no puede ser en el futuro.");
        }
        */

        this.fechaVenta = fechaVenta;
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

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) throws AppException {
        if (!FORMAS_PAGO.contains(formaPago)) {
            throw new AppException("La forma de pago debe ser una de las siguientes: Efectivo, Transferencia, Bizum, A plazos");
        }
        this.formaPago = formaPago;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Venta venta)) return false;
        return Objects.equals(codigoVenta, venta.codigoVenta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoVenta);
    }

    @Override
    public String toString() {
        return "Venta{" +
                "codigoVenta='" + codigoVenta + '\'' +
                ", fechaVenta=" + fechaVenta +
                ", matricula='" + matricula + '\'' +
                ", formaPago='" + formaPago + '\'' +
                ", id_cliente='" + id_cliente + '\'' +
                '}';
    }
}