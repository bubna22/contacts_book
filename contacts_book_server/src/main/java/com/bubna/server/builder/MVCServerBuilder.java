package com.bubna.server.builder;

import com.bubna.controller.Controller;
import com.bubna.controller.server.ServerController;
import com.bubna.dao.DAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.server.ContactModel;
import com.bubna.model.server.GroupModel;
import com.bubna.model.Model;
import com.bubna.model.server.UserModel;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import com.bubna.utils.ObservablePart;
import com.bubna.view.View;
import com.bubna.view.server.ServerView;

import java.util.Observable;

public class MVCServerBuilder implements MVCBuilder {

    @Override
    public Model createModel(ObservablePart observable, DAOFactory daoFactory, Class<? extends EntityAncestor> entityClass) throws CustomException {
        if (entityClass.equals(Contact.class)) {
            return new ContactModel(observable, daoFactory);
        } else if (entityClass.equals(Group.class)) {
            return new GroupModel(observable, daoFactory);
        } else if (entityClass.equals(User.class)) {
            return new UserModel(observable, daoFactory);
        }
        throw new CustomException("no such entity type - " + entityClass.getName());
    }

    @Override
    public Controller createController(Model modelUser, Model modelContact, Model modelGroup) throws CustomException {
        return new ServerController(modelContact, modelGroup, modelUser);
    }

    @Override
    public View createView(Observable observable, Controller controller) throws CustomException {
        ServerView sv = new ServerView(controller);
        observable.addObserver(sv);
        return sv;
    }
}
