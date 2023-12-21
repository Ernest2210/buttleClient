package org.client.connection;

import javafx.stage.Stage;
import org.client.enums.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerConnection extends Thread{
    private static ServerConnection serverConnection;
    private Socket socket;
    private List<String> messages;
    private Stage stage;
    private Map<String, MessageCallback> callbacks;

    private ServerConnection() throws IOException {
        this.socket = new Socket("localhost", 4000);
        this.callbacks = new HashMap<>();
        this.messages = new LinkedList<>();
        this.start();
    }

    public static ServerConnection getServerConnection(){
        if(serverConnection == null){
            try {
                serverConnection = new ServerConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return serverConnection;
    }

    public void clearCallbacks(){
        this.callbacks.clear();
    }

    public void addCallback(MessageType name, MessageCallback callback){
        this.callbacks.put(name.toString(), callback);
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket("localhost", 4000);

            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (this.socket.isConnected()){
                messages.add(in.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getRequestBody(String message){
        Map<String, String> body = new HashMap<>();
        String[] params = message.split(";");
        for (String param: params){
            body.put(param.split("=")[0], param.split("=")[1]);
        }
        return body;
    }

    public void executeFromMessages(){
        for(String message: messages){
            messages.remove(message);
            System.out.println(message);
            String messageType = message.split("&")[0];
            String messageBody = "";
            Map<String, String> data = new HashMap<>();
            if (message.split("&").length == 2){
                messageBody = message.split("&")[1];
                data = getRequestBody(messageBody);
            }

            if(callbacks.get(messageType) != null){
                callbacks.get(messageType).callback(data);
            }
        }
    }

    public void sendCreateRoomMessage(){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            out.write("createRoom&\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendConnectToRoom(String roomId){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            out.write("connectToRoom&room=" + roomId + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMove(Map<String, String> data){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String request = MessageType.makeMove + "&x_coord=" + data.get("x_coord") +
                    ";y_coord=" + data.get("y_coord") + ";direction=" + data.get("direction");

            out.write(request + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendShoot(Map<String, String> data){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String request = MessageType.makeShot + "&x_coord=" + data.get("x_coord") +
                    ";y_coord=" + data.get("y_coord") + ";direction=" + data.get("direction");

            out.write(request + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendGameOver(Map<String, String> data){
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String request = MessageType.gameOver + "&lose_type=" + data.get("lose_type");

            out.write(request + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
