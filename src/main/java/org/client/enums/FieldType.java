package org.client.enums;

public enum FieldType {
    wall ("wall"),
    space ("space");

    String type;

    FieldType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
