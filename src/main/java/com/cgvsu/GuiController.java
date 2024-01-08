package com.cgvsu;

import com.cgvsu.components.SceneModels;
import com.cgvsu.components.model.Model;
import com.cgvsu.draw.light.LightParams;
import com.cgvsu.draw.modes.CameraController;
import com.cgvsu.draw.modes.DrawModesController;
import com.cgvsu.infoclasses.ModelData;
import com.cgvsu.infoclasses.ModelsInfo;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.Camera;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class GuiController {
    private static final String LIGHT_THEME = "css/light_theme.css";
    private static final String DARK_THEME = "css/dark_theme.css";
    final private char pathSlash =
            System.getProperty("os.name").toLowerCase()
                    .contains("win") ? '\\' : '/';
    final private float TRANSLATION = 0.5F;
    private final SceneModels sceneModels;
    private final ModelsInfo modelsInfo;
    @FXML
    private Pane wrapperPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TableView<ModelData> tableModels;
    @FXML
    private TableColumn<ModelData, String> columnModelName;
    @FXML
    private TableColumn<ModelData, Button> columnTextureButton;
    @FXML
    private TableColumn<ModelData, Boolean> columnModelActive;
    @FXML
    private TableColumn<ModelData, Boolean> columnModelVisible;
    @FXML
    private ScrollPane camerasScrollPane;
    @FXML
    private TableView<Camera> tableCameras;
    @FXML
    private TableColumn<Camera, String> columnCameraName;
    @FXML
    private TableColumn<Camera, VectorDimThree> columnCameraPos;
    @FXML
    private CheckMenuItem checkLightTheme;
    @FXML
    private CheckMenuItem checkDarkTheme;
    @FXML
    private CheckBox checkBoxPolygonalGrid;
    @FXML
    private CheckBox checkBoxTexture;
    @FXML
    private CheckBox checkBoxLighting;
    @FXML
    private Label leftStatusLabel;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private ColorPicker meshColorPicker;
    @FXML
    private HBox hboxCamerasButtons;
    private Scene scene;
    private ObservableList<ModelData> selectedModelsData;
    private ObservableList<Camera> selectedCamerasData;
    private Timeline timeline;
    //позже удалить отдельную камеру
    private CameraController cameraController = new CameraController();

    private DrawModesController drawModesController;
    private LightParams lightParams;
    private Color fillColor = Color.RED;
    private Color meshColor = Color.BLACK;
    private int lastCam = 1;

    public GuiController() {
        Camera camera = new Camera(
                new VectorDimThree(0, 0, 50),
                new VectorDimThree(0, 0, 0),
                1.0F, 1, 0.01F, 1000, lastCam++ + "");

        cameraController.chooseCamera(camera);

        sceneModels = new SceneModels();
        modelsInfo = new ModelsInfo();

        lightParams = new LightParams(new VectorDimThree(1000, 0, 0), 0.4);
        drawModesController = new DrawModesController(fillColor, meshColor);
    }

    public void setScene(Scene s) {
        this.scene = s;
    }

    @FXML
    private void initialize() {
        wrapperPane.widthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        wrapperPane.heightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        tableModels.widthProperty().addListener((ov, oldValue, newValue) -> columnModelName.setPrefWidth(newValue.doubleValue() - 170));
        tableModels.setPlaceholder(new Label("No models loaded."));

        camerasScrollPane.widthProperty().addListener((ov, oldValue, newValue) -> tableCameras.setPrefWidth(newValue.doubleValue()));
        tableCameras.widthProperty().addListener((ov, oldValue, newValue) -> columnCameraName.setPrefWidth(newValue.doubleValue() * 0.3 - 3));
        tableCameras.widthProperty().addListener((ov, oldValue, newValue) -> columnCameraPos.setPrefWidth(newValue.doubleValue() * 0.7));
        tableCameras.setPlaceholder(new Label("No cameras."));

        tableCameras.getItems().addAll(cameraController.getCameras());

        hboxCamerasButtons.setAlignment(Pos.CENTER);

        checkDarkTheme.setSelected(true);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        columnModelName.setReorderable(false);
        columnTextureButton.setReorderable(false);
        columnModelActive.setReorderable(false);
        columnModelVisible.setReorderable(false);

        columnCameraName.setReorderable(false);
        columnCameraPos.setReorderable(false);

        selectedModelsData = tableModels.getSelectionModel().getSelectedItems();
        selectedCamerasData = tableCameras.getSelectionModel().getSelectedItems();

        columnCameraName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCameraPos.setCellValueFactory(new PropertyValueFactory<>("position"));

        columnModelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));

        columnTextureButton.setCellFactory(col -> new TableCell<>() {
            private final Button button = new Button("Load");

            {
                button.setUserData("load");
                button.setFocusTraversable(false);
                button.setOnAction(event -> {
                    ModelData md = getTableView().getItems().get(getIndex());

                    if (button.getUserData().equals("load")) {
                        if (loadTexture(md)) {
                            button.setText("Remove");
                            button.setUserData("remove");
                        }
                    } else {
                        removeTexture(md);
                        button.setText("Load");
                        button.setUserData("load");
                    }
                });
            }

            @Override
            public void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(button);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        columnModelActive.setCellFactory(col -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setAlignment(Pos.CENTER);
                checkBox.setFocusTraversable(false);
                checkBox.setOnAction(event -> {
                    ModelData rowData = getTableView().getItems().get(getIndex());
                    if (checkBox.isSelected()) {
                        sceneModels.getActiveModels().add(rowData.getModel());
                    } else {
                        sceneModels.getActiveModels().remove(rowData.getModel());
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


        columnModelVisible.setCellFactory(col -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setAlignment(Pos.CENTER);
                checkBox.setFocusTraversable(false);
                checkBox.setOnAction(event -> {
                    ModelData rowData = getTableView().getItems().get(getIndex());
                    if (checkBox.isSelected()) {
                        sceneModels.getVisibleModels().add(rowData.getModel());
                    } else {
                        sceneModels.getVisibleModels().remove(rowData.getModel());
                    }
                    rowData.setVisible(checkBox.isSelected());
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

        checkBoxPolygonalGrid.setOnAction(e -> drawModesController.setDrawMesh(checkBoxPolygonalGrid.isSelected()));
        checkBoxTexture.setOnAction(e -> drawModesController.setDrawTexture(checkBoxTexture.isSelected()));
        checkBoxLighting.setOnAction(e -> drawModesController.setDrawLight(checkBoxLighting.isSelected()));

        fillColorPicker.setValue(fillColor);
        meshColorPicker.setValue(meshColor);

        fillColorPicker.setOnAction(e -> fillColor = fillColorPicker.getValue());
        meshColorPicker.setOnAction(e -> meshColor = meshColorPicker.getValue());

        tableModels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableCameras.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        selectedModelsData.addListener((ListChangeListener<ModelData>) change -> {
            if (selectedModelsData.isEmpty()) {
                leftStatusLabel.setText("No items selected");
            } else {
                StringBuilder builder = new StringBuilder("Selected items: ");
                for (ModelData md : selectedModelsData) {
                    builder.append(md.getModelName()).append(", ");
                }
                builder.delete(builder.length() - 2, builder.length());
                leftStatusLabel.setText(builder.toString());
            }
        });

        checkLightTheme.setOnAction(event -> {
            if (!checkLightTheme.isSelected()) {
                checkLightTheme.setSelected(true);
            } else {
                checkDarkTheme.setSelected(false);
                scene.getStylesheets().clear();
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(LIGHT_THEME)).toExternalForm());
            }
        });

        checkDarkTheme.setOnAction(event -> {
            if (!checkDarkTheme.isSelected()) {
                checkDarkTheme.setSelected(true);
            } else {
                checkLightTheme.setSelected(false);
                scene.getStylesheets().clear();
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(DARK_THEME)).toExternalForm());
            }
        });

        KeyFrame frame = new KeyFrame(Duration.millis(300), event -> {
            tableCameras.refresh();
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            if (cameraController.getCurCamera() != null) {
                cameraController.getCurCamera().setAspectRatio((float) (width / height));

                lightParams.setLightSource(cameraController.getCurCamera().getPosition());

                drawModesController.setDefaultFillColor(fillColor);
                drawModesController.setMeshColor(meshColor);
                drawModesController.render(canvas.getGraphicsContext2D(), cameraController.getCurCamera(),
                        sceneModels, (int) width, (int) height, lightParams);

/*            if (sceneModels.getVisibleModels() != null) {
                for (Model model : sceneModels.getVisibleModels()) {
                    try {
                        BufferedImage image = ImageIO.read(new File("C:\\University\\2year_part1\\Graphics\\CGVSU-main\\3DModels\\Faceform\\AlexWithTexture\\NeutralWrapped.jpg"));
                        RenderEngine.render(canvas.getGraphicsContext2D(), cameraController.getCurCamera(),
                                modelsInfo.getModelTriangulatedModelMap().get(model), (int) width, (int) height);
                    } catch (Exception e) {

                    }
                }
            }*/
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void loadModel() {
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
            String shortName = fileNameString.substring(fileNameString.lastIndexOf(pathSlash) + 1, fileNameString.length() - 4);

            tableModels.getItems().add(new ModelData(model, shortName, true));

            sceneModels.makeModelActive(model);
            sceneModels.seeModel(model);
            modelsInfo.addModelFilename(model, fileNameString);
            modelsInfo.addModelTriangulated(model, model); // здесь второй должна быть триангулированная модель, но с ней всё тормозит
            // todo: обработка ошибок
        } catch (IOException exception) {
            System.err.println(exception);
        }
    }

    private boolean loadTexture(ModelData md) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image (.jpg, .png)", "*.jpg", "*.png"));
        fileChooser.setInitialDirectory(new File("./3DModels")); // для удобства при тестировании

        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return false;
        }

        try {
            BufferedImage image = ImageIO.read(file);
            sceneModels.addTexture(md.getModel(), image);
            return true;
        } catch (IOException exception) {
            System.err.println(exception);
        }
        return false;
    }

    private void removeTexture(ModelData md) {
        sceneModels.addTexture(md.getModel(), null);
    }

    @FXML
    private void deleteSelected() {
        List<Model> selectedModels = selectedModelsData.stream()
                .map(ModelData::getModel)
                .toList();
        selectedModels.forEach((m) -> sceneModels.getModelMatrixInfoMap().remove(m));
        selectedModels.forEach((m) -> sceneModels.getActiveModels().remove(m));
        selectedModels.forEach((m) -> sceneModels.getVisibleModels().remove(m));
        tableModels.getItems().removeAll(selectedModelsData);
    }

    @FXML
    private void saveSelected() {
        List<Model> selectedModels = selectedModelsData.stream()
                .map(ModelData::getModel)
                .toList();
        selectedModels.forEach((m) -> ObjWriter.write(modelsInfo.getMadelFilenameMap().get(m), m));
        tableModels.getItems().removeAll(selectedModelsData);
    }

    @FXML
    private void saveSelectedAs() {
        List<Model> selectedModels = selectedModelsData.stream()
                .map(ModelData::getModel)
                .toList();

        FileChooser fileChooser = new FileChooser();

        for (Model model : selectedModels) {
            String modelName = modelsInfo.getMadelFilenameMap().get(model);
            String shortName = modelName.substring(modelName.lastIndexOf(pathSlash) + 1, modelName.length() - 4);

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
    private void addCamera() {
        Camera camera = new Camera(
                new VectorDimThree(0, 0, 50),
                new VectorDimThree(0, 0, 0),
                1.0F, 1, 0.01F, 1000, lastCam++ + "");
        cameraController.addCamera(camera);
        cameraController.chooseCamera(camera);
        tableCameras.getItems().add(camera);
    }

    @FXML
    private void chooseCamera() {
        List<Camera> selectedCameras = selectedCamerasData.stream()
                .toList();
        // выбрана может быть только одна камера, поэтому обойдёмся обращением к первому элементу списка
        if (!selectedCameras.isEmpty()) {
            cameraController.chooseCamera(selectedCameras.get(0));
        }
    }

    @FXML
    private void removeCamera() {
        List<Camera> selectedCameras = selectedCamerasData.stream()
                .toList();
        // выбрана может быть только одна камера, поэтому обойдёмся обращением к первому элементу списка
        cameraController.removeCamera(selectedCameras.get(0));
        tableCameras.getItems().remove(selectedCameras.get(0));
    }

    @FXML
    private void selectAll() {
        tableModels.getSelectionModel().selectAll();
    }

    @FXML
    private void unselectAll() {
        tableModels.getSelectionModel().clearSelection();
    }

    @FXML
    public void quit() {
//        //todo: if (есть несохранённые изменения) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirm");
//        alert.setHeaderText("Unsaved changes have been made. Do you want to save them?");
//
//        ButtonType yesButton = new ButtonType("Yes");
//        ButtonType noButton = new ButtonType("No");
//        ButtonType cancelButton = new ButtonType("Cancel");
//
//        alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
//
//        alert.showAndWait().ifPresent(buttonType -> {
//            if (buttonType == noButton) {
//                Platform.exit();
//            } else if (buttonType == yesButton) {
//                //todo: save all unsaved changes
//                Platform.exit();
//            }
//        });
        Platform.exit();
    }

    @FXML
    private void handleCameraForward(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, 0, -TRANSLATION));
    }

    @FXML
    private void handleCameraBackward(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, 0, TRANSLATION));
    }

    @FXML
    private void handleCameraLeft(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(TRANSLATION, 0, 0));
    }

    @FXML
    private void handleCameraRight(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(-TRANSLATION, 0, 0));
    }

    @FXML
    private void handleCameraUp(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, TRANSLATION, 0));
    }

    @FXML
    private void handleCameraDown(ActionEvent actionEvent) {
        cameraController.moveCurCameraPosition(new VectorDimThree(0, -TRANSLATION, 0));
    }
}