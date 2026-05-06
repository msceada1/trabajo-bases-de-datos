import java.time.LocalDate;
import java.util.Objects;

public class Venta {

    private String codigoVenta;
    private LocalDate fechaVenta;
    private String matricula;
    private String formaPago;
    private String idCliente;

    public Venta(String codigoVenta, LocalDate fechaVenta, String matricula, String formaPago, String idCliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.matricula = matricula;
        this.formaPago = formaPago;
        this.idCliente = idCliente;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
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
