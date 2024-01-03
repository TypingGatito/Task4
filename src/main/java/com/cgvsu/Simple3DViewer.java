package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Simple3DViewer extends Application {
    private static final String LIGHT_THEME = "css/light_theme.css";
    private static final String DARK_THEME = "css/dark_theme.css";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/gui.fxml"));
        VBox viewport = loader.load();

        Scene scene = new Scene(viewport);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);

        GuiController guiController = loader.getController();
        guiController.setScene(scene);

        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(LIGHT_THEME)).toExternalForm());

        stage.setTitle("RenderCat Pro");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("images/icon.png")).toExternalForm()));

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            e.consume();
            guiController.quit();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}