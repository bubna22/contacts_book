package com.bubna.model;

import com.bubna.service.ServiceFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserModel extends AbstractModel<User> {

    public UserModel(ServiceFactory serviceFactory, ObservablePart observable) {
        this.serviceFactory = serviceFactory;
        this.observable = observable;
    }

    @Override
    public void prepare() {
        try {
            this.service = serviceFactory.getService(serviceFactory.getSource(), User.class);
        } catch (InitException | URISyntaxException | IncorrectInputException | IOException e) {
            applyException(e);
        }
    }

    @Override
    public void login(User entity) {
        observable.setChanged();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransferObject transferObject = new TransferObject("user", "login", entity.serialize());
                    service.sendRequest(transferObject);
                    synchronized (observable) {
                        observable.notifyObservers(service.login(entity));
                    }
                } catch (InitException | IOException e) {
                    applyException(e);
                }
            }
        }).start();
    }

    @Override
    public void unlogin(User entity) {
        observable.setChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransferObject transferObject = new TransferObject("user", "login", entity.serialize());
                    service.sendRequest(transferObject);
                    synchronized (observable) {
                        observable.notifyObservers(service.unlogin(entity));
                    }
                } catch (InitException | IOException e) {
                    applyException(e);
                }
            }
        }).start();
    }
}
