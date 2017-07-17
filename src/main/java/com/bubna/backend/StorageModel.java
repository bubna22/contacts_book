package com.bubna.backend;

import com.bubna.backend.dao.DAO;
import com.bubna.backend.dao.FSDAOFactory;
import com.bubna.entities.Contact;
import com.bubna.entities.Group;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.exceptions.ObserverNotRegisteredException;
import com.bubna.frontend.Observer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

import static com.bubna.backend.CommandController.*;

/**
 * Created by test on 11.07.2017.
 */
public enum StorageModel implements Observable {
    INSTANCE;

    private HashSet<Observer> observers;

    StorageModel() {
        observers = new HashSet<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) throws ObserverNotRegisteredException {
        if (observers.contains(o)) {
            observers.remove(o);
        } else {
            throw new ObserverNotRegisteredException();
        }
    }

    @Override
    public void notifyObservers(Object data) {
        observers.forEach(observer -> observer.update(data));
    }

    void apply(Entity entity,
                      Action action,
                      HashMap<Action.Variable, Object> variables) {
        switch (entity) {
            case GROUP:
                Group g = new Group(
                        (String) variables.get(Action.Variable.GROUP_NAME),
                        (Integer) variables.get(Action.Variable.GROUP_COLOR));
                DAO d = null;
                try {
                    d = FSDAOFactory.INSTANCE.getDAO(FSDAOFactory.INSTANCE.getSource(), g.getClass());
                } catch (InitException | URISyntaxException e) {
                    applyException(e);
                    return;
                }
                try {
                    switch (action) {
                        case ADD:
                            d.add(g);
                            break;
                        case REM:
                            d.rem(g);
                            break;
                        case EDIT:
                            d.edit(g);
                            break;
                        case LIST:
                            notifyObservers(d.list(o -> true));
                            break;
                        case VIEW:
                            notifyObservers(d.get(g.getName()));
                            break;
                    }
                } catch (InitException | NoSuchElementException | IOException e) {
                    applyException(e);
                }
                break;
            case CONTACT:
                if (variables.containsKey(Action.Variable.GROUP_NAME)) {
                    try {
                        d = FSDAOFactory.INSTANCE.getDAO(FSDAOFactory.INSTANCE.getSource(), Group.class);
                    } catch (InitException | URISyntaxException e) {
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
                d = null;
                try {
                    d = FSDAOFactory.INSTANCE.getDAO(FSDAOFactory.INSTANCE.getSource(), c.getClass());
                } catch (InitException | URISyntaxException e) {
                    applyException(e);
                    return;
                }
                try {
                    switch (action) {
                        case ADD:
                            d.add(c);
                            break;
                        case REM:
                            d.rem(c);
                            break;
                        case EDIT:
                            d.edit(c);
                            break;
                        case LIST:
                            notifyObservers(d.list(o -> (c.getGroupName() == null) ||
                                    c.getGroupName().equals(((Contact) o).getGroupName())));
                            break;
                        case VIEW:
                            notifyObservers(d.get(c.getName()));
                            break;
                    }
                } catch (InitException | NoSuchElementException | IOException e) {
                    applyException(e);
                }
                break;
        }
    }

    void applyString(String s) {
        notifyObservers(s);
    }

    void applyException(Exception e) {
        notifyObservers(e);
    }

}
