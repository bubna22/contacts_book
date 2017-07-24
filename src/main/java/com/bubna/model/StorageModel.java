package com.bubna.model;

import com.bubna.controller.CommandController;
import com.bubna.dao.AbstractFactory;
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
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by test on 11.07.2017.
 * part of pattern mvc - Model; pattern Singleton used
 */

public enum StorageModel {

    INSTANCE;

    /**
     * observaplePart instance
     */
    private ObservablePart observable;

    /**
     * class extends Observable; i can't use object of Observable cause observable.setChanged() has protected access
     */
    private class ObservablePart extends Observable {
        protected void setChanged() {
            super.setChanged();
        }
    }

    /**
     * get singleton of Observable part
     * @return Observable instance
     */
    public Observable getObservable() {
        if (observable == null)
            observable = new ObservablePart();
        observable.setChanged();
        return observable;
    }

    private DAOFactory currentFactory;

    public void setFactory(String factory) {
        try {
            currentFactory =
                    AbstractFactory.INSTANCE.getFactory(AbstractFactory.SourceType
                            .valueOf(factory));
        } catch (InitException e) {
            applyException(e);
            return;
        }
        applyString("initialization is successful;");
    }

    public void modify(EntityAncestor entityAncestor) {
        if (currentFactory == null) {
            applyException(new InitException("choose factory first; see help;"));
            return;
        }
        DAO d = null;
        try {
            d = currentFactory.getDAO(currentFactory.getSource(), entityAncestor.getClass());
            d.modify(entityAncestor.getName(), entityAncestor);
        } catch (InitException | URISyntaxException | IncorrectInputException | IOException | NoSuchElementException e) {
            applyException(e);
        }
    }

    public void list(EntityAncestor entityAncestor) {
        DAO d = null;
        DAO d1 = null;
        try {
            d = currentFactory.getDAO(currentFactory.getSource(), entityAncestor.getClass());
            if (entityAncestor instanceof Group)
                getObservable().notifyObservers(d.list(o -> true));
            else if (entityAncestor instanceof Contact) {
                Contact c = (Contact) entityAncestor;

                if (c.getGroupName() != null && c.getGroupName().equals("")) {
                    d1 = currentFactory.getDAO(currentFactory.getSource(), Group.class);
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
            d = currentFactory.getDAO(currentFactory.getSource(), entityAncestor.getClass());
            getObservable().notifyObservers(d.get(entityAncestor.getName()));
        } catch (InitException | URISyntaxException | IncorrectInputException | NoSuchElementException e) {
            applyException(e);
        }
    }

    /**
     * need to transfer some information from Controller and Model excepting DAO
     * @param s some string
     */
    public void applyString(String s) {
        getObservable().notifyObservers(s);
    }

    /**
     * need to transfer exceptions to view
     * @param e
     */
    public void applyException(Exception e) {
        getObservable().notifyObservers(e);
    }

}
