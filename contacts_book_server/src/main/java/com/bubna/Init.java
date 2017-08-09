package com.bubna;

import com.bubna.controller.client.ClientController;
import com.bubna.exception.CustomException;
import com.bubna.server.ServerListener;
import com.bubna.server.SocketChecker;
import com.bubna.view.View;
import com.bubna.view.client.ClientView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Init extends Application {

    private static Thread slThread;
    private static Thread scThread;

    public Init() {}

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        primaryStage = new Stage();
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(
                event -> {
                    try {
                        ClientView.INSTANCE.close();
                    } catch (CustomException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
        );

        Scene scene = new Scene(root, 800, 400);

        TextArea textArea = new TextArea();
        root.getChildren().add(textArea);
        ClientView.INSTANCE.setData(View.CONTROLLER, new ClientController());
        ClientView.INSTANCE.setData(View.TEXT_AREA, textArea);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("work hard, don't play");
        slThread = new Thread(() -> {
            while (!ServerListener.INSTANCE.isClosed()) {
                try {
                    ServerListener.INSTANCE.listen();
                } catch (CustomException e) {
                    e.printStackTrace();//TODO: logs
                }
            }
        });
        scThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();//TODO: logs
                }
                SocketChecker.INSTANCE.ping();
            }
        });
        slThread.start();
        scThread.start();
        launch(args);
    }

}
