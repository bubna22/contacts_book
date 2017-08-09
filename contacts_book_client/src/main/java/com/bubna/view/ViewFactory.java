package com.bubna.view;

import com.bubna.Init;
import com.bubna.controller.EntityController;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Observable;

public enum ViewFactory {

    INSTANCE;

    private WebEngine webEngine;
    private ContactView contactView;
    private GroupView groupView;
    private UserView userView;

    public synchronized void addView(Observable observable, EntityController entityController, Class<? extends EntityAncestor> eClass) {
        if (eClass.equals(Contact.class)) {
            contactView = new ContactView("contactView");
            contactView.setContainerCSSSelector("div.col-md-7.bubna-col>div.table-responsive>table.table-hover");
            contactView.setController(entityController);
            contactView.setWebEngine(webEngine);
            observable.addObserver(contactView);
        } else if (eClass.equals(Group.class)) {
            groupView = new GroupView("groupView");
            groupView.setContainerCSSSelector("div.col-md-5.bubna-col>div.table-responsive>table.table-hover");
            groupView.setController(entityController);
            groupView.setWebEngine(webEngine);
            observable.addObserver(groupView);
        } else if (eClass.equals(User.class)) {
            userView = new UserView("userView");
            userView.setContainerCSSSelector(null);
            userView.setController(entityController);
            userView.setWebEngine(webEngine);
            observable.addObserver(userView);
        }
    }

    public synchronized void Init(StackPane root) {
        try {
            WebView webview = new WebView();
            webview.setVisible(true);
            webEngine = webview.getEngine();
            webEngine.setJavaScriptEnabled(true);
            webEngine.load(getClass().getResource("/index.html").toExternalForm());

            webEngine.setOnAlert(event -> {
                if("command:ready".equals(event.getData())){

                }
            });

            root.getChildren().add(webview);
        } catch (Exception ex) {
            System.err.print("error " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
