package com.bubna.model.server;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import com.bubna.utils.ObservablePart;
import com.bubna.utils.TransferObject;
import com.bubna.utils.Utils;

import java.util.ArrayList;

public class ContactModel extends AbstractModel<Contact> {

    public ContactModel(ObservablePart observable, DAOFactory daoFactory) {
        super(observable, daoFactory, Contact.class);
    }

    @Override
    public void list(User user, Contact entity) throws CustomException {
        prepareDAO();
        if (entity != null && entity.getGroupName() != null && entity.getGroupName().equals("")) {
            DAO d1 = daoFactory.getDAO(daoFactory.getSource(), Group.class);
            d1.get(user, entity.getGroupName());//TODO: make get cmd!!!
        }

        ArrayList<Contact> dataReturned = dao.list(user, o -> (entity != null && (entity.getGroupName() == null) ||
                (((Contact) o).getGroupName() != null &&
                        entity.getGroupName().equals(((Contact) o).getGroupName())))
        );
        TransferObject transferObject =
                new TransferObject("contact", "list", Utils.INSTANCE.getGson().toJson(dataReturned));
        Utils.INSTANCE.putGson();
        observable.notifyObservers(transferObject);
    }
}
