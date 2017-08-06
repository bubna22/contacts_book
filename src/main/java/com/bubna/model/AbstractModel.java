package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

abstract class AbstractModel <V extends EntityAncestor> implements Model<V> {
    protected ObservablePart observable;
    protected DAOFactory daoFactory;
    protected DAO dao;

    protected User acc;

    private void getAcc() {
        acc = new User("bubna", "test", "1");
    }

    @Override
    public void rem(V entity) {
        getAcc();
        try {
            dao.modify(acc, entity.getName(), null);
        } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
            applyException(e);
        }

    }

    @Override
    public void list(V entity) {
        getAcc();
    }

    @Override
    public void modify(V entity) {
        getAcc();
        try {
            dao.modify(acc, entity.getName(), entity);
        } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
            applyException(e);
        }
    }

    public void applyException(Exception e) {
        observable.notifyObservers(e);
    }
}
