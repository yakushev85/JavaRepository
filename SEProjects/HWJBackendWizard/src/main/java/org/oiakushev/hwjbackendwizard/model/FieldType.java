package org.oiakushev.hwjbackendwizard.model;

import java.util.Arrays;
import java.util.List;

public class FieldType {
    public static List<FieldType> NOT_LINKED_FIELD_TYPES_LIST = Arrays.asList(
            new FieldType(FieldTypeValue.vString),
            new FieldType(FieldTypeValue.id),
            new FieldType(FieldTypeValue.numInt),
            new FieldType(FieldTypeValue.numLong),
            new FieldType(FieldTypeValue.numFloat),
            new FieldType(FieldTypeValue.numDouble),
            new FieldType(FieldTypeValue.dDate),
            new FieldType(FieldTypeValue.bytes)
    );

    private final FieldTypeValue fieldTypeRaw;
    private Entity linkedEntity;

    public FieldType(FieldTypeValue fieldTypeRaw) {
        this.fieldTypeRaw = fieldTypeRaw;
    }

    public void setLinkedEntity(Entity linkedEntity) {
        this.linkedEntity = linkedEntity;
    }

    public FieldTypeValue getFieldType() {
        return fieldTypeRaw;
    }

    public Entity getLinkedEntity() {
        return linkedEntity;
    }

    public String getUITitle() {
        if (fieldTypeRaw == FieldTypeValue.idLink && linkedEntity != null) {
            return fieldTypeRaw.getTitle() + linkedEntity.getName();
        } else {
            return fieldTypeRaw.getTitle();
        }
    }

    public enum FieldTypeValue {
        vString(0, "string", "String"),
        id(1, "id", "Long"),
        numInt(2, "int", "int"),
        numLong(3, "long", "long"),
        numFloat(4, "float", "float"),
        numDouble(5,"double", "double"),
        dDate(6, "date", "Date"),
        bytes(7,"bytes", "byte[]"),
        idLink(8,"link to ", "long");

        private final int index;
        private final String title;
        private final String originalType;

        FieldTypeValue(int index, String title, String originalType) {
            this.index = index;
            this.title = title;
            this.originalType = originalType;
        }

        public int getIndex() {
            return index;
        }

        public String getTitle() {
            return title;
        }

        public String getOriginalType() {
            return originalType;
        }
    }

    @Override
    public String toString() {
        return getUITitle();
    }
}
