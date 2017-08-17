package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;

public enum DBDAOFactory {

    INSTANCE;

    private final EntityDAO userEntityDAO = new UserEntityDAO();
    private final EntityDAO groupEntityDAO = new GroupEntityDAO();
    private final EntityDAO contactEntityDAO = new ContactEntityDAO();
    private final AdminDAO adminEntityDAO = new CustomAdminDAO();

    public EntityDAO getEntityDAO(Class<? extends EntityAncestor> entityAncestor) throws CustomException {
        if (entityAncestor.equals(User.class)) {
            return userEntityDAO;
        } else if (entityAncestor.equals(Contact.class)) {
            return contactEntityDAO;
        } else if (entityAncestor.equals(Group.class)) {
            return groupEntityDAO;
        }
        throw new CustomException("no such adminDAO");
    }

    public AdminDAO getAdminDAO() {
        return adminEntityDAO;
    }

}
