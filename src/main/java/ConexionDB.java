import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/tienda_vehiculos?serverTimezone=UTC";
        String usuario, password;

        PropertiesReader p = PropertiesReader.getInstance();

        // La conexión se cerrará automáticamente al salir del try
        try (Connection conexion = DriverManager.getConnection(url, p.get("usuario"), p.get("password"))) {

            System.out.println("¡Conexión establecida con éxito! (se cerrará automáticamente)");
            // ... usar la conexión ...

        } catch (SQLException e) {
            System.err.println("Error durante la operación con la base de datos:");
            e.printStackTrace();
        } // No se necesita finally para cerrar 'conexion'

    }
}