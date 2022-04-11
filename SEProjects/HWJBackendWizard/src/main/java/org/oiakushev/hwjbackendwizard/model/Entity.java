package org.oiakushev.hwjbackendwizard.model;

import java.util.ArrayList;

public class Entity {
    private String name;
    private ArrayList<Field> Fields;
    private boolean isReadOnly;

    public Entity(String name) {
        this.name = name;
        isReadOnly = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Field> getFields() {
        return Fields;
    }

    public void setFields(ArrayList<Field> fields) {
        Fields = fields;
    }

    public Boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        isReadOnly = readOnly;
    }

    public static Entity generateUserEntity() {
        Entity userEntity = new Entity("User");
        userEntity.isReadOnly = true;

        ArrayList<Field> userFields = new ArrayList<>();
        userFields.add(Field.generateIdField());

        Field usernameField = new Field("username", new FieldType(FieldType.FieldTypeValue.vString));
        usernameField.setReadOnly(true);
        usernameField.setRequired(true);
        usernameField.setAddSearchBy(true);
        userFields.add(usernameField);

        Field passwordField = new Field("password", new FieldType(FieldType.FieldTypeValue.vString));
        passwordField.setReadOnly(true);
        passwordField.setRequired(true);
        userFields.add(passwordField);

        userEntity.setFields(userFields);
        return userEntity;
    }

    @Override
    public String toString() {
        return name;
    }
}
