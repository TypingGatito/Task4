package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));

        Scene scene = new Scene(viewport);
        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        String css = Objects.requireNonNull(getClass().getResource("css/styles.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("RenderCat Pro");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("images/RenderCat Pro Icon.png")).toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}