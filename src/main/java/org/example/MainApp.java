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
import org.example.exceptions.AppException;

import java.time.LocalDate;

public class MainApp extends Application {

    // Instancias de los DAOs para interactuar con la Base de Datos
    private final VehiculoDAO vehiculoDAO = new VehiculoDAOMySQL();
    private final ClienteDAO clienteDAO = new ClienteDAOMySQL();
    private final VentaDAO ventaDAO = new VentaDAOMySQL();

    @Override
    public void start(Stage stage) {
        stage.setTitle("🚙 Gestor de Concesionario Pro");

        // Contenedor principal con pestañas
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Crear las pestañas
        Tab tabVehiculos = new Tab("🚗 Vehículos", crearPanelVehiculos());
        Tab tabClientes = new Tab("👥 Clientes", crearPanelClientes());
        Tab tabVentas = new Tab("🤝 Ventas", crearPanelVentas());

        tabPane.getTabs().addAll(tabVehiculos, tabClientes, tabVentas);

        Scene scene = new Scene(tabPane, 1200, 700);

        try {
            String rutaCss = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(rutaCss);
        } catch (Exception e) {
            System.out.println("ℹ️ Aviso: No se encontró styles.css. Se usará el estilo por defecto.");
        }

        stage.setScene(scene);
        stage.show();
    }

    // ==========================================
    // MÓDULO DE VEHÍCULOS
    // ==========================================
    private BorderPane crearPanelVehiculos() {
        BorderPane panel = new BorderPane();
        TableView<Vehiculo> tabla = new TableView<>();
        ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();

        // Configurar Columnas
        TableColumn<Vehiculo, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        TableColumn<Vehiculo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        TableColumn<Vehiculo, Double> colPrecio = new TableColumn<>("Precio (€)");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        TableColumn<Vehiculo, Double> colVelMax = new TableColumn<>("Vel. Máx");
        colVelMax.setCellValueFactory(new PropertyValueFactory<>("velocidad_max"));

        tabla.getColumns().addAll(colMatricula, colMarca, colModelo, colPrecio, colVelMax);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setItems(listaVehiculos);

        // Cargar datos iniciales
        listaVehiculos.setAll(vehiculoDAO.listarVehiculos());

        // Formulario lateral
        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(20));
        formulario.setPrefWidth(300);
        formulario.getStyleClass().add("form-panel");

        Label tituloForm = new Label("GESTIÓN DE VEHÍCULOS");
        tituloForm.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtMatricula = new TextField(); txtMatricula.setPromptText("Matrícula (Ej: 1234BCD)");
        TextField txtMarca = new TextField(); txtMarca.setPromptText("Marca");
        TextField txtModelo = new TextField(); txtModelo.setPromptText("Modelo");
        TextField txtPrecio = new TextField(); txtPrecio.setPromptText("Precio");
        TextField txtVelMax = new TextField(); txtVelMax.setPromptText("Velocidad Máxima");

