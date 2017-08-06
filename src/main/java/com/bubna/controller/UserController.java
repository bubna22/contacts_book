package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entities.User;

public class UserController extends EntityController<User> {

    public UserController(Model model) {
        super(model);
    }

    @Override
    public void listen(String action, User entity) {
        storageModel.prepare();
        switch (action) {
            case "login":
                storageModel.login(entity);
                break;
            case "unlogin":
                storageModel.unlogin(entity);
                break;
        }
    }
}
