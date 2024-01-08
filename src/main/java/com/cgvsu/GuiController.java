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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.Iterator;
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
    private TextField cameraField;
    @FXML
    private Pane wrapperPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TableView<ModelData> tableModels;
    @FXML
    private TableColumn<ModelData, String> columnModelName;
    @FXML
    private TableColumn<ModelData, Boolean> columnModelActive;
    @FXML
    private TableColumn<ModelData, Boolean> columnModelVisible;
    @FXML
    private TableView<Camera> tableCameras;

    @FXML
    private TableColumn<Camera, String> tableCamerasCamera;

    @FXML
    private TableColumn<Camera, VectorDimThree> tableCamerasPos;

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
    private ColorPicker gridColorPicker;
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

        cameraController.choseCamera(camera);

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

        tableModels.widthProperty().addListener((ov, oldValue, newValue) -> columnModelName.setPrefWidth(newValue.doubleValue() - 100));
        tableModels.setPlaceholder(new Label("No models loaded."));

        tableCameras.widthProperty().addListener((ov, oldValue, newValue) -> columnModelName.setPrefWidth(newValue.doubleValue() - 100));
        tableCameras.setPlaceholder(new Label("No cameras"));

        checkLightTheme.setSelected(true);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        columnModelName.setReorderable(false);
        columnModelActive.setReorderable(false);
        columnModelVisible.setReorderable(false);

        selectedModelsData = tableModels.getSelectionModel().getSelectedItems();

        //selectedCamerasData = tableCameras.getSelectionModel().getSelectedItems();
        tableCamerasCamera.setCellValueFactory(new PropertyValueFactory<Camera, String>("name"));
        tableCamerasPos.setCellValueFactory(new PropertyValueFactory<Camera, VectorDimThree>("position"));
        tableCameras.setItems(FXCollections.observableList(cameraController.getCameras()));


        columnModelName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelName()));
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

        checkBoxPolygonalGrid.setOnAction(e -> {
            if (checkBoxPolygonalGrid.isSelected()) {
                drawModesController.setDrawMesh(true);
            } else {
                drawModesController.setDrawMesh(false);
            }
        });

        checkBoxTexture.setOnAction(e -> {
            if (checkBoxTexture.isSelected()) {
                drawModesController.setDrawTexture(true);
            } else {
                drawModesController.setDrawTexture(false);
            }
        });

        checkBoxLighting.setOnAction(e -> {
            if (checkBoxLighting.isSelected()) {
                drawModesController.setDrawLight(true);
            } else {
                drawModesController.setDrawLight(false);
            }
        });
        // состояние при загрузке программы
        //checkBoxPolygonalGrid.setSelected(true);
        // сетка.визибл(да);
        // или любой другой набор параметров, по идее надо ставить галочку и включать
        // параметр здесь синхронно (только для начального состояния)
        // иначе получится, что сетку мы видим, а галочка не стоит

        // fillColorPicker.setValue(изначально заданный цвет, чтобы было синхронно с gui);
        // gridColorPicker.setValue(изначально заданный цвет, чтобы было синхронно с gui);

        fillColorPicker.setOnAction(e -> {
            fillColor = fillColorPicker.getValue();
        });

        gridColorPicker.setOnAction(e -> {
            meshColor = gridColorPicker.getValue();
        });

        ObservableList<ModelData> data = FXCollections.observableArrayList();
        tableModels.setItems(data);

        tableModels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void loadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setInitialDirectory(new File("C:\\University\\2year_part1\\Graphics\\CGVSU-main\\3DModels")); // для удобства при тестировании
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

            sceneModels.chooseModel(model);
            sceneModels.seeModel(model);
            modelsInfo.addModelFilename(model, fileNameString);
            modelsInfo.addModelTriangulated(model, model); // здесь второй должна быть триангулированная модель, но с ней всё тормозит
            // todo: обработка ошибок
        } catch (IOException exception) {
            System.err.println(exception);
        }
    }
    @FXML
    private void loadTexture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg", "*npg"));
        fileChooser.setInitialDirectory(new File("C:\\University\\2year_part1\\Graphics\\CGVSU-main\\3DModels")); // для удобства при тестировании
        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        try {
            BufferedImage image = ImageIO.read(file);
            for (Model activeModel: sceneModels.getActiveModels()) {
                sceneModels.addTexture(activeModel, image);
            }
        } catch (IOException exception) {
            System.err.println(exception);
        }
    }
    @FXML
    private void removeTexture() {
        for (Model activeModel: sceneModels.getActiveModels()) {
            sceneModels.addTexture(activeModel, null);
        }
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
        cameraController.choseCamera(camera);
        //tableCameras.setItems(FXCollections.observableList(cameraController.getCameras()));
        tableCameras.refresh();
    }

    @FXML
    private void chooseCamera() {
        List<Camera> cameras = cameraController.getCameras();
        for (Camera camera: cameras) {
            if (camera.getName().equals(cameraField.getText())) {
                cameraController.choseCamera(camera);
                break;
            }
        }

        tableCameras.setItems(FXCollections.observableList(cameraController.getCameras()));
    }
    @FXML
    private void removeCamera() {
        List<Camera> cameras = cameraController.getCameras();

        Iterator<Camera> iterator = cameras.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(cameraField.getText())) iterator.remove();
        }

        tableCameras.setItems(FXCollections.observableList(cameraController.getCameras()));
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