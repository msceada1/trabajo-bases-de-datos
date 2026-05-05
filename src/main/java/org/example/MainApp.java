package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // Contenido básico
        Label label = new Label("Hola, JavaFX 👋");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        // Escena
        Scene scene = new Scene(root, 400, 250);

        // Configuración de la ventana
        stage.setTitle("Mi primera app JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
