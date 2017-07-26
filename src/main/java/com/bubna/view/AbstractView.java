package com.bubna.view;

import com.bubna.controller.Controller;
import com.bubna.model.entities.EntityAncestor;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.Observable;

abstract class AbstractView<V extends EntityAncestor> implements Viewable<V> {

    private Controller controller;
    private WebEngine webEngine;
    private String cssSelector;

    protected String jsName;

    protected abstract V fromHtml(String html);
    protected abstract String toHtml(V entity);
    protected abstract boolean checkType(Object obj);

    AbstractView(String jsName) {
        this.jsName = jsName;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!checkType(arg)) return;
        if (arg instanceof Exception) {
            webEngine.executeScript("alert('" + arg.toString() + "')");
        } else if (arg instanceof ArrayList) {
            ArrayList<V> list = (ArrayList<V>) arg;
            for (int i = 0; i < list.size(); i++) {
                V entity = list.get(i);
                if (!checkType(entity)) return;
                webEngine.executeScript("remElem('" + cssSelector + "', '" + entity.getName() + "')");
                webEngine.executeScript("addElem('" + cssSelector + "', '" + toHtml(entity) + "')");
            }
        } else {
            V entity = (V) arg;
            webEngine.executeScript("remElem('" + cssSelector + "', '" + entity.getName() + "')");
            webEngine.executeScript("addElem('" + cssSelector + "', '" + toHtml(entity) + "')");
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
}
