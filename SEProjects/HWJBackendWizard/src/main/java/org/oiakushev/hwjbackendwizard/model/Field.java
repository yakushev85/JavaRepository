package org.oiakushev.hwjbackendwizard.model;

public class Field {
    private String name;
    private FieldType type;
    private boolean isAddSearchBy;
    private boolean isReadOnly;
    private boolean isRequired;

    public Field(String name, FieldType type) {
        this.name = name;
        this.type = type;
        isAddSearchBy = false;
        isReadOnly = false;
        isRequired = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getType() {
        if (name.equals("id")) {
            type = new FieldType(FieldType.FieldTypeValue.id);
        } else if (type == null) {
            type = new FieldType(FieldType.FieldTypeValue.vString);
        }
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public boolean isAddSearchBy() {
        return isAddSearchBy;
    }

    public void setAddSearchBy(boolean addSearchBy) {
        isAddSearchBy = addSearchBy;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public static Field generateIdField() {
        Field idField = new Field("id", new FieldType(FieldType.FieldTypeValue.id));
        idField.isReadOnly = true;
        return idField;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDbType() {
        String result = "";

        switch (type.getFieldType()) {
            case vString:
                result += "VARCHAR(255)";
                break;
            case id:
                result += "int(11) NOT NULL AUTO_INCREMENT";
                break;
            case numInt:
                result += "int(6)";
                break;
            case numLong: case idLink:
                result += "int(11)";
                break;
            case numFloat:
                result += "float";
                break;
            case numDouble:
                result += "double";
                break;
            case dDate:
                result += "datetime";
                break;
            case bytes:
                result += "blob";
                break;
        }

        if (type.getFieldType() != FieldType.FieldTypeValue.id) {
            result += (isRequired)? " NOT NULL" : " DEFAULT NULL";
        }

        return result;
    }
}
