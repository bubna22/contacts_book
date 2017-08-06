package com.bubna.model;

import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.User;

import java.net.URISyntaxException;

public class UserModel extends AbstractModel<User> {

    public UserModel(DAOFactory daoFactory, ObservablePart observable) {
        this.daoFactory = daoFactory;
        this.observable = observable;
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), User.class);
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
        }
    }

    @Override
    public void prepare() {
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), User.class);
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
        }
    }

    @Override
    public void login(User entity) {
        try {
            observable.setChanged();
            observable.notifyObservers(dao.login(entity));
        } catch (InitException e) {
            applyException(e);
        }
    }

    @Override
    public void unlogin(User entity) {
        try {
            observable.setChanged();
            observable.notifyObservers(dao.unlogin(entity));
        } catch (InitException e) {
            applyException(e);
        }
    }
}
