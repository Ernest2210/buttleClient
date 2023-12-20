package org.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.client.connection.ServerConnection;
import org.client.controller.GameSceneController;

import java.net.URL;
import java.util.ArrayList;

public class Main extends Application {
    private static ArrayList<String> input;
    private long lastUpdate;
    private static int time;
    private static int updateRate = 120;
    ServerConnection serverConnection;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        lastUpdate = 0L;
        time = 0;
        input = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);



        this.serverConnection = ServerConnection.getServerConnection();

        new AnimationTimer() {
            public void handle(long now) {

                long deltaTime = (now - lastUpdate)/1000000;
                if(deltaTime >= (1000/updateRate) ){

                    serverConnection.executeFromMessages();
                    lastUpdate = now;
                    time++;
                }
            }
        }.start();

        primaryStage.show();
    }

    public static ArrayList<String> getInput(){
        return input;
    }

    public static void clearInput(){
        input.clear();
    }
}