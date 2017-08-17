package com.bubna.dao;

import com.bubna.dao.handler.RSContactHandler;
import com.bubna.dao.map.ContactMap;
import com.bubna.dao.map.UserMap;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.User;
import com.bubna.view.MainServlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class ContactEntityDAO extends AbstractEntityDAO<Contact> {

    ContactEntityDAO() {
        super();
    }

    @Override
    public void create() throws CustomException {
        update();
    }

    @Override
    public void delete() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT contact_modify( ?, ?, ? );");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));
            Contact inputContact = (Contact) extraData.get("entity");
            preparedStatement.setObject(2, inputContact.getName());
            preparedStatement.setObject(3, null);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    @Override
    public void update() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT contact_modify( ?, ?, ? );");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));
            Contact inputContact = (Contact) extraData.get("entity");
            preparedStatement.setObject(2, inputContact.getName());
            preparedStatement.setObject(3, new ContactMap(inputContact));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    @Override
    public Collection<Contact> list() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT data FROM contact_list( ? ) as data;");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));

            RSContactHandler contactHandler = new RSContactHandler(preparedStatement.executeQuery());
            return contactHandler.call();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }
}
