package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.FSDAOFactory;
import com.bubna.entities.Contact;
import com.bubna.entities.EntityAncestor;
import com.bubna.entities.Group;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Observable;

import static com.bubna.controller.CommandController.*;

/**
 * Created by test on 11.07.2017.
 */
public enum StorageModel {

    INSTANCE;

    private ObservablePart observable;
    private class ObservablePart extends Observable {
        protected void setChanged() {
            super.setChanged();
        }
    }

    public Observable getObservable() {
        if (observable == null)
            observable = new ObservablePart();
        observable.setChanged();
        return observable;
    }

    private void apply(Action action, EntityAncestor object) {
        DAO d = null;
        try {
            d = FSDAOFactory.INSTANCE.getDAO(FSDAOFactory.INSTANCE.getSource(),
                    object.getClass());
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
            return;
        }
        try {
            switch (action) {
                case ADD:
                    d.add(object);
                    break;
                case REM:
                    d.rem(object);
                    break;
                case EDIT:
                    d.edit(object);
                    break;
                case LIST:
                    if (object instanceof Group)
                        getObservable().notifyObservers(d.list(o -> true));
                    else if (object instanceof Contact) {
                        Contact c = (Contact) object;
                        getObservable().notifyObservers(d.list(o -> (c.getGroupName() == null) ||
                                c.getGroupName().equals(((Contact) o).getGroupName())));
                    }
                    break;
                case VIEW:
                    getObservable().notifyObservers(d.get(object.getName()));
                    break;
            }
        } catch (InitException | NoSuchElementException | IOException e) {
            applyException(e);
        }
    }

    public void apply(Entity entity,
                      Action action,
                      HashMap<Action.Variable, Object> variables) {
        switch (entity) {
            case GROUP:
                Group g = new Group(
                        (String) variables.get(Action.Variable.GROUP_NAME),
                        (Integer) variables.get(Action.Variable.GROUP_COLOR));
                apply(action, g);
                break;
            case CONTACT:
                if (variables.containsKey(Action.Variable.GROUP_NAME)) {
                    DAO d;
                    try {
                        d = FSDAOFactory.INSTANCE.getDAO(FSDAOFactory.INSTANCE.getSource(), Group.class);
                    } catch (InitException | URISyntaxException | IncorrectInputException e) {
                        applyException(e);
                        return;
                    }
                    try {
                        d.get(variables.get(Action.Variable.GROUP_NAME));
                    } catch (Exception e) {
                        applyException(e);
                        return;
                    }
                }
                Contact c = new Contact(
                        (String) variables.get(Action.Variable.CONTACT_NAME),
                        (String) variables.get(Action.Variable.CONTACT_EMAIL),
                        (Integer) variables.get(Action.Variable.CONTACT_NUM),
                        (String) variables.get(Action.Variable.CONTACT_SKYPE),
                        (String) variables.get(Action.Variable.CONTACT_TELEGRAM),
                        (String) variables.get(Action.Variable.GROUP_NAME));
                apply(action, c);
                break;
        }
    }

    public void applyString(String s) {
        getObservable().notifyObservers(s);
    }

    public void applyException(Exception e) {
        getObservable().notifyObservers(e);
    }

}
