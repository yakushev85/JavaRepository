package com.oleksandr.iakushev.oenkeeper;

import com.oleksandr.iakushev.oenkeeper.model.DataItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class KeeperApplication extends Application {
    private Stage stage;
    private StorageManager storageManager;
    private String currentStorageName;
    private String currentPassword;
    private List<DataItem> initialResources;

    private static KeeperApplication instance;

    public KeeperApplication() {
        instance = this;
    }

    public static KeeperApplication getInstance() {
        return instance;
    }

    // Run from console:
    // java -jar "OenKeeper-1.0-SNAPSHOT-jar-with-dependencies.jar" -Dfile.encoding=UTF-8 com.oleksandr.iakushev.oenkeeper.KeeperApplication
    public static void main(String[] args) {
        launch(args);
    }

    public void gotoLoginWindow() {
        try {
            replaceSceneContent("/fxml/login.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoResourcesWindow() {
        try {
            replaceSceneContent("/fxml/resources.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoCreateWindow() {
        try {
            replaceSceneContent("/fxml/create.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoLoadingWindow() {
        try {
            replaceSceneContent("/fxml/loading.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoSavingWindow() {
        try {
            replaceSceneContent("/fxml/saving.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        storageManager = new StorageManager();

        gotoLoginWindow();
        stage.show();
    }

    private void replaceSceneContent(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("OenKeeper");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setMaximized(false);
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public Stage getStage() {
        return stage;
    }

    public FileChooser getFileChooserDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Storage File");
        File currentPath = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(currentPath);
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("Storage File (*.oen)", "*.oen");
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser;
    }

    public void loadInitialResources() throws Exception {
        initialResources = StorageManager.loadDataItems(currentStorageName, currentPassword);
    }

    public void setCurrentStorageName(String currentStorageName) {
        this.currentStorageName = currentStorageName;
    }

    public String getCurrentStorageName() {
        return currentStorageName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public List<DataItem> getInitialResources() {
        return initialResources;
    }

    public void clearInitialResources() {
        this.initialResources = null;
    }

    public static void showSimpleWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
