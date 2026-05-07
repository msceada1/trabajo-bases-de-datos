package org.example;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class Venta {

    private String codigoVenta;
    private LocalDate fechaVenta;
    private String matricula;
    private String formaPago;
    private String idCliente;

    // Definimos el patrón de matrícula como constante para reutilizarlo
    private static final Pattern PATRON_MATRICULA = Pattern.compile("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$");

    public Venta(String codigoVenta, LocalDate fechaVenta, String matricula, String formaPago, String idCliente) {
        setCodigoVenta(codigoVenta);
        setFechaVenta(fechaVenta);
        setMatricula(matricula);
        setFormaPago(formaPago);
        setIdCliente(idCliente);
    }

    public void setCodigoVenta(String codigoVenta) {
        if (codigoVenta == null || codigoVenta.isBlank()) {
            throw new IllegalArgumentException("El código de venta no puede estar vacío.");
        }
        this.codigoVenta = codigoVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        if (fechaVenta == null || fechaVenta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de venta no puede ser nula ni posterior a hoy.");
        }
        this.fechaVenta = fechaVenta;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || !PATRON_MATRICULA.matcher(matricula.toUpperCase()).matches()) {
            throw new IllegalArgumentException("La matrícula no tiene el patron valido:(4 números y 3 consonantes).");
        }
        this.matricula = matricula.toUpperCase();
    }

    public void setFormaPago(String formaPago) {
        if (formaPago == null) throw new IllegalArgumentException("La forma de pago es obligatoria.");

        String pago = formaPago.trim().toLowerCase();
        if (!(pago.equals("efectivo") || pago.equals("tarjeta") || pago.equals("transferencia"))) {
            throw new IllegalArgumentException("Forma de pago no válida. Use: Efectivo, Tarjeta o Transferencia.");
        }
        this.formaPago = pago;
    }

    public void setIdCliente(String idCliente) {
        if (idCliente == null || idCliente.isBlank()) {
            throw new IllegalArgumentException("El ID del cliente es obligatorio para registrar la venta.");
        }
        this.idCliente = idCliente;
    }

    public String getCodigoVenta() { return codigoVenta; }
    public LocalDate getFechaVenta() { return fechaVenta; }
    public String getMatricula() { return matricula; }
    public String getFormaPago() { return formaPago; }
    public String getIdCliente() { return idCliente; }

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
                "codigo='" + codigoVenta + '\'' +
                ", fecha=" + fechaVenta +
                ", matricula='" + matricula + '\'' +
                ", pago='" + formaPago + '\'' +
                ", cliente='" + idCliente + '\'' +
                '}';
    }
}