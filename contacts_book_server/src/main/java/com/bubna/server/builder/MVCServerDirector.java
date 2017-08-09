package com.bubna.server.builder;

import com.bubna.controller.Controller;
import com.bubna.dao.db.DBDAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;
import com.bubna.utils.ObservablePart;
import com.bubna.view.View;
import com.bubna.view.client.ClientView;

import java.net.Socket;
import java.util.Observable;

public class MVCServerDirector implements MVCDirector {

    private MVCBuilder builder;
    private Socket socket;

    public MVCServerDirector(MVCBuilder builder, Socket newSocket) {
        this.builder = builder;
        this.socket = newSocket;
    }

    @Override
    public void construct() throws CustomException {
        ObservablePart observable = new ObservablePart();
        observable.addObserver(ClientView.INSTANCE);
        Model modelContact = builder.createModel(observable, DBDAOFactory.INSTANCE, Contact.class);
        Model modelGroup = builder.createModel(observable, DBDAOFactory.INSTANCE, Group.class);
        Model modelUser = builder.createModel(observable, DBDAOFactory.INSTANCE, User.class);
        Controller controller = builder.createController(modelUser, modelContact, modelGroup);
        View view = builder.createView(observable, controller);
        view.setData(View.SOCKET, socket);
    }
}
