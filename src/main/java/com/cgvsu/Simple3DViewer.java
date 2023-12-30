package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));

        Scene scene = new Scene(viewport);
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        String css = Objects.requireNonNull(getClass().getResource("css/styles.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("RenderCat Pro");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("images/icon.png")).toExternalForm()));

        if (Taskbar.isTaskbarSupported()) { // код для того, чтобы на macOS была иконка приложения...
            var taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getResource("images/icon.png"));
                taskbar.setIconImage(dockIcon);
            }

        }
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}