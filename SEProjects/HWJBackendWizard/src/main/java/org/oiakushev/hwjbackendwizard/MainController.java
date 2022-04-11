package org.oiakushev.hwjbackendwizard;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.oiakushev.hwjbackendwizard.model.Entity;
import org.oiakushev.hwjbackendwizard.model.Field;
import org.oiakushev.hwjbackendwizard.model.FieldType;
import org.oiakushev.hwjbackendwizard.model.ProjectMetaInfo;
import org.oiakushev.hwjbackendwizard.util.StringNamingUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MainController extends Application {
    private static final Insets DEFAULT_PANE_INSETS = new Insets(10, 5, 5, 5);

    private Button addEntityBtn, delEntityBtn, renameEntityBtn;
    private ListView<Entity> entityListView;
    private ListView<Field> fieldListView;
    private Button addFieldBtn, delFieldBtn, renameFieldBtn;
    private ComboBox<FieldType> typeFieldCombo;
    private CheckBox searchByFieldCheck;
    private CheckBox requiredCheck;
    private TextField buildPathText;
    private TextField artifactIdText, groupIdText, versionText;
    private Button generateBtn;

    private final DataController dataController = new DataController();

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("HWJ Backend Wizard");

        TabPane mainPane = new TabPane();
        mainPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab modelTab = new Tab();
        modelTab.setText("Model");
        modelTab.setContent(initModelPane());

        Tab projectTab = new Tab();
        projectTab.setText("Project");
        projectTab.setContent(initProjectTab());

        mainPane.getTabs().addAll(modelTab, projectTab);

        Scene mainScene = new Scene(mainPane, 900, 500);

        updateControlsFromSelection();
        setUpListeners(mainStage);

        mainStage.setScene(mainScene);
        mainStage.show();
    }

    private FlowPane initProjectTab() {
        FlowPane projectPane = new FlowPane(Orientation.VERTICAL, 5, 5);

        projectPane.setAlignment(Pos.CENTER);

        buildPathText = new TextField();
        buildPathText.setMinWidth(400);
        buildPathText.setPromptText("Build path");
        buildPathText.setEditable(false);
        projectPane.getChildren().add(buildPathText);

        artifactIdText = new TextField("ApplicationName");
        artifactIdText.setMinWidth(400);
        artifactIdText.setPromptText("ApplicationName");
        projectPane.getChildren().add(artifactIdText);

        groupIdText = new TextField("com.example");
        groupIdText.setMinWidth(400);
        groupIdText.setPromptText("Group ID");
        projectPane.getChildren().add(groupIdText);

        versionText = new TextField("1.0");
        versionText.setMinWidth(400);
        versionText.setPromptText("Version");
        projectPane.getChildren().add(versionText);

        generateBtn = new Button("Generate");
        generateBtn.setMinWidth(400);
        projectPane.getChildren().add(generateBtn);

        return projectPane;
    }

    private FlowPane initModelPane() {
        FlowPane modelPane = new FlowPane();

        modelPane.setAlignment(Pos.TOP_CENTER);

        modelPane.getChildren().addAll(
                initEntityControls(),
                initEntityListView(),
                initFieldListView(),
                initFieldControls()
        );

        return modelPane;
    }

    private FlowPane initEntityControls() {
        FlowPane entityControlsPane = new FlowPane(Orientation.VERTICAL, 0, 5);
        entityControlsPane.setPadding(DEFAULT_PANE_INSETS);

        addEntityBtn = new Button("Add Entity");
        entityControlsPane.getChildren().add(addEntityBtn);

        delEntityBtn = new Button("Delete Entity");
        entityControlsPane.getChildren().add(delEntityBtn);

        renameEntityBtn = new Button("Rename Entity");
        entityControlsPane.getChildren().add(renameEntityBtn);

        return entityControlsPane;
    }

    private FlowPane initFieldControls() {
        FlowPane fieldControlPane = new FlowPane(Orientation.VERTICAL, 0, 5);
        fieldControlPane.setPadding(DEFAULT_PANE_INSETS);

        addFieldBtn = new Button("Add Field");
        fieldControlPane.getChildren().add(addFieldBtn);

        delFieldBtn = new Button("Delete Field");
        fieldControlPane.getChildren().add(delFieldBtn);

        renameFieldBtn = new Button("Rename Field");
        fieldControlPane.getChildren().add(renameFieldBtn);

        Label typeFieldLbl = new Label("Type:");
        fieldControlPane.getChildren().add(typeFieldLbl);

        typeFieldCombo =
                new ComboBox<>(FXCollections.observableArrayList(dataController.getFieldTypes()));
        fieldControlPane.getChildren().add(typeFieldCombo);

        searchByFieldCheck = new CheckBox("Add `search by` endpoint");
        fieldControlPane.getChildren().add(searchByFieldCheck);

        requiredCheck = new CheckBox("Is required?");
        fieldControlPane.getChildren().add(requiredCheck);

        return fieldControlPane;
    }

    private FlowPane initEntityListView() {
        FlowPane entityListPane = new FlowPane(Orientation.VERTICAL);
        entityListPane.setPadding(DEFAULT_PANE_INSETS);

        entityListView =
                new ListView<>(FXCollections.observableArrayList(dataController.getEntities()));
        entityListPane.getChildren().add(entityListView);
        entityListView.getSelectionModel().select(0);

        return entityListPane;
    }

    private FlowPane initFieldListView() {
        FlowPane fieldListPane = new FlowPane(Orientation.VERTICAL);
        fieldListPane.setPadding(DEFAULT_PANE_INSETS);

        Entity selectedItem = entityListView.getSelectionModel().getSelectedItem();

        fieldListView =
                new ListView<>(FXCollections.observableArrayList(selectedItem.getFields()));
        fieldListPane.getChildren().add(fieldListView);
        fieldListView.getSelectionModel().select(0);

        return fieldListPane;
    }

    private void updateControlsFromSelection() {
        Entity currentEntity = entityListView.getSelectionModel().getSelectedItem();
        Field currentField = fieldListView.getSelectionModel().getSelectedItem();

        if (currentEntity == null || currentField == null) {
            return;
        }

        delEntityBtn.setDisable(currentEntity.isReadOnly());
        renameEntityBtn.setDisable(currentEntity.isReadOnly());

        boolean isDisableField = currentField.getType().getFieldType() == FieldType.FieldTypeValue.id
                || currentField.isReadOnly();

        delFieldBtn.setDisable(currentField.isReadOnly());
        renameFieldBtn.setDisable(currentField.isReadOnly());

        searchByFieldCheck.setDisable(isDisableField);
        searchByFieldCheck.setSelected(currentField.isAddSearchBy());

        requiredCheck.setDisable(isDisableField);
        requiredCheck.setSelected(currentField.isRequired());

        typeFieldCombo.setDisable(isDisableField);
        typeFieldCombo.getSelectionModel().select(currentField.getType());
    }

    private void refreshFieldTypeView() {
        typeFieldCombo.setItems(FXCollections.observableArrayList(
                dataController.getFieldTypes()
        ));
    }

    private void refreshEntityListView() {
        entityListView.setItems(FXCollections.observableArrayList(
                dataController.getEntities()
        ));
        entityListView.getSelectionModel().selectLast();
    }

    private void refreshFieldListView(Entity entity) {
        fieldListView.setItems(FXCollections.observableArrayList(
                entity.getFields()
        ));
        fieldListView.getSelectionModel().selectLast();
    }

    private void setUpListeners(Stage mainStage) {
        addEntityBtn.setOnAction((value) -> {
            TextInputDialog addEntityDialog = new TextInputDialog();
            addEntityDialog.setTitle("New Entity");
            addEntityDialog.setContentText("Please enter a name for a new entity.");
            addEntityDialog.setResizable(false);
            Optional<String> result = addEntityDialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                String entityNewName = StringNamingUtil.toFirstLetterUpper(result.get());
                Entity newEntity = dataController.addNewEntityWithName(entityNewName);
                refreshEntityListView();
                refreshFieldListView(newEntity);
                refreshFieldTypeView();
                updateControlsFromSelection();
            }
        });

        delEntityBtn.setOnAction((value) -> {
            Entity selectedItem = entityListView.getSelectionModel().getSelectedItem();
            dataController.getEntities().remove(selectedItem);
            refreshEntityListView();
            refreshFieldListView(entityListView.getSelectionModel().getSelectedItem());
            refreshFieldTypeView();
            updateControlsFromSelection();
        });

        renameEntityBtn.setOnAction((value) -> {
            Entity selectedItem = entityListView.getSelectionModel().getSelectedItem();
            TextInputDialog renameEntityDialog = new TextInputDialog(selectedItem.getName());
            renameEntityDialog.setTitle("Rename Entity");
            renameEntityDialog.setContentText("Please rename the entity.");
            renameEntityDialog.setResizable(false);
            Optional<String> result = renameEntityDialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                String newName = StringNamingUtil.toFirstLetterUpper(result.get());
                int index = dataController.getEntities().indexOf(selectedItem);
                if (index >= 0) {
                    dataController.getEntities().get(index).setName(newName);
                    entityListView.setItems(FXCollections.observableArrayList(
                            dataController.getEntities()
                    ));
                    entityListView.refresh();
                }
            }
        });

        addFieldBtn.setOnAction((value) -> {
            TextInputDialog addFieldDialog = new TextInputDialog();
            addFieldDialog.setTitle("New Field");
            addFieldDialog.setContentText("Please enter a name for a new field.");
            addFieldDialog.setResizable(false);
            Optional<String> result = addFieldDialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                String fieldNewName = StringNamingUtil.toFirsLetterLower(result.get());
                Entity currentEntity = entityListView.getSelectionModel().getSelectedItem();
                dataController.addNewFieldWithName(currentEntity, fieldNewName);
                refreshFieldListView(currentEntity);
                updateControlsFromSelection();
            }
        });

        delFieldBtn.setOnAction((value) -> {
            Entity selectedEntity = entityListView.getSelectionModel().getSelectedItem();
            Field selectedField = fieldListView.getSelectionModel().getSelectedItem();

            int index = dataController.getEntities().indexOf(selectedEntity);
            if (index >= 0) {
                dataController.getEntities().get(index).getFields().remove(selectedField);
                refreshFieldListView(dataController.getEntities().get(index));
            }
        });

        renameFieldBtn.setOnAction((value) -> {
            Field selectedField = fieldListView.getSelectionModel().getSelectedItem();

            TextInputDialog renameFieldDialog = new TextInputDialog(selectedField.getName());
            renameFieldDialog.setTitle("Rename Field");
            renameFieldDialog.setContentText("Please rename the field.");
            renameFieldDialog.setResizable(false);
            Optional<String> result = renameFieldDialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                String fieldNewName = StringNamingUtil.toFirsLetterLower(result.get());
                Entity selectedEntity = entityListView.getSelectionModel().getSelectedItem();

                int indexEntity = dataController.getEntities().indexOf(selectedEntity);
                if (indexEntity >= 0) {
                    int indexField = dataController.getEntities().get(indexEntity).getFields().indexOf(selectedField);
                    if (indexField >= 0) {
                        dataController.getEntities().get(indexEntity).getFields().get(indexField).setName(fieldNewName);

                        fieldListView.setItems(FXCollections.observableArrayList(
                                dataController.getEntities().get(indexEntity).getFields()
                        ));
                        fieldListView.refresh();
                    }
                }
            }
        });

        entityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshFieldListView(newValue);
            }
            updateControlsFromSelection();
        });

        fieldListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateControlsFromSelection();
        });

        typeFieldCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Entity selectedEntity = entityListView.getSelectionModel().getSelectedItem();
            Field selectedField = fieldListView.getSelectionModel().getSelectedItem();
            int indexEntity = dataController.getEntities().indexOf(selectedEntity);
            if (indexEntity >= 0) {
                int indexField = dataController.getEntities().get(indexEntity).getFields().indexOf(selectedField);
                if (indexField >= 0) {
                    dataController.getEntities().get(indexEntity).getFields().get(indexField).setType(newValue);
                    fieldListView.setItems(FXCollections.observableArrayList(
                            dataController.getEntities().get(indexEntity).getFields()
                    ));
                }
            }
        });

        searchByFieldCheck.setOnAction((value) -> {
            Entity selectedEntity = entityListView.getSelectionModel().getSelectedItem();
            Field selectedField = fieldListView.getSelectionModel().getSelectedItem();
            int indexEntity = dataController.getEntities().indexOf(selectedEntity);
            if (indexEntity >= 0) {
                int indexField = dataController.getEntities().get(indexEntity).getFields().indexOf(selectedField);
                if (indexField >= 0) {
                    dataController.getEntities().get(indexEntity).getFields().get(indexField)
                            .setAddSearchBy(searchByFieldCheck.isSelected());
                    fieldListView.setItems(FXCollections.observableArrayList(
                            dataController.getEntities().get(indexEntity).getFields()
                    ));
                }
            }
        });

        requiredCheck.setOnAction((value) -> {
            Entity selectedEntity = entityListView.getSelectionModel().getSelectedItem();
            Field selectedField = fieldListView.getSelectionModel().getSelectedItem();
            int indexEntity = dataController.getEntities().indexOf(selectedEntity);
            if (indexEntity >= 0) {
                int indexField = dataController.getEntities().get(indexEntity).getFields().indexOf(selectedField);
                if (indexField >= 0) {
                    dataController.getEntities().get(indexEntity).getFields().get(indexField)
                            .setRequired(requiredCheck.isSelected());
                    fieldListView.setItems(FXCollections.observableArrayList(
                            dataController.getEntities().get(indexEntity).getFields()
                    ));
                }
            }
        });

        buildPathText.setOnMouseClicked((value) -> {
            DirectoryChooser buildPathChooser = new DirectoryChooser();
            buildPathChooser.setTitle("Select path to save project");
            File pathToBuildFile = buildPathChooser.showDialog(mainStage);
            if (pathToBuildFile != null && pathToBuildFile.exists() && pathToBuildFile.canWrite()) {
                buildPathText.setText(pathToBuildFile.getPath());
            }
        });

        generateBtn.setOnAction((value) -> {
            String pathBuild = buildPathText.getText();
            String artifactId = artifactIdText.getText();
            String groupId = groupIdText.getText();
            String version = versionText.getText();

            if (validateForEmpty(artifactId, "Artifact Id") ||
                    validateForEmpty(groupId, "Group Id") ||
                    validateForEmpty(version, "Version")) {
                return;
            }

            generateBtn.setDisable(true);

            try {
                dataController.generateCode(pathBuild, new ProjectMetaInfo(artifactId, groupId, version));
            } catch (IOException e) {
                e.printStackTrace();
                showError(e.getMessage());
            }

            generateBtn.setDisable(false);

            Alert alertWindow = new Alert(Alert.AlertType.WARNING, "Project was generated!", ButtonType.OK);
            alertWindow.show();
        });
    }

    private boolean validateForEmpty(String value, String valueName) {
        boolean isEmpty = value.isEmpty();

        if (isEmpty) {
            Alert alertWindow = new Alert(Alert.AlertType.ERROR,  valueName + " can't be empty!", ButtonType.OK);
            alertWindow.show();
        }

        return isEmpty;
    }

    private void showError(String text) {
        Alert alertWindow = new Alert(Alert.AlertType.ERROR,  text, ButtonType.OK);
        alertWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
