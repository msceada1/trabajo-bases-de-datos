package org.example;

import org.example.exceptions.AppException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Venta {

    private static final Pattern PATRON_MATRICULA = Pattern.compile("^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$");
    private static final Pattern PATRON_FECHA = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[-/](0[1-9]|1[0-2])[-/](19[0-9]{2}|20[0-1][0-9]|202[0-5]|2026)$");
    private static final List<String> FORMAS_PAGO = List.of("Efectivo", "Transferencia", "Bizum", "A plazos");

    private int codigoVenta;
    private LocalDate fechaVenta;
    private String matricula;
    private String formaPago;
    private int idCliente;

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

        if (fechaVenta == null || !PATRON_FECHA.matcher(fechaVenta.toString()).matches()) {
            throw new AppException("Fallo en el registro de fecha, sigue el patron dd/mm/yyyy");
        }
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
            throw new AppException("La forma de pago debe ser una de las siguientes: Efectivo, Transferencia, Bizum. A plazos");
        }
        this.formaPago = formaPago;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
                ", idCliente='" + idCliente + '\'' +
                '}';
    }
}
