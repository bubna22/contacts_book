package com.bubna.dao;

import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.utils.Utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
enum FSDAOFactory implements DAOFactory<File> {

    INSTANCE;

    @Override
    public DAO getDAO(File source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException {
        if (daoType.equals(Contact.class)) {
            return FSEntityAncestorDAO.CONTACT.setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return FSEntityAncestorDAO.GROUP.setUpdatedSource(source);
        }
        throw new IncorrectInputException("Invalid format of requested data");
    }

    @Override
    public File getSource() throws InitException, URISyntaxException {
        File f = Utils.getRootDir();
        if (!f.exists()) f.mkdirs();
        return f;
    }
}
