import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static Connection conexion = null;

    // Privado para evitar instanciación externa (Patrón Singleton)
    private ConexionDB() {}

    /**
     * Devuelve una conexión activa a la base de datos.
     */
    public static Connection getConnection() throws Exception {
        PropertiesReader p = PropertiesReader.getInstance();
        String url = "jdbc:mysql://localhost:3306/tienda_vehiculos?serverTimezone=UTC";

        // Si la conexión no existe o está cerrada, creamos una nueva
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(url, p.get("usuario"), p.get("password"));
        }
        return conexion;
    }
}