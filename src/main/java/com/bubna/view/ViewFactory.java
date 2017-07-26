package com.bubna.view;

import com.bubna.controller.EntityController;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Observable;

public enum ViewFactory {

    INSTANCE;

    private WebEngine webEngine;
    private ContactView contactView;
    private GroupView groupView;

    public void addView(Observable observable, Class<? extends EntityAncestor> eClass) {
        if (eClass.equals(Contact.class)) {
            contactView = new ContactView("contactView");
            contactView.setContainerCSSSelector("div.col-md-7.bubna-col>div.table-responsive>table.table-hover");
            contactView.setController(new EntityController<Contact>());
            contactView.setWebEngine(webEngine);
            observable.addObserver(contactView);
        } else if (eClass.equals(Group.class)) {
            groupView = new GroupView("groupView");
            groupView.setContainerCSSSelector("div.col-md-5.bubna-col>div.table-responsive>table.table-hover");
            groupView.setController(new EntityController<Group>());
            groupView.setWebEngine(webEngine);
            observable.addObserver(groupView);
        }
    }

    public void Init(StackPane root) {
        try {
            WebView webview = new WebView();
            webview.setVisible(true);
            webEngine = webview.getEngine();
            webEngine.setJavaScriptEnabled(true);
            webEngine.load(getClass().getResource("/index.html").toExternalForm());

            webEngine.setOnAlert(event -> {
                if("command:ready".equals(event.getData())){
                    contactView.actionJS("list", null);
                    groupView.actionJS("list", null);
                }
            });

            root.getChildren().add(webview);
        } catch (Exception ex) {
            System.err.print("error " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
