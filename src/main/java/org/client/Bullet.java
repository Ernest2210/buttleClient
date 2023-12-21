package org.client;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.client.controller.GameSceneController;
import org.client.enums.FieldType;

import java.util.HashMap;
import java.util.Map;

public class Bullet extends Rectangle {
    private static final Map<String, Image> images = new HashMap<>();
    private AnimationTimer animationTimer;
    private double xMove;
    private double yMove;
    private AnchorPane ap;
    private Player enemy;

    static {
        images.put("UP", new Image("/assets/BulletUP.png"));
        images.put("DOWN", new Image("/assets/BulletDOWN.png"));
        images.put("LEFT", new Image("/assets/BulletLEFT.png"));
        images.put("RIGHT", new Image("/assets/BulletRIGHT.png"));
    }

    public Bullet(double x, double y, String direction, AnchorPane ap, Player enemy){
        super();
        this.ap = ap;
        this.enemy = enemy;
        xMove = 0;
        yMove = 0;
        // -20 + 15 LEFT
        if(direction.equals("LEFT")){
            setX(x - 30);
            setY(y + 15);
            setWidth(60);
            setHeight(30);
            xMove = -60;
        }else if(direction.equals("RIGHT")){
            setX(x + 30);
            setY(y + 15);
            setWidth(60);
            setHeight(30);
            xMove = 60;
        } else if (direction.equals("UP")) {
            setX(x + 15);
            setY(y - 30);
            setWidth(30);
            setHeight(60);
            yMove = -60;
        }else if (direction.equals("DOWN")) {
            setX(x + 15);
            setY(y + 30);
            setWidth(30);
            setHeight(60);
            yMove = 60;
        }
        setFill(new ImagePattern(images.get(direction)));
        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                move();
            }
        };
        animationTimer.start();
    }

    public void move(){
        setX(getX() + this.xMove);
        setY(getY() + this.yMove);
        if(GameSceneController.getBlockFromMap(getX(), getY()) == FieldType.wall){
            System.out.println("STOP");
            animationTimer.stop();
            ap.getChildren().remove(this);
        }
        if((int)(enemy.getX() / GameSceneController.BLOCK_SIZE) == (int)(getX() / GameSceneController.BLOCK_SIZE)
                && (int)(enemy.getY() / GameSceneController.BLOCK_SIZE) == (int)(getY() / GameSceneController.BLOCK_SIZE)){
            enemy.damage();
            animationTimer.stop();
            ap.getChildren().remove(this);
        }
    }
}
