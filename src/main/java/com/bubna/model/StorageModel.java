package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;

/**
 * Created by test on 11.07.2017.
 * part of pattern mvc - Model; pattern Singleton used
 */

public class StorageModel {

    private static StorageModel instance;
    private ObservablePart observable;
    private DAOFactory factory;

    public static StorageModel getInstance() {
        return instance;
    }

    public static void init(DAOFactory factory, ObservablePart observable) throws InitException {
        if (instance != null) throw new InitException("storage model already exists");
        instance = new StorageModel();
        instance.observable = observable;
        instance.factory = factory;
    }

    /**
     * get singleton of Observable part
     * @return Observable instance
     */
    public Observable getObservable() {
        observable.setChanged();
        return observable;
    }

    public void modify(EntityAncestor entityAncestor) {
        DAO d = null;
        try {
            d = factory.getDAO(factory.getSource(), entityAncestor.getClass());
            d.modify(entityAncestor.getName(), entityAncestor);
        } catch (InitException | URISyntaxException | IncorrectInputException | IOException | NoSuchElementException e) {
            applyException(e);
        }
    }

    public void list(EntityAncestor entityAncestor) {
        DAO d = null;
        DAO d1 = null;
        try {
            d = factory.getDAO(factory.getSource(), entityAncestor.getClass());
            if (entityAncestor instanceof Group)
                getObservable().notifyObservers(d.list(o -> true));
            else if (entityAncestor instanceof Contact) {
                Contact c = (Contact) entityAncestor;

                if (c.getGroupName() != null && c.getGroupName().equals("")) {
                    d1 = factory.getDAO(factory.getSource(), Group.class);
                    Group sGroup = (Group) d1.get(c.getGroupName());
                }

                getObservable().notifyObservers(d.list(o -> (c.getGroupName() == null) ||
                        (((Contact) o).getGroupName() != null &&
                                c.getGroupName().equals(((Contact) o).getGroupName()))));
            }
        } catch (InitException | URISyntaxException | IncorrectInputException | NoSuchElementException e) {
            applyException(e);
        }
    }

    public void view(EntityAncestor entityAncestor) {
        DAO d = null;
        try {
            d = factory.getDAO(factory.getSource(), entityAncestor.getClass());
            getObservable().notifyObservers(d.get(entityAncestor.getName()));
        } catch (InitException | URISyntaxException | IncorrectInputException | NoSuchElementException e) {
            applyException(e);
        }
    }

    /**
     * need to transfer exceptions to view
     * @param e
     */
    public void applyException(Exception e) {
        getObservable().notifyObservers(e);
    }

    public void applyString(String e) {
        getObservable().notifyObservers(e);
    }

}
