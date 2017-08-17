package com.bubna.service.socket_io;

import com.bubna.service.Service;
import com.bubna.service.ServiceFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public enum SIOServiceFactory implements ServiceFactory<Socket> {

    INSTANCE;

    private Service contactService = new SIOContactService();
    private Service groupService = new SIOGroupService();
    private Service userService = new SIOUserService();

    @Override
    public synchronized Service getService(Socket source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException {
        if (daoType.equals(Contact.class)) {
            return contactService.setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return groupService.setUpdatedSource(source);
        } else if (daoType.equals(User.class)) {
            return userService.setUpdatedSource(source);
        }
        throw new IncorrectInputException("Invalid format of requested data");
    }

    @Override
    public Socket getSource() throws InitException, URISyntaxException, IOException {
        Socket socket = new Socket("localhost", 9999);
        return socket;
    }
}
