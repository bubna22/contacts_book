package com.bubna.model;

import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;

public interface Model<V extends EntityAncestor> {

    void prepare();
    default void login(User acc) {}
    default void unlogin(User acc) {}
    void rem(V entity);
    void modify(V entity);
    void list(V entity);
}
