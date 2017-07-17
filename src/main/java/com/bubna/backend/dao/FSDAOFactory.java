package com.bubna.backend.dao;

import com.bubna.entities.Contact;
import com.bubna.entities.EntityAncestor;
import com.bubna.entities.Group;
import com.bubna.exceptions.InitException;
import com.bubna.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public enum FSDAOFactory implements DAOFactory<File> {

    INSTANCE;

    @Override
    public DAO getDAO(File source, Class<? extends EntityAncestor> daoType) {
        if (daoType.equals(Contact.class)) {
            return FSEntityAncestorDAO.CONTACT.setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return FSEntityAncestorDAO.GROUP.setUpdatedSource(source);
        }
        return null;
    }

    @Override
    public File getSource() throws InitException, URISyntaxException {
        File f = new File(Utils.getRootDir() + "/storage");
        if (!f.exists()) f.mkdirs();
        return f;
    }
}
