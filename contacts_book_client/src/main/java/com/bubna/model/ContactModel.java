package com.bubna.model;

import com.bubna.service.Service;
import com.bubna.service.ServiceFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;
import com.bubna.utils.UserContactPair;
import com.bubna.utils.Utils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ContactModel extends AbstractModel<Contact> {

    public ContactModel(ServiceFactory serviceFactory, ObservablePart observable) {
        this.serviceFactory = serviceFactory;
        this.observable = observable;
    }

    @Override
    public void prepare() {
        try {
            this.service = serviceFactory.getService(serviceFactory.getSource(), Contact.class);
        } catch (InitException | URISyntaxException | IncorrectInputException | IOException e) {
            applyException(e);
        }
    }

    @Override
    public void list(User user, Contact entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (entity.getGroupName() != null && entity.getGroupName().equals("")) {
                        Service groupService = serviceFactory.getService(serviceFactory.getSource(), Group.class);
                        TransferObject transferObject =
                                new TransferObject("group", "get", new Group(entity.getGroupName(), null).serialize());
                        groupService.sendRequest(transferObject);
                        groupService.get(user, entity.getGroupName());
                    }

                    UserContactPair customPair = new UserContactPair(user, entity);

                    TransferObject transferObject = new TransferObject("contact", "list", Utils.INSTANCE.getGson().toJson(customPair));
                    Utils.INSTANCE.putGson();
                    service.sendRequest(transferObject);
                    synchronized (observable) {
                        observable.setChanged();
                        observable.notifyObservers(
                                service.list(user, o -> (entity.getGroupName() == null) ||
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
