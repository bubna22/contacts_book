package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;

import java.io.IOException;
import java.util.function.Predicate;

abstract class AbstractModel <V extends EntityAncestor> implements Model<V> {
    protected ObservablePart observable;
    protected DAOFactory daoFactory;
    protected DAO dao;

    @Override
    public void rem(User u, V entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dao.modify(u, entity.getName(), null);
                } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
                    applyException(e);
                }
            }
        }).start();
    }

    @Override
    public void list(User u, V entity) {}

    @Override
    public void modify(User u, V entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dao.modify(u, entity.getName(), entity);
                } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
                    applyException(e);
                }
            }
        }
        ).start();
    }

    public synchronized void applyException(Exception e) {
        observable.notifyObservers(e);
    }
}
