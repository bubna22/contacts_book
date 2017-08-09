package com.bubna.model;

import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;
import com.bubna.utils.UserGroupPair;

import java.io.IOException;
import java.net.URISyntaxException;

public class GroupModel extends AbstractModel<Group> {

    public GroupModel(DAOFactory daoFactory, ObservablePart observable) {
        this.daoFactory = daoFactory;
        this.observable = observable;
    }

    @Override
    public void prepare() {
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), Group.class);
        } catch (InitException | URISyntaxException | IncorrectInputException | IOException e) {
            applyException(e);
        }
    }

    @Override
    public void list(User u, Group entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserGroupPair userGroupPair = new UserGroupPair(u, entity);
                    TransferObject transferObject = new TransferObject("group", "list", userGroupPair.serialize());
                    dao.sendRequest(transferObject);
                    synchronized (observable) {
                        observable.setChanged();
                        observable.notifyObservers(dao.list(u, o -> true));
                    }
                } catch (InitException | NoSuchElementException | IOException e) {
                    applyException(e);
                }
            }
        }).start();
    }
}
