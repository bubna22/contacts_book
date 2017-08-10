package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;
import com.bubna.utils.UserContactPair;
import com.bubna.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ContactModel extends AbstractModel<Contact> {

    public ContactModel(DAOFactory daoFactory, ObservablePart observable) {
        this.daoFactory = daoFactory;
        this.observable = observable;
    }

    @Override
    public void prepare() {
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), Contact.class);
        } catch (InitException | URISyntaxException | IncorrectInputException | IOException e) {
            applyException(e);
        }
    }

    @Override
    public void list(User u, Contact entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (entity.getGroupName() != null && entity.getGroupName().equals("")) {
                        DAO d1 = daoFactory.getDAO(daoFactory.getSource(), Group.class);
                        TransferObject transferObject =
                                new TransferObject("group", "get", new Group(entity.getGroupName(), null).serialize());
                        d1.sendRequest(transferObject);
                        d1.get(u, entity.getGroupName());
                    }

                    UserContactPair customPair = new UserContactPair(u, entity);

                    TransferObject transferObject = new TransferObject("contact", "list", Utils.INSTANCE.getGson().toJson(customPair));
                    Utils.INSTANCE.putGson();
                    dao.sendRequest(transferObject);
                    synchronized (observable) {
                        observable.setChanged();
                        observable.notifyObservers(
                                dao.list(u, o -> (entity.getGroupName() == null) ||
                                        (((Contact) o).getGroupName() != null &&
                                                entity.getGroupName().equals(((Contact) o).getGroupName()))
                                )
                        );
                    }
                } catch (InitException | URISyntaxException | IncorrectInputException | NoSuchElementException | IOException e) {
                    applyException(e);
                }
            }
        }).start();
    }
}
