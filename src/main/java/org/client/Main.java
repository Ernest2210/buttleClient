package org.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.client.connection.ServerConnection;

import java.net.URL;

public class Main extends Application {
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

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/gameScene.fxml");
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
}