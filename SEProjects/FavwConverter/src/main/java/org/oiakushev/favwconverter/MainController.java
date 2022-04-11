package org.oiakushev.favwconverter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.oiakushev.favwconverter.model.ConvertStatus;
import org.oiakushev.favwconverter.model.ConvertingType;
import org.oiakushev.favwconverter.model.FileToConvert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController extends Application {
    private static final List<ConvertingType> CONVERTING_TYPES_ARRAY = Arrays.asList(
            ConvertingType.MKV,
            ConvertingType.AVI,
            ConvertingType.MP4,

            ConvertingType.MP3,
            ConvertingType.OGG,
            ConvertingType.AAC
    );

    private ListView<FileToConvert> filesListView;
    private final ArrayList<FileToConvert> filesToConvert = new ArrayList<>();
    private ComboBox<ConvertingType> convertingTypeCombo;
    private TextField pathToText;
    private Button addButton, removeButton, clearButton;
    private Button convertButton, cancelButton;
    private Thread dataThread;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Favw Converter");

        FlowPane mainPane = new FlowPane(Orientation.HORIZONTAL, 5, 5);
        mainPane.setAlignment(Pos.CENTER);

        FlowPane filesListPane = new FlowPane(Orientation.VERTICAL, 5, 5);

        filesListView =
                new ListView<>(FXCollections.observableArrayList(filesToConvert));
        filesListView.setPrefWidth(300);
        filesListPane.getChildren().add(filesListView);

        FlowPane controlsPane = new FlowPane(Orientation.VERTICAL, 5, 5);

        addButton = new Button("Add");
        addButton.setMinWidth(180);
        controlsPane.getChildren().add(addButton);

        removeButton = new Button("Remove");
        removeButton.setMinWidth(180);
        controlsPane.getChildren().add(removeButton);

        clearButton = new Button("Clear");
        clearButton.setMinWidth(180);
        controlsPane.getChildren().add(clearButton);

        Label convertingTypeLabel = new Label("Converting type");
        controlsPane.getChildren().add(convertingTypeLabel);

        convertingTypeCombo = new ComboBox<>(FXCollections.observableArrayList(CONVERTING_TYPES_ARRAY));
        convertingTypeCombo.getSelectionModel().select(0);
        convertingTypeCombo.setMinWidth(180);
        controlsPane.getChildren().add(convertingTypeCombo);

        Label pathToLabel = new Label("Saving path:");
        controlsPane.getChildren().add(pathToLabel);

        pathToText = new TextField();
        pathToText.setEditable(false);
        controlsPane.getChildren().add(pathToText);

        convertButton = new Button("Convert");
        convertButton.setMinWidth(180);
        controlsPane.getChildren().add(convertButton);

        cancelButton = new Button("Cancel");
        cancelButton.setDisable(true);
        cancelButton.setMinWidth(180);
        controlsPane.getChildren().add(cancelButton);

        mainPane.getChildren().addAll(filesListPane, controlsPane);

        setUpListeners(mainStage);

        Scene mainScene = new Scene(mainPane, 600, 500);

        mainStage.setScene(mainScene);
        mainStage.show();
    }

    private void setUpListeners(Stage mainStage) {
        addButton.setOnAction((value) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Add file to convert");
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(mainStage);
            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                for (File selectedFile : selectedFiles) {
                    filesToConvert.add(new FileToConvert(selectedFile));
                }

                refreshFilesListView();
            }
        });

        removeButton.setOnAction((value) -> {
            FileToConvert fileSelected = filesListView.getSelectionModel().getSelectedItem();
            if (fileSelected != null) {
                filesToConvert.remove(fileSelected);
                refreshFilesListView();
            }
        });

        clearButton.setOnAction((value) -> {
            filesToConvert.clear();
            refreshFilesListView();
        });

        pathToText.setOnMouseClicked((value) -> {
            DirectoryChooser buildPathChooser = new DirectoryChooser();
            buildPathChooser.setTitle("Select path to save");
            File pathToBuildFile = buildPathChooser.showDialog(mainStage);
            if (pathToBuildFile != null && pathToBuildFile.exists() && pathToBuildFile.canWrite()) {
                pathToText.setText(pathToBuildFile.getPath());
            }
        });

        convertButton.setOnAction((value) -> {
            if (filesToConvert.isEmpty()) {
                showAlertError("Please select files to convert!");
                return;
            }

            ConvertingType convertingType = convertingTypeCombo.getSelectionModel().getSelectedItem();
            String pathTo = pathToText.getText();

            convertingFiles(convertingType, pathTo);
        });

        cancelButton.setOnAction((value) -> cancelConvertingFiles());
    }

    private void refreshFilesListView() {
        filesListView.setItems(FXCollections.observableArrayList(filesToConvert));
    }

    private void convertingFiles(ConvertingType convertingType, String pathTo) {
        makeFilesReady();

        DataRunnable dataRunnable = new DataRunnable(convertingType, pathTo, filesToConvert);
        dataRunnable.setMainController(this);

        dataThread = new Thread(dataRunnable);
        dataThread.start();
        disableControls();
    }

    private void makeFilesReady() {
        for (FileToConvert fileToConvert : filesToConvert) {
            fileToConvert.setStatus(ConvertStatus.READY);
        }
    }

    private void cancelConvertingFiles() {
        if (dataThread != null && dataThread.isAlive()) {
            dataThread.interrupt();

            for (FileToConvert fileToConvert : filesToConvert) {
                if (fileToConvert.getStatus() == ConvertStatus.CONVERTING) {
                    fileToConvert.setStatus(ConvertStatus.READY);
                }
            }

            filesListView.refresh();
            enableControls();
        }
    }

    private boolean isCompletedList() {
        for (FileToConvert fileToConvert : filesToConvert) {
            if (fileToConvert.getStatus() != ConvertStatus.COMPLETED) {
                return false;
            }
        }

        return true;
    }

    private void disableControls() {
        filesListView.setDisable(true);
        convertingTypeCombo.setDisable(true);
        pathToText.setDisable(true);
        addButton.setDisable(true);
        removeButton.setDisable(true);
        clearButton.setDisable(true);
        convertButton.setDisable(true);
        cancelButton.setDisable(false);
    }

    private void enableControls() {
        filesListView.setDisable(false);
        convertingTypeCombo.setDisable(false);
        pathToText.setDisable(false);
        addButton.setDisable(false);
        removeButton.setDisable(false);
        clearButton.setDisable(false);
        convertButton.setDisable(false);
        cancelButton.setDisable(true);
    }

    private void showAlertError(String msg) {
        Alert alertWindow = new Alert(Alert.AlertType.ERROR,  msg, ButtonType.OK);
        alertWindow.show();
    }

    public void updateData() {
        filesListView.refresh();

        if (isCompletedList()) {
            enableControls();
        }
    }

    public void handleError(String msg) {
        cancelConvertingFiles();
        enableControls();

        Platform.runLater(() -> showAlertError(msg));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
