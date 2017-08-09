package com.bubna.controller.server;

import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.model.entity.User;
import com.bubna.utils.TransferObject;

public class UserHandler implements Handler {

    private Handler next;
    private Model model;

    UserHandler(Model model) {
        this.model = model;
    }

    @Override
    public void setNext(Handler handler) {
        this.next = handler;
    }
    @Override
    public void handle(TransferObject transferObject) throws CustomException {
        if (transferObject.getWho() == null || transferObject.getWhat() == null) throw new CustomException("incorrect transfer object;");
        if (!transferObject.getWho().equals("user")) {
            if (next != null) next.handle(transferObject);
            return;
        }
        switch (transferObject.getWhat()) {
            case "login":
                model.login(User.deserialize(transferObject.getData(), User.class));
                break;
            case "unlogin":
                model.unlogin(User.deserialize(transferObject.getData(), User.class));
                break;
            default:
                throw new CustomException("no such action");
        }
    }
}
