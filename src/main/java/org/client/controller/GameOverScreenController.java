package org.client.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.client.connection.ServerConnection;

import java.io.IOException;
import java.net.URL;


public class GameOverScreenController {
    @FXML
    private AnchorPane ap;
    @FXML
    private Label resultLabel;

    public void initialize(){
        ServerConnection.getServerConnection().clearCallbacks();
    }

    public void goMain(){
        Stage stage = (Stage) ap.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void setText(String text){
        resultLabel.setText(text);
    }
}
