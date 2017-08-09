package com.bubna.controller;

import com.bubna.model.entities.EntityAncestor;

public interface Controller<V extends EntityAncestor> {

    void listen(String action, V entity);
}
