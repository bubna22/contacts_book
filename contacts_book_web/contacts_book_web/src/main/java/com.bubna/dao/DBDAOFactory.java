package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;

import java.util.HashMap;

public enum DBDAOFactory {

    INSTANCE;

    private final DAO userDAO = new UserDAO();
    private final DAO groupDAO = new GroupDAO();
    private final DAO contactDAO = new ContactDAO();

    public DAO getDAO(Class<? extends EntityAncestor> entityAncestor) throws CustomException {
        if (entityAncestor.equals(User.class)) {
            return userDAO;
        } else if (entityAncestor.equals(Contact.class)) {
            return contactDAO;
        } else if (entityAncestor.equals(Group.class)) {
            return groupDAO;
        }
        throw new CustomException("no such dao");
    }

}
