package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ChatServer extends Application {
    private int PORT;
    private static HashSet<String> names = new HashSet<>();
    private static HashSet<PrintWriter> writers = new HashSet<>();
    @FXML
    private TextArea chat_area;
    @FXML
    private TextField port_tf;
    @FXML
    private Button on_button;
    private Thread listenerThread;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }


    public void onServerAction(ActionEvent actionEvent) throws Exception {
        if (!port_tf.getText().isEmpty() && port_tf.getText().matches("[0-9]*")) {
            PORT = Integer.parseInt(port_tf.getText());
            chat_area.setEditable(false);
            chat_area.appendText("Server with port " + port_tf.getText() + " is alive!");
            final ServerSocket listener = new ServerSocket(PORT);
            listenerThread = new Thread(() -> {
                try {
                    while (true) {
                        Thread thread = new Thread(new ClientHandler(listener.accept()));
                        thread.setDaemon(true);
                        thread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();
            on_button.setVisible(false);
        } else {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Button button = new Button("Ok");
            VBox vbox = new VBox(new Text("Wrong port!Input port again!"), button);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();
                }
            });
            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
        }
    }


    public void usersServerAction(ActionEvent actionEvent) {
        chat_area.appendText("\nOnline users:");
        for (String user : names) {
            chat_area.appendText(user + ",");
        }
    }


    public void offServerAction(ActionEvent actionEvent) {
        chat_area.appendText("\nServer is starting to die!Wait 3 seconds!");
        try {
            Thread.sleep(3000);
            Platform.exit();
        } catch (InterruptedException ex) {
        }
    }

    private class ClientHandler implements Runnable {

        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }


        public void run() {

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    out.println("Sendname");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }
                out.println("Nameisaccepted");
                writers.add(out);
                Platform.runLater(() -> {
                    chat_area.appendText("\n User " + name + " is connected to server!");
                });
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("Message" + " " + name + ": " + input);
                        Platform.runLater(() -> {
                            chat_area.appendText("\n" + name + " said:" + input);
                        });

                    }
                }
            } catch (IOException e) {
                System.out.println(e);
                Platform.runLater(() -> {
                    chat_area.appendText("\n" + name + " user was disconnected!");
                });
            } finally {
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
