package com.bubna.model.server;

import com.bubna.dao.DAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.User;
import com.bubna.utils.ObservablePart;
import com.bubna.utils.TransferObject;

import java.util.Observable;

public class UserModel extends AbstractModel<User> {

    public UserModel(ObservablePart observable, DAOFactory daoFactory) {
        super(observable, daoFactory, User.class);
    }

    @Override
    public void login(User user) throws CustomException {
        prepareDAO();
        String data = dao.login(user).serialize();
        observable.notifyObservers(new TransferObject("user", "login", data));
    }

    @Override
    public void unlogin(User user) throws CustomException {
        prepareDAO();
        String data = dao.unlogin(user).serialize();
        observable.notifyObservers(new TransferObject("user", "unlogin", data));
    }
}
