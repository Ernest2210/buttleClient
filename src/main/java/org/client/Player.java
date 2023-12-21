package org.client;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.client.connection.ServerConnection;
import org.client.controller.GameSceneController;
import org.client.enums.FieldType;
import org.client.enums.PlayerType;

import java.util.HashMap;
import java.util.Map;

public class Player extends Rectangle {
    private static final int DAMAGE_LEVEL = 10;
    private static final int RELOAD_TIME_MILLIS = 1000;
    private Map<String, Image> images;
    private int health;
    private String direction;
    private AnchorPane ap;
    private Player enemy;
    private PlayerType playerType;
    private ProgressBar healthProgressBar;
    private AnimationTimer moveAnimationTimer;
    private long lastSootTime;

    public Player(boolean isPlayer, PlayerType type, AnchorPane ap){
        super();
        this.ap = ap;
        lastSootTime = System.currentTimeMillis();
        healthProgressBar = new ProgressBar();
        images = new HashMap<>();
        health = 100;
        this.playerType = type;
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
            moveAnimationTimer = new AnimationTimer() {
                public void handle(long now) {
                    for(String key: Main.getInput()){
                        Map<String, String> data;
                        if(key.equals("UP") || key.equals("LEFT")|| key.equals("RIGHT")|| key.equals("DOWN")){
                            data = move(key);
                            ServerConnection serverConnection = ServerConnection.getServerConnection();
                            serverConnection.sendMove(data);
                        } else if (key.equals("SPACE")) {
                            shot(getX() , getY() , direction, true);
                            Map<String, String> requestData = new HashMap<>();
                            requestData.put("x_coord", String.valueOf(getX()));
                            requestData.put("y_coord", String.valueOf(getY()));
                            requestData.put("direction", direction);
                            ServerConnection.getServerConnection().sendShoot(requestData);
                        }
                    }
                    Main.clearInput();
                }
            };
            moveAnimationTimer.start();
        }
        Platform.runLater(() -> {
            healthProgressBar.setStyle("-fx-accent: #038603");
            healthProgressBar.setProgress(1);
            healthProgressBar.setMinWidth(100);
            healthProgressBar.setMaxWidth(100);
            if(this.playerType == PlayerType.gray){
                healthProgressBar.setLayoutX(0);
                healthProgressBar.setLayoutY(0);
            }else{
                healthProgressBar.setLayoutX(
                        GameSceneController.BLOCK_SIZE * GameSceneController.FIELD_SIZE_X - 100);
                healthProgressBar.setLayoutY(
                        GameSceneController.BLOCK_SIZE * GameSceneController.FIELD_SIZE_Y - 25);
            }
            ap.getChildren().add(healthProgressBar);
            healthProgressBar.toFront();
        });
    }

    public void shot(double x, double y, String direction, boolean reloadAnimation){
        if(lastSootTime + RELOAD_TIME_MILLIS < System.currentTimeMillis()){
            Bullet bullet = new Bullet(x ,y , direction, ap, enemy);
            ap.getChildren().add(bullet);
            lastSootTime = System.currentTimeMillis();
            if(reloadAnimation){
                Rectangle reloadBullet = new Rectangle();
                reloadBullet.setFill(new ImagePattern(new Image("/assets/BulletRIGHT.png")));
                reloadBullet.setWidth(40);
                reloadBullet.setHeight(30);
                reloadBullet.setX(550);
                reloadBullet.setY(0);
                ap.getChildren().add(reloadBullet);
                RotateTransition bulletRotate = new RotateTransition();
                bulletRotate.setNode(reloadBullet);
                bulletRotate.setDuration(Duration.millis(RELOAD_TIME_MILLIS));
                bulletRotate.setByAngle(360);
                bulletRotate.play();
                bulletRotate.setOnFinished(event -> ap.getChildren().remove(reloadBullet));
            }
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

    public void setEnemy(Player enemy){
        this.enemy = enemy;
    }

    public void damage(){
        if(this.health > 0){
            this.health -= DAMAGE_LEVEL;
            healthProgressBar.setProgress((double) this.health / 100);
            if (this.health <= 0){
                Map<String, String> data = new HashMap<>();
                data.put("lose_type", this.playerType.toString());
                ServerConnection.getServerConnection().sendGameOver(data);
            }
        }
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void stopMove(){
        moveAnimationTimer.stop();
    }
}
