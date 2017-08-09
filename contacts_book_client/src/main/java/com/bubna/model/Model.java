package com.bubna.model;

import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;

public interface Model<V extends EntityAncestor> {

    void prepare();
    default void login(User acc) {}
    default void unlogin(User acc) {}
    void rem(User u, V entity);
    void modify(User u, V entity);
    void list(User u, V entity);
}
