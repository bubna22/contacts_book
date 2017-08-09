package com.bubna.controller.server;

import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import com.bubna.utils.TransferObject;
import com.bubna.utils.UserGroupPair;

class GroupHandler implements Handler {

    private Handler next;
    private Model model;

    GroupHandler(Model model) {
        this.model = model;
    }

    @Override
    public void setNext(Handler handler) {
        this.next = handler;
    }

    @Override
    public void handle(TransferObject transferObject) throws CustomException {
        if (transferObject.getWho() == null || transferObject.getWhat() == null) throw new CustomException("incorrect transfer object;");
        if (!transferObject.getWho().equals("group")) {
            if (next != null) next.handle(transferObject);
            return;
        }
        switch (transferObject.getWhat()) {
            case "list":
                UserGroupPair customPair = (UserGroupPair) UserGroupPair.deserialize(transferObject.getData(), UserGroupPair.class);
                model.list(customPair.getV1(), customPair.getV2());
                break;
            case "modify":
                customPair = (UserGroupPair) UserGroupPair.deserialize(transferObject.getData(), UserGroupPair.class);
                model.modify(customPair.getV1(), customPair.getV2().getName(), customPair.getV2());
                break;
            default:
                throw new CustomException("no such action");
        }
    }
}
