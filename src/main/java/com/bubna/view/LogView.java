package com.bubna.view;

import com.bubna.entities.EntityAncestor;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.*;

/**
 * Created by test on 15.07.2017.
 */
public class LogView implements Observer {
    private TextArea ta;

    /**
     * LogView constructor
     * @param rootPane - container for TextArea ta {@link com.bubna.view.LogView}
     */
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

    /**
     * Interface Observer method; used to display info from StorageModel {@link com.bubna.model.StorageModel}
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
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
