package com.bubna.model;

import com.bubna.service.Service;
import com.bubna.service.ServiceFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;

import java.io.IOException;

abstract class AbstractModel <V extends EntityAncestor> implements Model<V> {
    protected ObservablePart observable;
    protected ServiceFactory serviceFactory;
    protected Service service;

    @Override
    public void rem(User user, V entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.modify(user, entity.getName(), null);
                } catch (InitException | IncorrectInputException | IOException | NoSuchElementException e) {
                    applyException(e);
                }
            }
        }).start();
    }

    @Override
    public void list(User u, V entity) {}

    @Override
    public void modify(User user, V entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.modify(user, entity.getName(), entity);
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
