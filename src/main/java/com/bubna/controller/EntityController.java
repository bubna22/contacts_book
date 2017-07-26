package com.bubna.controller;

import com.bubna.model.StorageModel;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;

public class EntityController <V extends EntityAncestor> implements Controller<V> {

    @Override
    public void listen(String action, V entity) {
        switch (action) {
            case "list":
                StorageModel.getInstance().list(entity);
                break;
            case "modify":
                StorageModel.getInstance().modify(entity);
                break;
            case "rem":
                StorageModel.getInstance().rem(entity);
                break;
        }
    }
}
