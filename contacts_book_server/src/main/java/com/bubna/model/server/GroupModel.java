package com.bubna.model.server;

import com.bubna.dao.DAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import com.bubna.utils.ObservablePart;
import com.bubna.utils.TransferObject;
import com.bubna.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Observable;

public class GroupModel extends AbstractModel<Group> {

    public GroupModel(ObservablePart observable, DAOFactory daoFactory) {
        super(observable, daoFactory, Group.class);
    }

    @Override
    public void list(User user, Group entity) throws CustomException {
        prepareDAO();
        ArrayList<Group> dataReturned = dao.list(user, o -> true);
        TransferObject transferObject =
                new TransferObject("group", "list", Utils.INSTANCE.getGson().toJson(dataReturned));
        Utils.INSTANCE.putGson();
        observable.notifyObservers(transferObject);
    }
}
