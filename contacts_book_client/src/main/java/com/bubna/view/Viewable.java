package com.bubna.view;

import com.bubna.controller.Controller;
import com.bubna.model.entities.EntityAncestor;
import javafx.scene.web.WebEngine;

import java.util.Observer;

public interface Viewable<V extends EntityAncestor> extends Observer {

    void setController(Controller controller);
    void setWebEngine(WebEngine webEngine);
    void setContainerCSSSelector(String cssSelector);
}
