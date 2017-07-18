package com.bubna;

import com.bubna.model.StorageModel;
import com.bubna.view.LogView;
import com.bubna.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by test on 11.07.2017.
 */
public class Init extends Application {

    public Init() {}

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        primaryStage.setResizable(false);

        Scene scene = new Scene(root,800,400);

        StorageModel.INSTANCE.getObservable().addObserver(new MainView(root));
        StorageModel.INSTANCE.getObservable().addObserver(new LogView(root));

        root.setMinWidth(300);
        root.setPrefWidth(300);
        root.setMaxWidth(300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("work hard, don't play");
        launch(args);
    }

}
