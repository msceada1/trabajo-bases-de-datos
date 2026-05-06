package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();
    private TableView<Vehiculo> tabla = new TableView<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("🚙 Gestor de Vehículos Pro - MiColección");

        // --- 1. CONFIGURACIÓN DE LA TABLA ---
        configurarTabla();

        // --- 2. BARRA DE BÚSQUEDA (SUPERIOR) ---
        HBox barraBusqueda = new HBox(15);
        barraBusqueda.setPadding(new Insets(15));
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);
        // Aplicamos la clase CSS para el degradado oscuro
        barraBusqueda.getStyleClass().add("header-bar");

        Label lblBuscar = new Label("🔍 Filtros:");
        TextField busquedaMarca = new TextField();
        busquedaMarca.setPromptText("Por marca...");

        TextField busquedaPrecio = new TextField();
        busquedaPrecio.setPromptText("Precio máx...");

        Button btnBuscar = new Button("Filtrar");

        barraBusqueda.getChildren().addAll(lblBuscar, busquedaMarca, busquedaPrecio, btnBuscar);

        // --- 3. FORMULARIO DE ALTA (LATERAL DERECHO) ---
        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        formulario.setPrefWidth(300);
        // Aplicamos la clase CSS para el panel lateral con borde rosa
        formulario.getStyleClass().add("form-panel");

        Label tituloForm = new Label("AÑADIR VEHÍCULO");
        tituloForm.setStyle("-fx-font-size: 18px; -fx-text-fill: #e94560;"); // Color acento rosa

        TextField txtMatricula = new TextField(); txtMatricula.setPromptText("Matrícula");
        TextField txtMarca = new TextField(); txtMarca.setPromptText("Marca");
        TextField txtModelo = new TextField(); txtModelo.setPromptText("Modelo");
        TextField txtPrecio = new TextField(); txtPrecio.setPromptText("Precio (€)");

        Button btnGuardar = new Button("➕ Guardar Vehículo");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        // Aplicamos la clase CSS para el botón rosa con degradado
        btnGuardar.getStyleClass().add("button-save");

        btnGuardar.setOnAction(e -> {
            // Requisito Fase 3: Aquí llamarás al DAO
            System.out.println("Intentando guardar: " + txtMatricula.getText());
        });

        formulario.getChildren().addAll(tituloForm, new Separator(), txtMatricula, txtMarca, txtModelo, txtPrecio, btnGuardar);

        // --- 4. DISEÑO FINAL (LAYOUT) ---
        BorderPane root = new BorderPane();
        root.setTop(barraBusqueda);
        root.setCenter(new StackPane(tabla)); // El StackPane añade un poco de "aire" alrededor
        root.setRight(formulario);

        // --- 5. CARGA DE ESCENA Y CSS ---
        Scene scene = new Scene(root, 1200, 700);

        try {
            // IMPORTANTE: Verifica si tu archivo es "style.css" o "styles.css"
            String rutaCss = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(rutaCss);
        } catch (Exception e) {
            System.out.println("❌ Error: No se pudo cargar el archivo CSS. Revisa el nombre en /resources.");
        }

        stage.setScene(scene);
        stage.show();
    }

    private void configurarTabla() {
        TableColumn<Vehiculo, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Vehiculo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Vehiculo, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tabla.getColumns().addAll(colMarca, colModelo, colMatricula, colPrecio);
        // Hace que las columnas ocupen todo el ancho disponible proporcionalmente
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setItems(listaVehiculos);
    }

    public static void main(String[] args) {
        launch(args);
    }
}