package com.bubna.model;

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

import static com.bubna.controller.CommandController.*;

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

    /**
     * make to remove duplicate code from other realization of apply
     * @param action wich action is putted by user
     * @param object is TransferObject between Model and View
     */
    private void apply(Action action, EntityAncestor object) {
        DAO d = null;
        DAOFactory df = null;
        try {
            df = AbstractFactory.INSTANCE.getFactory(AbstractFactory.SourceType.FILE_SYSTEM);
            d = df.getDAO(df.getSource(), object.getClass());
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

    /**
     * apply action from controller; see controller{@link com.bubna.controller.CommandController}
     * @param entity entity inputted by user
     * @param action action inputted by user
     * @param variables variables inputted by user
     */
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
                    DAOFactory df;
                    try {
                        df = AbstractFactory.INSTANCE.getFactory(AbstractFactory.SourceType.FILE_SYSTEM);
                        d = df.getDAO(df.getSource(), Group.class);
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
