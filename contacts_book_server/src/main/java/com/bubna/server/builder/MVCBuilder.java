package com.bubna.server.builder;

import com.bubna.dao.DAOFactory;
import com.bubna.controller.Controller;
import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.utils.ObservablePart;
import com.bubna.view.View;

import java.util.Observable;

public interface MVCBuilder {

    Model createModel(ObservablePart observable, DAOFactory daoFactory, Class<? extends EntityAncestor> entityClass) throws CustomException;
    Controller createController(Model modelUser, Model modelContact, Model modelGroup) throws CustomException;
    View createView(Observable observable, Controller controller) throws CustomException;
}
