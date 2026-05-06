import java.util.List;

public interface VehiculoDAO {

    void insertar(Vehiculo vehiculo);
    Vehiculo obtenerPorMatricula(String matricula);
    List<Vehiculo> listarVehiculos();
    void actualizar(Vehiculo vehiculo);
    void eliminar(String matricula);
}
