package org.client.enums;

public enum PlayerType {
    gray ("gray"),
    yellow ("yellow");

    private String type;

    PlayerType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
