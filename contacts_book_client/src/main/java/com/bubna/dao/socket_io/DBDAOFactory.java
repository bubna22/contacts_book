package com.bubna.dao.socket_io;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.model.entities.User;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by test on 17.07.2017.
 */
public enum DBDAOFactory implements DAOFactory<Socket> {

    INSTANCE;

    private DAO contactDAO = new DBContactDAO();
    private DAO groupDAO = new DBGroupDAO();
    private DAO userDAO = new DBUserDAO();

    @Override
    public synchronized DAO getDAO(Socket source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException {
        if (daoType.equals(Contact.class)) {
            return contactDAO.setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return groupDAO.setUpdatedSource(source);
        } else if (daoType.equals(User.class)) {
            return userDAO.setUpdatedSource(source);
        }
        throw new IncorrectInputException("Invalid format of requested data");
    }

    @Override
    public Socket getSource() throws InitException, URISyntaxException, IOException {
        Socket socket = new Socket("localhost", 9999);
        return socket;
    }
}
