package com.bubna.model;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.User;

public interface Model<V extends EntityAncestor> {

    void prepareDAO() throws CustomException;

    void login(User user) throws CustomException;
    void unlogin(User user) throws CustomException;
    void list(User user, V entity) throws CustomException;
    void modify(User user, String name, V entity) throws CustomException;
}
