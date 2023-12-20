package org.client;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.client.connection.ServerConnection;
import org.client.controller.GameSceneController;
import org.client.enums.FieldType;
import org.client.enums.PlayerType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Rectangle {
    private Map<String, Image> images;
    private int health;
    private String direction;
    public Player(boolean isPlayer, PlayerType type){
        super();
        images = new HashMap<>();
        health = 100;
        if(type == PlayerType.yellow){
            this.setX(GameSceneController.BLOCK_SIZE * (GameSceneController.FIELD_SIZE_X-1));
            this.setY(GameSceneController.BLOCK_SIZE * (GameSceneController.FIELD_SIZE_Y-1));
            this.setWidth(GameSceneController.BLOCK_SIZE);
            this.setHeight(GameSceneController.BLOCK_SIZE);
            images.put("UP", new Image("/assets/UP.png"));
            images.put("DOWN", new Image("/assets/DOWN.png"));
            images.put("LEFT", new Image("/assets/LEFT.png"));
            images.put("RIGHT", new Image("/assets/RIGHT.png"));
            direction = "LEFT";
            this.setFill(new ImagePattern(images.get("LEFT")));
        }else{
            this.setX(0);
            this.setY(0);
            this.setWidth(60);
            this.setHeight(60);
            images.put("UP", new Image("/assets/EnemyUP.png"));
            images.put("DOWN", new Image("/assets/EnemyDOWN.png"));
            images.put("LEFT", new Image("/assets/EnemyLEFT.png"));
            images.put("RIGHT", new Image("/assets/EnemyRIGHT.png"));
            direction = "RIGHT";
            this.setFill(new ImagePattern(images.get("RIGHT")));
        }

        if(isPlayer){
            System.out.println(type);
            new AnimationTimer() {
                public void handle(long now) {
                    for(String key: Main.getInput()){
                        Map<String, String> data;
                        if(key.equals("UP") || key.equals("LEFT")|| key.equals("RIGHT")|| key.equals("DOWN")){
                            data = move(key);
                            ServerConnection serverConnection = ServerConnection.getServerConnection();
                            serverConnection.sendMove(data);
                        }
                    }
                    Main.clearInput();
                }
            }.start();
        }
    }

    public void moveOnCoords(double x, double y, String direction){
        setX(x);
        setY(y);
        this.direction = direction;
        setFill(new ImagePattern(images.get(direction)));
    }

    public Map<String, String> move(String direction){
        this.setFill(new ImagePattern(images.get(direction)));
        if(this.direction.equals(direction)){
            double newY = getY();
            double newX = getX();
            if(direction.equals("UP")){
                newY = getY() - GameSceneController.BLOCK_SIZE;
            } else if (direction.equals("DOWN")) {
                newY = getY() + GameSceneController.BLOCK_SIZE;
            } else if (direction.equals("LEFT")) {
                newX = getX() - GameSceneController.BLOCK_SIZE;
            } else if (direction.equals("RIGHT")) {
                newX = getX() + GameSceneController.BLOCK_SIZE;
            }
            System.out.println(GameSceneController.getBlockFromMap(newX, newY));
            if(GameSceneController.getBlockFromMap(newX, newY) != FieldType.wall){
                setX(newX);
                setY(newY);
            }
        }
        this.direction = direction;
        Map<String, String> coords = new HashMap<>();
        coords.put("x_coord", String.valueOf(getX()));
        coords.put("y_coord", String.valueOf(getY()));
        coords.put("direction", this.direction);
        return coords;
    }
}
