package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.Group;

import java.net.URISyntaxException;

public class ContactModel extends AbstractModel<Contact> {

    public ContactModel(DAOFactory daoFactory, ObservablePart observable) {
        this.daoFactory = daoFactory;
        this.observable = observable;
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), Contact.class);
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
        }
    }

    @Override
    public void list(Contact entity) {
        try {
            if (entity.getGroupName() != null && entity.getGroupName().equals("")) {
                DAO d1 = daoFactory.getDAO(daoFactory.getSource(), Group.class);
                d1.get(entity.getGroupName());
            }

            observable.setChanged();
            observable.notifyObservers(
                    dao.list(o -> (entity.getGroupName() == null) ||
                                    (((Contact) o).getGroupName() != null &&
                                    entity.getGroupName().equals(((Contact) o).getGroupName()))
                    )
            );
        } catch (InitException | URISyntaxException | IncorrectInputException | NoSuchElementException e) {
            applyException(e);
        }
    }
}
