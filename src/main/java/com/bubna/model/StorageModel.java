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

    /**
     * make to remove duplicate code from other realization of apply
     * @param action wich action is putted by user
     * @param object is TransferObject between Model and View
     */
    private void apply(CommandController.Action action, EntityAncestor object) {
        if (currentFactory == null) {
            applyException(new InitException("choose factory first; see help;"));
            return;
        }
        DAO d = null;
        try {
            d = currentFactory.getDAO(currentFactory.getSource(), object.getClass());
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
            return;
        }
        try {
            switch (action) {
                case ADD:
                    d.modify(object.getName(), object);
                    break;
                case REM:
                    d.modify(object.getName(), null);
                    break;
                case EDIT:
                    d.modify(object.getName(), object);
                    break;
                case LIST:
                    if (object instanceof Group)
                        getObservable().notifyObservers(d.list(o -> true));
                    else if (object instanceof Contact) {
                        Contact c = (Contact) object;
                        getObservable().notifyObservers(d.list(o -> (c.getGroupName() == null) ||
                                (((Contact) o).getGroupName() != null &&
                                        c.getGroupName().equals(((Contact) o).getGroupName()))));
                    }
                    break;
                case VIEW:
                    getObservable().notifyObservers(d.get(object.getName()));
                    break;
            }
        } catch (InitException | NoSuchElementException | IOException | IncorrectInputException e) {
            applyException(e);
        }
    }

    /**
     * apply action from controller; see controller{@link com.bubna.controller.CommandController}
     * @param entity entity inputted by user
     * @param action action inputted by user
     * @param variables variables inputted by user
     */
    public void apply(CommandController.Entity entity,
                      CommandController.Action action,
                      HashMap<CommandController.Action.Variable, Object> variables) {
        switch (entity) {
            case APP:
                try {
                    currentFactory =
                            AbstractFactory.INSTANCE.getFactory(AbstractFactory.SourceType
                                    .valueOf((String) variables.get(CommandController.Action.Variable.FACTORY)));
                } catch (InitException e) {
                    applyException(e);
                    return;
                }
                applyString("initialization is successful;");
                return;
            case GROUP:
                Group g = new Group(
                        (String) variables.get(CommandController.Action.Variable.GROUP_NAME),
                        (Integer) variables.get(CommandController.Action.Variable.GROUP_COLOR));
                apply(action, g);
                break;
            case CONTACT:
                if (variables.containsKey(CommandController.Action.Variable.GROUP_NAME)) {
                    DAO d;
                    try {
                        d = currentFactory.getDAO(currentFactory.getSource(), Group.class);
                    } catch (InitException | URISyntaxException | IncorrectInputException e) {
                        applyException(e);
                        return;
                    }
                    try {
                        d.get(variables.get(CommandController.Action.Variable.GROUP_NAME));
                    } catch (Exception e) {
                        applyException(e);
                        return;
                    }
                }
                Contact c = new Contact(
                        (String) variables.get(CommandController.Action.Variable.CONTACT_NAME),
                        (String) variables.get(CommandController.Action.Variable.CONTACT_EMAIL),
                        (Integer) variables.get(CommandController.Action.Variable.CONTACT_NUM),
                        (String) variables.get(CommandController.Action.Variable.CONTACT_SKYPE),
                        (String) variables.get(CommandController.Action.Variable.CONTACT_TELEGRAM),
                        (String) variables.get(CommandController.Action.Variable.GROUP_NAME));
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
