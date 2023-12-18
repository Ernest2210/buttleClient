package org.client.enums;

public enum MessageType {
    waitSecondPlayer ("waitSecondPlayer"),
    startGame ("startGame"),
    makeMove ("makeMove"),
    makeShot ("makeShoot");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
