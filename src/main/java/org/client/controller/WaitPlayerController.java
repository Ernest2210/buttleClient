package org.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.client.enums.MessageType;
import org.client.connection.ServerConnection;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class WaitPlayerController {
    @FXML
    private Label roomIdLabel;
    @FXML
    private AnchorPane ap;

    public void initialize(){
        ServerConnection serverConnection = ServerConnection.getServerConnection();
        serverConnection.clearCallbacks();
        serverConnection.addCallback(MessageType.startGame, this::gameScene);
    }

    public void setRoomId(String roomId){
        roomIdLabel.setText(roomId);
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
