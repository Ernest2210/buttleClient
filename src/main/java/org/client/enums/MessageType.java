package org.client.enums;

public enum MessageType {
    waitSecondPlayer ("waitSecondPlayer"),
    startGame ("startGame"),
    makeMove ("makeMove"),
    makeShot ("makeShoot"),
    gameOver ("gameOver");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
