package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient extends Application {
    @FXML
    private  Button disc_button;
    @FXML
    private Button connect_button;
    @FXML
    private TextArea chat_area;
    @FXML
    private TextField tf_chat;
    @FXML
    private TextField name_tf;
    @FXML
    private TextField port_tf;

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public void connectAction(ActionEvent actionEvent) throws Exception {
        chat_area.setEditable(false);
        Socket socket;
        String serverAddress = "localhost";
        try {
             socket = new Socket(serverAddress, Integer.parseInt(port_tf.getText()));
        }catch (Exception ex){
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Button button = new Button("Ok");
            VBox vbox = new VBox(new Text("Wrong port!Input port again!"), button);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new javafx.geometry.Insets(20));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();
                }
            });
            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            return;
        }
        chat_area.appendText("You are connected to server with port:"+port_tf.getText()+"!\n");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        Thread readerThread = new Thread(() -> {
            try {
                while (true) {
                    String line = in.readLine();
                    if (line.startsWith("Sendname")) {
                        out.println(name_tf.getText());
                    } else if (line.startsWith("Nameisaccepted")) {
                        name_tf.setEditable(false);
                        connect_button.setVisible(false);
                    } else if (line.startsWith("Message")) {
                        chat_area.appendText(line.substring(8) + "\n");
                    }
                }
            } catch (IOException e) {
                chat_area.appendText("\nServer is offline!");
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

    public void sendAction(ActionEvent actionEvent) {
        out.println(tf_chat.getText());
        tf_chat.setText("");
    }

}
