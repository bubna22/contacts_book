package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;

import java.io.IOException;
import java.net.URISyntaxException;

abstract class AbstractModel <V extends EntityAncestor> implements Model<V> {
    protected ObservablePart observable;
    protected DAOFactory daoFactory;
    protected DAO dao;

    @Override
    public void rem(V entity) {
        try {
            dao.modify(entity.getName(), null);
        } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
            applyException(e);
        }

    }

    @Override
    public void modify(V entity) {
        try {
            dao.modify(entity.getName(), entity);
        } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
            applyException(e);
        }
    }

    public void applyException(Exception e) {
        observable.notifyObservers(e);
    }
}
