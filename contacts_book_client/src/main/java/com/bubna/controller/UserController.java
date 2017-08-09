package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;

public class UserController extends EntityController {

    public UserController(Model model) {
        super(model);
    }

    @Override
    public void listen(String action, EntityAncestor entity) {
        storageModel.prepare();
        switch (action) {
            case "login":
                storageModel.login((User) entity);
                break;
            case "unlogin":
                storageModel.unlogin((User) entity);
                break;
        }
    }
}
