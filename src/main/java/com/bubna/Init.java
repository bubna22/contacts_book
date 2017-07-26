package com.bubna;

import com.bubna.dao.AbstractFactory;
import com.bubna.model.ObservablePart;
import com.bubna.model.StorageModel;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.Group;
import com.bubna.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Observable;

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
        StackPane root = new StackPane();
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);

        Scene scene = new Scene(root,800,400);

        Observable observable = StorageModel.getInstance().getObservable();

        ViewFactory.INSTANCE.Init(root);
        ViewFactory.INSTANCE.addView(observable, Contact.class);
        ViewFactory.INSTANCE.addView(observable, Group.class);

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
