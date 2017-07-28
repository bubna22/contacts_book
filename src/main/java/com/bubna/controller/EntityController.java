package com.bubna.controller;

import com.bubna.model.Model;
import com.bubna.model.entities.EntityAncestor;

public class EntityController <V extends EntityAncestor> implements Controller<V> {

    private Model storageModel;

    public EntityController(Model model) {
        storageModel = model;
    }

    @Override
    public void listen(String action, V entity) {
        switch (action) {
            case "list":
                storageModel.list(entity);
                break;
            case "modify":
                storageModel.modify(entity);
                break;
            case "rem":
                storageModel.rem(entity);
                break;
        }
    }
}
