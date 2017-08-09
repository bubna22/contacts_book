package com.bubna.controller.server;

import com.bubna.controller.Controller;
import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.utils.TransferObject;

public class ServerController implements Controller {

    private Handler handler;

    public ServerController(Model modelContact, Model modelGroup, Model modelUser) {
        handler = new UserHandler(modelUser);
        Handler contactHandler = new ContactHandler(modelContact);
        handler.setNext(contactHandler);
        contactHandler.setNext(new GroupHandler(modelGroup));
    }

    @Override
    public void listen(String cmd) throws CustomException {
        TransferObject transferObject = TransferObject.deserialize(cmd);
        handler.handle(transferObject);
    }
}
