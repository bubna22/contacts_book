package com.bubna.frontend;

import com.bubna.backend.CommandController;
import com.bubna.entities.EntityAncestor;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by test on 15.07.2017.
 */
public class LogView implements Observer {
    private TextArea ta;

    public LogView(BorderPane rootPane) {
        StackPane sp = new StackPane();
        ta = new TextArea();
        ta.setEditable(false);
        sp.getChildren().add(ta);
        sp.setMaxWidth(400);
        sp.setMinWidth(400);
        sp.setPrefWidth(400);

        rootPane.setRight(sp);
    }

    @Override
    public void update(Object data) {
        ta.appendText("\n/*-----------next command----------*/\n");
        if (data instanceof String) {
            ta.appendText((String) data);
        } else if (data instanceof Exception) {
            Exception e = (Exception) data;
            ta.appendText(e.getClass().getName() + " " + (e.getMessage()!=null?e.getMessage():""));
        } else if (data instanceof ArrayList) {
            ArrayList<EntityAncestor> arrs = (ArrayList<EntityAncestor>) data;
            arrs.forEach(o -> ta.appendText(o.toString()));
        } else if (data instanceof EntityAncestor) {
            ta.appendText(data.toString());
        }
    }
}
