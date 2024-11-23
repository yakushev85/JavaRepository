package com.oleksandr.iakushev.oenkeeper.controller;

import com.oleksandr.iakushev.oenkeeper.KeeperApplication;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public class LoginController {
    @FXML
    private ComboBox<String> storageCombo;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        KeeperApplication.getInstance().clearInitialResources();
        loadListOfStorage();
    }

    public void onClickLogin() throws IOException {
        String storageName = storageCombo.getValue();
        if (StringUtils.isEmpty(storageName)) {
            KeeperApplication.showSimpleWarning("Please select storage!");
            return;
        }

        String password = passwordField.getText();
        if (StringUtils.isEmpty(password)) {
            KeeperApplication.showSimpleWarning("Please enter password!");
            return;
        }

        KeeperApplication.getInstance().setCurrentStorageName(storageName);
        KeeperApplication.getInstance().setCurrentPassword(password);

        KeeperApplication.getInstance().gotoLoadingWindow();
        Thread thread = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    KeeperApplication.getInstance().loadInitialResources();
                } catch (Exception e) {
                    this.cancel();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                KeeperApplication.getInstance().gotoResourcesWindow();
            }

            @Override
            protected void cancelled() {
                KeeperApplication.getInstance().gotoLoginWindow();
                KeeperApplication.showSimpleWarning("Please verify your password!");
            }

            @Override
            protected void failed() {
                KeeperApplication.getInstance().gotoLoginWindow();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public void onClickOpenStorage() {
        FileChooser fileChooser = KeeperApplication.getInstance().getFileChooserDialog();
        File file = fileChooser.showOpenDialog(KeeperApplication.getInstance().getStage());
        if (file != null) {
            KeeperApplication.getInstance().getStorageManager().addStorageFileName(file.getAbsolutePath());
            try {
                KeeperApplication.getInstance().getStorageManager().saveStorageFileNames();
                loadListOfStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCreateStorage() {
        KeeperApplication.getInstance().gotoCreateWindow();
    }

    private void loadListOfStorage() {
        KeeperApplication application = KeeperApplication.getInstance();

        try {
            Set<String> listOfStorage = application.getStorageManager().loadStorageFileNames();
            storageCombo.setItems(FXCollections.observableArrayList(listOfStorage));

            if (listOfStorage.size() > 0) {
                storageCombo.setValue(listOfStorage.iterator().next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onPasswordEnteredAction() throws IOException {
        onClickLogin();
    }
}
