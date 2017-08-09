package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;

public class EntityController implements Controller<EntityAncestor> {

    protected Model storageModel;
    private User user;

    public EntityController(Model model) {
        storageModel = model;
    }

    @Override
    public void listen(String action, EntityAncestor entity) {
        storageModel.prepare();
        switch (action) {
            case "list":
                storageModel.list(user, entity);
                break;
            case "modify":
                storageModel.modify(user, entity);
                break;
            case "rem":
                storageModel.rem(user, entity);
                break;
            case "set_user":
                user = (User) entity;
        }
    }
}