        Button btnGuardar = new Button("➕ Guardar");
        Button btnEliminar = new Button("🗑️ Eliminar Seleccionado");
        Button btnActualizar = new Button("🔄 Actualizar Seleccionado");

        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnEliminar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);

        // Listener para rellenar el formulario al hacer clic en la tabla
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                txtMatricula.setText(nuevo.getMatricula());
                txtMatricula.setDisable(true); // Bloqueamos la matrícula (Primary Key)
                txtMarca.setText(nuevo.getMarca());
                txtModelo.setText(nuevo.getModelo());
                txtPrecio.setText(String.valueOf(nuevo.getPrecio()));
                txtVelMax.setText(String.valueOf(nuevo.getVelocidad_max()));
            }
        });

        // Lógica de botones
        btnGuardar.setOnAction(e -> {
            try {
                Vehiculo v = new Vehiculo(
                        txtMatricula.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        txtMarca.getText(),
                        txtModelo.getText(),
                        Double.parseDouble(txtVelMax.getText())
                );
                vehiculoDAO.insertar(v);
                listaVehiculos.setAll(vehiculoDAO.listarVehiculos()); // Refrescar
                limpiarCampos(txtMatricula, txtMarca, txtModelo, txtPrecio, txtVelMax);

                txtMatricula.setDisable(false); // Desbloqueamos el campo
                tabla.getSelectionModel().clearSelection(); // Limpiamos la selección

                mostrarInfo("Éxito", "Vehículo insertado correctamente.");
            } catch (Exception ex) {
                mostrarError("Error de Validación", ex.getMessage() != null ? ex.getMessage() : "Revisa los campos numéricos.");
            }
        });

        // Acción del botón Actualizar
        btnActualizar.setOnAction(e -> {
            Vehiculo seleccionado = tabla.getSelectionModel().getSelectedItem();

            if (seleccionado != null) {
                try {
                    Vehiculo vActualizado = new Vehiculo(
                            txtMatricula.getText(),
                            Double.parseDouble(txtPrecio.getText()),
                            txtMarca.getText(),
                            txtModelo.getText(),
                            Double.parseDouble(txtVelMax.getText())
                    );

                    vehiculoDAO.actualizar(vActualizado);
                    listaVehiculos.setAll(vehiculoDAO.listarVehiculos());
                    limpiarCampos(txtMatricula, txtMarca, txtModelo, txtPrecio, txtVelMax);

                    txtMatricula.setDisable(false);
                    tabla.getSelectionModel().clearSelection();

                    mostrarInfo("Éxito", "Vehículo actualizado correctamente.");

                } catch (Exception ex) {
                    mostrarError("Error de Validación", ex.getMessage() != null ? ex.getMessage() : "Revisa los datos introducidos.");
                }
            } else {
                mostrarError("Aviso", "Selecciona primero un vehículo de la tabla haciendo clic en él.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Vehiculo seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                vehiculoDAO.eliminar(seleccionado.getMatricula());
                listaVehiculos.setAll(vehiculoDAO.listarVehiculos());
                limpiarCampos(txtMatricula, txtMarca, txtModelo, txtPrecio, txtVelMax);

                txtMatricula.setDisable(false); // Desbloqueamos el campo
                tabla.getSelectionModel().clearSelection();

                mostrarInfo("Éxito", "Vehículo eliminado.");
            } else {
                mostrarError("Error", "Selecciona un vehículo de la tabla.");
            }
        });

        formulario.getChildren().addAll(tituloForm, new Separator(), txtMatricula, txtMarca, txtModelo, txtPrecio, txtVelMax, btnGuardar, btnActualizar, btnEliminar);

        panel.setCenter(new StackPane(tabla));
        panel.setRight(formulario);
        return panel;
    }

    // ==========================================
    // MÓDULO DE CLIENTES
    // ==========================================
    private BorderPane crearPanelClientes() {
        BorderPane panel = new BorderPane();
        TableView<Cliente> tabla = new TableView<>();
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Cliente, String> colApe1 = new TableColumn<>("Apellido 1");
        colApe1.setCellValueFactory(new PropertyValueFactory<>("apellido_1"));
        TableColumn<Cliente, String> colApe2 = new TableColumn<>("Apellido 2");
        colApe2.setCellValueFactory(new PropertyValueFactory<>("apellido_2"));
        TableColumn<Cliente, Integer> colEdad = new TableColumn<>("Edad");
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        tabla.getColumns().addAll(colId, colNombre, colApe1, colApe2, colEdad);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setItems(listaClientes);

        listaClientes.setAll(clienteDAO.obtenerClientes());

        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(20));
        formulario.setPrefWidth(300);

        Label tituloForm = new Label("GESTIÓN DE CLIENTES");
        tituloForm.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtNombre = new TextField(); txtNombre.setPromptText("Nombre");
        TextField txtApe1 = new TextField(); txtApe1.setPromptText("Primer Apellido");
        TextField txtApe2 = new TextField(); txtApe2.setPromptText("Segundo Apellido");
        TextField txtEdad = new TextField(); txtEdad.setPromptText("Edad");

        Button btnGuardar = new Button("➕ Guardar");
        Button btnEliminar = new Button("🗑️ Eliminar (Por nombre)");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnEliminar.setMaxWidth(Double.MAX_VALUE);

        btnGuardar.setOnAction(e -> {
            try {
                Cliente c = new Cliente(
                        txtNombre.getText(),
                        txtApe1.getText(),
                        txtApe2.getText(),
                        Integer.parseInt(txtEdad.getText())
                );
                clienteDAO.insertar(c);
                listaClientes.setAll(clienteDAO.obtenerClientes());
                limpiarCampos(txtNombre, txtApe1, txtApe2, txtEdad);
            } catch (Exception ex) {
                mostrarError("Error de Validación", ex.getMessage() != null ? ex.getMessage() : "Edad inválida.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Cliente seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                clienteDAO.eliminarPorNombreCompleto(seleccionado.getNombre(), seleccionado.getApellido_1(), seleccionado.getApellido_2());
                listaClientes.setAll(clienteDAO.obtenerClientes());
            } else {
                mostrarError("Error", "Selecciona un cliente de la tabla.");
            }
        });

        formulario.getChildren().addAll(tituloForm, new Separator(), txtNombre, txtApe1, txtApe2, txtEdad, btnGuardar, btnEliminar);

        panel.setCenter(tabla);
        panel.setRight(formulario);
        return panel;
    }

    // ==========================================
    // MÓDULO DE VENTAS
    // ==========================================
    private BorderPane crearPanelVentas() {
        BorderPane panel = new BorderPane();
        TableView<Venta> tabla = new TableView<>();
        ObservableList<Venta> listaVentas = FXCollections.observableArrayList();

        TableColumn<Venta, Integer> colCod = new TableColumn<>("Cód");
        colCod.setCellValueFactory(new PropertyValueFactory<>("codigoVenta"));
        TableColumn<Venta, LocalDate> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        TableColumn<Venta, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        TableColumn<Venta, String> colFormaPago = new TableColumn<>("Forma de Pago");
        colFormaPago.setCellValueFactory(new PropertyValueFactory<>("formaPago"));
        TableColumn<Venta, Integer> colIdCliente = new TableColumn<>("ID Cliente");
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));

        tabla.getColumns().addAll(colCod, colFecha, colMatricula, colFormaPago, colIdCliente);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setItems(listaVentas);

        listaVentas.setAll(ventaDAO.obtenerVentas());

        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(20));
        formulario.setPrefWidth(300);

        Label tituloForm = new Label("NUEVA VENTA");
        tituloForm.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        DatePicker dpFecha = new DatePicker(); dpFecha.setPromptText("Fecha Venta");
        dpFecha.setMaxWidth(Double.MAX_VALUE);
        TextField txtMatricula = new TextField(); txtMatricula.setPromptText("Matrícula Vehículo");
        ComboBox<String> cbFormaPago = new ComboBox<>(FXCollections.observableArrayList("Efectivo", "Transferencia", "Bizum", "A plazos"));
        cbFormaPago.setPromptText("Forma de Pago");
        cbFormaPago.setMaxWidth(Double.MAX_VALUE);
        TextField txtIdCliente = new TextField(); txtIdCliente.setPromptText("ID Cliente");

        Button btnGuardar = new Button("➕ Registrar Venta");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);

        btnGuardar.setOnAction(e -> {
            try {
                if (dpFecha.getValue() == null) throw new AppException("Debes seleccionar una fecha.");

                Venta v = new Venta(
                        dpFecha.getValue(),
                        txtMatricula.getText(),
                        cbFormaPago.getValue()
                );
                v.setId_cliente(Integer.parseInt(txtIdCliente.getText()));

                ventaDAO.insertar(v);

                // Ahora JavaFX espera a que "insertar" termine. Si falla, el catch de abajo captura el error
                listaVentas.setAll(ventaDAO.obtenerVentas());
                limpiarCampos(txtMatricula, txtIdCliente);
                dpFecha.setValue(null);
                cbFormaPago.setValue(null);
                mostrarInfo("Éxito", "Venta registrada.");

            } catch (Exception ex) {
                mostrarError("Error en Venta", ex.getMessage() != null ? ex.getMessage() : "Error al procesar la venta.");
            }
        });

        formulario.getChildren().addAll(tituloForm, new Separator(), dpFecha, txtMatricula, cbFormaPago, txtIdCliente, btnGuardar);

        panel.setCenter(tabla);
        panel.setRight(formulario);
        return panel;
    }

    // ==========================================
    // MÉTODOS DE UTILIDAD (Alertas y Limpieza)
    // ==========================================
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos(TextInputControl... campos) {
        for (TextInputControl campo : campos) {
            campo.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}