package com.bubna.model;

import com.bubna.model.entities.EntityAncestor;

public interface Model<V extends EntityAncestor> {

    void rem(V entity);
    void modify(V entity);
    void list(V entity);
}
