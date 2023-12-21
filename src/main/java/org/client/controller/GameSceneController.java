package org.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.client.Main;
import org.client.Player;
import org.client.connection.ServerConnection;
import org.client.enums.FieldType;
import org.client.enums.MessageType;
import org.client.enums.PlayerType;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameSceneController {
    public static final int FIELD_SIZE_X = 10;
    public static final int FIELD_SIZE_Y = 10;
    public static final double BLOCK_SIZE = 60;
    @FXML
    private AnchorPane ap;
    private static List<List<FieldType>> map;

    private Player player;
    private Player enemy;
    private PlayerType playerType;

    private static List<List<FieldType>> generateMap(){
        List<List<FieldType>> map = new ArrayList<>(10);

        List<FieldType> list1 = new ArrayList<>(10);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        list1.add(FieldType.space);
        map.add(list1);

        List<FieldType> list2 = new ArrayList<>(10);
        list2.add(FieldType.wall);
        list2.add(FieldType.wall);
        list2.add(FieldType.wall);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        list2.add(FieldType.space);
        map.add(list2);

        List<FieldType> list3 = new ArrayList<>(10);
        list3.add(FieldType.wall);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.wall);
        list3.add(FieldType.wall);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        list3.add(FieldType.space);
        map.add(list3);

        List<FieldType> list4 = new ArrayList<>(10);
        list4.add(FieldType.wall);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        list4.add(FieldType.wall);
        list4.add(FieldType.wall);
        list4.add(FieldType.wall);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        list4.add(FieldType.space);
        map.add(list4);

        List<FieldType> list5 = new ArrayList<>(10);
        list5.add(FieldType.wall);
        list5.add(FieldType.wall);
        list5.add(FieldType.space);
        list5.add(FieldType.wall);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        list5.add(FieldType.space);
        map.add(list5);

        List<FieldType> list6 = new ArrayList<>(10);
        list6.add(FieldType.wall);
        list6.add(FieldType.wall);
        list6.add(FieldType.space);
        list6.add(FieldType.wall);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        list6.add(FieldType.space);
        map.add(list6);

        List<FieldType> list7 = new ArrayList<>(10);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.wall);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        list7.add(FieldType.space);
        map.add(list7);

        List<FieldType> list8 = new ArrayList<>(10);
        list8.add(FieldType.space);
        list8.add(FieldType.space);
        list8.add(FieldType.space);
        list8.add(FieldType.wall);
        list8.add(FieldType.wall);
        list8.add(FieldType.space);
        list8.add(FieldType.wall);
        list8.add(FieldType.space);
        list8.add(FieldType.space);
        list8.add(FieldType.wall);
        map.add(list8);

        List<FieldType> list9 = new ArrayList<>(10);
        list9.add(FieldType.space);
        list9.add(FieldType.space);
        list9.add(FieldType.space);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        list9.add(FieldType.space);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        list9.add(FieldType.wall);
        map.add(list9);

        List<FieldType> list10 = new ArrayList<>(10);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        list10.add(FieldType.space);
        map.add(list10);
        return map;
    }

    public void initialize(){
        this.map = generateMap();
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(60*j);
                rectangle.setY(60*i);
                rectangle.setWidth(60);
                rectangle.setHeight(60);
                rectangle.setStrokeWidth(2);
                if(map.get(i).get(j) == FieldType.wall){
                    Image img = new Image("/assets/block.png");
                    rectangle.setFill(new ImagePattern(img));
                }else{
                    rectangle.setFill(Color.BLACK);
                    rectangle.setStroke(Color.BLACK);
                }
                ap.getChildren().add(rectangle);
            }
        }
        ServerConnection.getServerConnection().clearCallbacks();
        ServerConnection.getServerConnection().addCallback(MessageType.gameOver, this::gameOver);
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
        player = new Player(true, playerType, ap);
        if(playerType == PlayerType.yellow){
            enemy = new Player(false, PlayerType.gray, ap);
        }else{
            enemy = new Player(false, PlayerType.yellow, ap);
        }
        player.setEnemy(enemy);
        enemy.setEnemy(player);
        ap.getChildren().add(player);
        ap.getChildren().add(enemy);
        ServerConnection serverConnection = ServerConnection.getServerConnection();
        serverConnection.addCallback(MessageType.makeMove, this::moveEnemy);
        serverConnection.addCallback(MessageType.makeShot, this::enemyShot);
    }

    public static List<List<FieldType>> getMap(){
        return map;
    }

    public static FieldType getBlockFromMap(double x, double y){
        int xIndex = (int)(y / BLOCK_SIZE);
        int yIndex = (int)(x / BLOCK_SIZE);
        if(xIndex < 0 || yIndex < 0 || xIndex > FIELD_SIZE_X - 1 || yIndex > FIELD_SIZE_Y - 1){
            return FieldType.wall;
        }
        return map.get(xIndex).get(yIndex);
    }

    public void moveEnemy(Map<String, String> data){
        enemy.moveOnCoords(Double.parseDouble(data.get("x_coord")), Double.parseDouble(data.get("y_coord")), data.get("direction"));
    }

    public void enemyShot(Map<String, String> data){
        enemy.shot(Double.parseDouble(data.get("x_coord")),
                Double.parseDouble(data.get("y_coord")),
                data.get("direction"));
    }

    public void gameOver(Map<String, String> data){
        player.stopMove();
        Stage stage = (Stage) ap.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/gameOver.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameOverScreenController gameOverScreenController = loader.getController();
        String text = "";
        if(data.get("lose_type").equals(player.getPlayerType().toString())){
            text = "Вы проиграли";
        }else{
            text = "Вы победили";
        }
        gameOverScreenController.setText(text);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
