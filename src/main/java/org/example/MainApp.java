package org.example;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class MainApp extends Application {

    // Lista observable que reacciona a cambios en la interfaz
    private ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();
    private TableView<Vehiculo> tabla = new TableView<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gestor Personal de Vehículos - MiColección");

        // --- 1. TABLA (Requisito: TableView) ---
        TableColumn<Vehiculo, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Vehiculo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Vehiculo, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tabla.getColumns().addAll(colMatricula, colMarca, colModelo, colPrecio);
        tabla.setItems(listaVehiculos);

        // --- 2. FORMULARIO DE ALTA (Requisito: Formulario de alta/edición) ---
        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(15));
        formulario.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");

        TextField txtMatricula = new TextField(); txtMatricula.setPromptText("Matrícula");
        TextField txtMarca = new TextField(); txtMarca.setPromptText("Marca");
        TextField txtModelo = new TextField(); txtModelo.setPromptText("Modelo");
        TextField txtPrecio = new TextField(); txtPrecio.setPromptText("Precio");
        Button btnGuardar = new Button("Añadir Vehículo");

        btnGuardar.setOnAction(e -> {
            // Aquí llamarás a tu VehiculoDAOMysql.insertar() en la Fase 3
            System.out.println("Guardando: " + txtMatricula.getText());
        });

        formulario.getChildren().addAll(new Label("Nuevo Vehículo"), txtMatricula, txtMarca, txtModelo, txtPrecio, btnGuardar);

        // --- 3. BARRA DE BÚSQUEDA (Requisito: Mínimo 2 campos) ---
        HBox barraBusqueda = new HBox(10);
        barraBusqueda.setPadding(new Insets(10));
        TextField busquedaMarca = new TextField(); busquedaMarca.setPromptText("Buscar por marca...");
        TextField busquedaPrecio = new TextField(); busquedaPrecio.setPromptText("Precio máximo...");
        Button btnBuscar = new Button("🔍 Filtrar");

        barraBusqueda.getChildren().addAll(busquedaMarca, busquedaPrecio, btnBuscar);

        // --- LAYOUT FINAL ---
        BorderPane root = new BorderPane();
        root.setTop(barraBusqueda);
        root.setCenter(tabla);
        root.setRight(formulario);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}