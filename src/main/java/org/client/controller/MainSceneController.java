package org.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import org.client.enums.MessageType;
import org.client.connection.ServerConnection;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MainSceneController {
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField textField;

    public void initialize(){
        ServerConnection serverConnection = ServerConnection.getServerConnection();
        serverConnection.clearCallbacks();
        serverConnection.addCallback(MessageType.waitSecondPlayer, this::waitScene);
        serverConnection.addCallback(MessageType.startGame, this::gameScene);
    }

    public void createRoom(ActionEvent e){
        System.out.println("CREATE");
        ServerConnection.getServerConnection().sendCreateRoomMessage();
    }

    public void connectToRoom(ActionEvent e){
        String room_id = textField.getText();
        System.out.println(room_id);
        ServerConnection.getServerConnection().sendConnectToRoom(room_id);
    }

    public void waitScene(Map<String, String> data){
        Stage stage = (Stage) ap.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/waitPlayerScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WaitPlayerController waitPlayerController = loader.getController();
        waitPlayerController.setRoomId(data.get("room"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void gameScene(Map<String, String> data){
        Stage stage = (Stage) ap.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/gameScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameSceneController gameSceneController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
