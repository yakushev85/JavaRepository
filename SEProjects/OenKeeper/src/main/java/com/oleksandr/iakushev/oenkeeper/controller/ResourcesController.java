package com.oleksandr.iakushev.oenkeeper.controller;

import com.oleksandr.iakushev.oenkeeper.KeeperApplication;
import com.oleksandr.iakushev.oenkeeper.StorageManager;
import com.oleksandr.iakushev.oenkeeper.model.DataItem;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class ResourcesController {
    @FXML
    private TableView<DataItem> tableResources;
    @FXML
    private TableColumn<DataItem, String> columnTitle;
    @FXML
    private TableColumn<DataItem, String> columnDescription;
    @FXML
    private TextField resourceTitleField;
    @FXML
    private TextArea resourceDescriptionField;
    @FXML
    private TextArea resourceSensInfoField;

    private List<DataItem> resources;
    private DataItem currentItem;

    @FXML
    public void initialize() {
        loadResourcesTable();
    }

    private void loadResourcesTable() {
        resources = KeeperApplication.getInstance().getInitialResources();

        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableResources.setItems(FXCollections.observableArrayList(resources));

        tableResources.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showDataItem(newSelection);
            }
        });
    }

    private void showDataItem(DataItem newSelection) {
        currentItem = newSelection;
        resourceTitleField.setText(newSelection.getTitle());
        resourceDescriptionField.setText(newSelection.getDescription());
        resourceSensInfoField.setText(newSelection.getSensitiveInfo());
    }

    public void onClickSave() {
        if (currentItem != null) updateResourceList();
        KeeperApplication.getInstance().gotoSavingWindow();

        Thread thread = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    StorageManager.saveDataItems(
                            KeeperApplication.getInstance().getCurrentStorageName(),
                            KeeperApplication.getInstance().getCurrentPassword(),
                            resources);
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
                KeeperApplication.getInstance().gotoResourcesWindow();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    private void updateResourceList() {
        for (DataItem item : resources) {
            if (currentItem.getId().equals(item.getId())) {
                item.setTitle(resourceTitleField.getText());
                item.setDescription(resourceDescriptionField.getText());
                item.setSensitiveInfo(resourceSensInfoField.getText());
                tableResources.setItems(FXCollections.observableArrayList(resources));
                tableResources.refresh();
                return;
            }
        }
    }

    public void onClickLogout() {
        KeeperApplication.getInstance().gotoLoginWindow();
    }

    public void onClickNew() {
        DataItem newItem = StorageManager.generateNewDataItem();
        resources.add(newItem);
        tableResources.setItems(FXCollections.observableArrayList(resources));
        tableResources.getSelectionModel().select(newItem);
        tableResources.refresh();
    }

    public void onClickDelete() {
        if (currentItem == null) {
            KeeperApplication.showSimpleWarning("Please select item to delete!");
            return;
        }

        if (resources.size() == 1) {
            KeeperApplication.showSimpleWarning("Storage should contain at least one item!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete selected item?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (DataItem item : resources) {
                if (item.getId().equals(currentItem.getId())) {
                    resources.remove(item);
                    clearItemFields();
                    tableResources.setItems(FXCollections.observableArrayList(resources));
                    tableResources.refresh();
                    return;
                }
            }
        }
    }

    private void clearItemFields() {
        currentItem = null;
        resourceTitleField.setText(StringUtils.EMPTY);
        resourceDescriptionField.setText(StringUtils.EMPTY);
        resourceSensInfoField.setText(StringUtils.EMPTY);
    }
}
