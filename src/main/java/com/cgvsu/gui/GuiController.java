package com.cgvsu.gui;

import com.cgvsu.components.SceneModels;
import com.cgvsu.components.model.Model;
import com.cgvsu.components.model.ModelTriangulated;
import com.cgvsu.draw.modes.CameraController;
import com.cgvsu.infoclasses.ModelsInfo;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GuiController {
    final private float TRANSLATION = 0.5F;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private TableView<ModelData> tableModels;
    @FXML
    private TableColumn<ModelData, String> columnModelName;
    @FXML
    private TableColumn<ModelData, Boolean> columnModelActive;
    private final SceneModels sceneModels;
    private final ModelsInfo modelsInfo;
    private Timeline timeline;

    //позже удалить отдельную камеру
    private CameraController cameraController = new CameraController();
    /*    private Camera camera = new Camera(
                new VectorDimThree(0, 0, 100),
                new VectorDimThree(0, 0, 0),
                1.0F, 1, 0.01F, 100);*/

    public GuiController() {
        Camera camera = new Camera(
                new VectorDimThree(0, 0, 100),
                new VectorDimThree(0, 0, 0),
                1.0F, 1, 0.01F, 100);

        cameraController.choseCamera(camera);

        sceneModels = new SceneModels();
        modelsInfo = new ModelsInfo();
    }

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        columnModelActive.setSortable(false);
        columnModelName.setSortable(false);
        columnModelName.setReorderable(false);
        columnModelActive.setReorderable(false);

        columnModelName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelName()));
        columnModelActive.setCellFactory(col -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {

                    ModelData rowData = getTableView().getItems().get(getIndex());
                    if (checkBox.isSelected()) {
                        sceneModels.getChosenModels().add(rowData.getModel());
                    } else {
                        sceneModels.getChosenModels().remove(rowData.getModel());
                    }
                    rowData.setActive(checkBox.isSelected());
                });
            }

            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        });

        ObservableList<ModelData> data = FXCollections.observableArrayList();
        tableModels.setItems(data);

        tableModels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableModels.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectModel(newSelection.getModelName());
            }
        });

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            cameraController.getCurCamera().setAspectRatio((float) (width / height));

            if (sceneModels.getChosenModels() != null) {
                for (Model model : sceneModels.getChosenModels()) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), cameraController.getCurCamera(), model, (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    // Метод для открытия модели
    private void selectModel(String modelName) {
        // Ваш код для открытия модели по имени modelName
        System.out.println("Выбрана модель: " + modelName);
    }

    @FXML
    private void addOnButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setInitialDirectory(new File("./3DModels")); // для удобства при тестировании
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            Model model = ObjReader.read(fileContent);

            String fileNameString = fileName.toString();
            String shortName = fileNameString.substring(fileNameString.lastIndexOf("\\") + 1, fileNameString.length() - 4);

            tableModels.getItems().add(new ModelData(model, shortName, true));

            sceneModels.addModel(model);
            sceneModels.chooseModel(model);
            modelsInfo.addModelFilename(model, fileNameString);
            modelsInfo.addModelTriangulated(model, new ModelTriangulated(model));
            // todo: обработка ошибок
        } catch (IOException exception) {
            System.err.println(exception);
        }
    }

    @FXML
    private void deleteSelectedOnButtonClick() {
        ObservableList<ModelData> selectedModelsData = tableModels.getSelectionModel().getSelectedItems();
        List<Model> selectedModels = selectedModelsData.stream()
                .map(ModelData::getModel)
                .toList();
        selectedModels.forEach((m) -> sceneModels.getModelMatrixInfoMap().remove(m));
        selectedModels.forEach((m) -> sceneModels.getChosenModels().remove(m));
        tableModels.getItems().removeAll(selectedModelsData);
    }

    @FXML
    private void saveSelectedOnButtonClick() {
        ObservableList<ModelData> selectedModelsData = tableModels.getSelectionModel().getSelectedItems();
        List<Model> selectedModels = selectedModelsData.stream()
                .map(ModelData::getModel)
                .toList();
        selectedModels.forEach((m) -> ObjWriter.write(modelsInfo.getMadelFilenameMap().get(m), m));
        tableModels.getItems().removeAll(selectedModelsData);
    }

    @FXML
    private void saveSelectedAsOnButtonClick() {
        ObservableList<ModelData> selectedModelsData = tableModels.getSelectionModel().getSelectedItems();
        List<Model> selectedModels = selectedModelsData.stream()
                .map(ModelData::getModel)
                .toList();

        FileChooser fileChooser = new FileChooser();

        for (Model model : selectedModels) {
            String modelName = modelsInfo.getMadelFilenameMap().get(model);
            String shortName = modelName.substring(modelName.lastIndexOf("\\") + 1, modelName.length() - 4);

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
            fileChooser.setInitialDirectory(new File("./")); // для удобства при тестировании

            fileChooser.setTitle(String.format("Save %s as", shortName));

            File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

            if (file != null) {
                Path fileName = Path.of(file.getAbsolutePath());
                ObjWriter.write(fileName.toString(), model);
            }
        }
    }

    @FXML
    private void exitOnButtonClick() {
        //todo: if (есть несохранённые изменения) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Unsaved changes have been made. Do you want to save them?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == noButton) {
                Platform.exit();
            } else if (buttonType == yesButton) {
                //todo: save all unsaved changes
                Platform.exit();
            }
        });
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, 0, -TRANSLATION));
        //camera.movePosition(new VectorDimThree(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, 0, TRANSLATION));
        //camera.movePosition(new VectorDimThree(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(TRANSLATION, 0, 0));
        //camera.movePosition(new VectorDimThree(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(-TRANSLATION, 0, 0));
        //camera.movePosition(new VectorDimThree(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, TRANSLATION, 0));
        //camera.movePosition(new VectorDimThree(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, -TRANSLATION, 0));
        //camera.movePosition(new VectorDimThree(0, -TRANSLATION, 0));
    }
}