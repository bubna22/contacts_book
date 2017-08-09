package com.bubna.view;

import com.bubna.controller.Controller;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.Observable;

abstract class AbstractView<V extends EntityAncestor> implements Viewable<V> {

    private Controller controller;
    WebEngine webEngine;
    private String cssSelector;

    String jsName;

    protected V fromHtml(String html) {return null;}
    protected String toHtml(V entity) {return null;}
    protected boolean checkType(Object obj) {return false;}
    protected void parseEntity(V entity) {
        if (!checkType(entity)) return;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webEngine.executeScript("remElem('" + cssSelector + "', '" + entity.getName() + "')");
                webEngine.executeScript("addElem('" + cssSelector + "', '" + toHtml(entity) + "')");
            }
        });
    }
    protected void parseEntitiesArray(ArrayList<V> args) {
        for (int i = 0; i < args.size(); i++) {
            V entity = args.get(i);
            parseEntity(entity);
        }
    }

    AbstractView(String jsName) {
        this.jsName = jsName;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!checkType(arg)) return;
        if (arg instanceof Exception) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    webEngine.executeScript("addError('" + ((Exception) arg).getMessage() + "')");
                }
            });
        } else if (arg instanceof ArrayList) {
            parseEntitiesArray((ArrayList<V>) arg);
        } else {
            V entity = (V) arg;
            parseEntity(entity);
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember(jsName, this);
    }

    @Override
    public void setContainerCSSSelector(String cssSelector) {
        this.cssSelector = cssSelector;
    }

    public void actionJS(String action, String data) {
        V entity = fromHtml(data);
        controller.listen(action, entity);
    }

    public void setUser(String login,  String pass, String ip) {
        controller.listen("set_user", new User(login, pass, ip));
    }
}
