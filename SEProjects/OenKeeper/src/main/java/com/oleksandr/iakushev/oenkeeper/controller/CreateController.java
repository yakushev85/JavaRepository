package com.oleksandr.iakushev.oenkeeper.controller;

import com.oleksandr.iakushev.oenkeeper.KeeperApplication;
import com.oleksandr.iakushev.oenkeeper.StorageManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class CreateController {
    @FXML
    private TextField newStorageFileNameField;
    @FXML
    private PasswordField newStoragePasswordField;
    @FXML
    private PasswordField newPromtStoragePasswordField;

    public void selectNewStorageFile() {
        FileChooser fileChooser = KeeperApplication.getInstance().getFileChooserDialog();
        File file = fileChooser.showSaveDialog(KeeperApplication.getInstance().getStage());
        if (file != null) {
            newStorageFileNameField.setText(file.getAbsolutePath());
        }
    }

    public void cancelNewStorage() {
        KeeperApplication.getInstance().gotoLoginWindow();
    }

    public void saveNewStorage() {
        if (!fieldsValidation()) return;

        try {
            StorageManager.saveDataItems(
                    newStorageFileNameField.getText(),
                    newPromtStoragePasswordField.getText(),
                    StorageManager.generateNewStorageList());

            StorageManager storageManager = KeeperApplication.getInstance().getStorageManager();
            storageManager.addStorageFileName(newStorageFileNameField.getText());
            storageManager.saveStorageFileNames();
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeeperApplication.getInstance().gotoLoginWindow();
    }

    private boolean fieldsValidation() {
        if (StringUtils.isEmpty(newStorageFileNameField.getText())) {
            KeeperApplication.showSimpleWarning("Please select file to save!");
            return false;
        }

        String password = newStoragePasswordField.getText();
        String promtPassword = newPromtStoragePasswordField.getText();
        if (!password.equals(promtPassword) || StringUtils.isEmpty(password)) {
            KeeperApplication.showSimpleWarning("Your passwords are not equal or empty, please enter again!");
            return false;
        }

        return true;
    }
}
