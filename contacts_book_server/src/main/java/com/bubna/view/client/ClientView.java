package com.bubna.view.client;

import com.bubna.controller.Controller;
import com.bubna.exception.CustomException;
import com.bubna.utils.TransferObject;
import com.bubna.view.View;
import javafx.scene.control.TextArea;

import java.util.Observable;

public enum ClientView implements View {

    INSTANCE;

    private TextArea textArea;
    private Controller controller;

    @Override
    public void setData(int key, Object data) {
        if (key == View.TEXT_AREA) this.textArea = (TextArea) data;
        else if (key == View.CONTROLLER) this.controller = (Controller) data;
    }

    public void close() throws CustomException {
        controller.listen("unlogin_all");
    }

    @Override
    public void update(Observable o, Object arg) {
        TransferObject transferObject = (TransferObject) arg;
        textArea.appendText("-------------------\n");
        textArea.appendText("who: " + transferObject.getWho() + "\n");
        textArea.appendText("what: " + transferObject.getWhat() + "\n");
        textArea.appendText("data: " + transferObject.getData() + "\n");
    }
}
