package com.bubna;

import com.bubna.dao.AbstractFactory;
import com.bubna.model.ObservablePart;
import com.bubna.model.StorageModel;
import com.bubna.view.LogView;
import com.bubna.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by bubna on 11.07.2017.
 *
 */
public class Init extends Application {

    public Init() {}

    /**
     * start JavaFX program; create MainView and LogView
     * @param primaryStage from superclass
     */

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        primaryStage.setResizable(false);

        Scene scene = new Scene(root,800,400);

        StorageModel.getInstance().getObservable().addObserver(new MainView(root));
        StorageModel.getInstance().getObservable().addObserver(new LogView(root));

        root.setMinWidth(300);
        root.setPrefWidth(300);
        root.setMaxWidth(300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("work hard, don't play");
        String factory = args.length==0?"sax":args[0];
        StorageModel.init(
                AbstractFactory.INSTANCE.getFactory(AbstractFactory.SourceType.valueOf(factory.toUpperCase())),
                new ObservablePart()
        );
        launch(args);
    }

}
