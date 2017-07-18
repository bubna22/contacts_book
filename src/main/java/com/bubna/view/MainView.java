package com.bubna.view;

import com.bubna.controller.CommandController;
import com.bubna.utils.javaFX.Console;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by test on 12.07.2017.
 */
public class MainView implements Observer {

    private Console console;

    public MainView(BorderPane rootPane) {
        console = new Console(CommandController.INSTANCE::listen);

        StackPane sp = new StackPane();
        sp.getChildren().add(console);
        sp.setMaxWidth(400);
        sp.setMinWidth(400);
        sp.setPrefWidth(400);

        rootPane.setCenter(sp);
    }

    @Override
    public void update(Observable o, Object data) {
        console.setConsoleMode(false);
        if (!(data instanceof Exception)) {
            console.appendText("\n>:received message");
        }
        console.setConsoleMode(true);
    }
}
