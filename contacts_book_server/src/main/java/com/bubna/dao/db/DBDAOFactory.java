package com.bubna.dao.db;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by test on 17.07.2017.
 */
public enum DBDAOFactory implements DAOFactory<Connection> {

    INSTANCE;

    private boolean jdbcClassInited = false;
    private DAO contactDAO = new DBContactDAO();
    private DAO groupDAO = new DBGroupDAO();
    private DAO userDAO = new DBUserDAO();

    @Override
    public DAO getDAO(Connection source, Class<? extends EntityAncestor> daoType) throws CustomException {
        if (daoType.equals(Contact.class)) {
            return contactDAO.setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return groupDAO.setUpdatedSource(source);
        } else if (daoType.equals(User.class)) {
            return userDAO.setUpdatedSource(source);
        }
        throw new CustomException("Invalid format of requested data");
    }

    @Override
    public Connection getSource() throws CustomException {
        try {
            if (!jdbcClassInited) Class.forName("org.postgresql.Driver");
            jdbcClassInited = true;
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:32768/postgres", "postgres", "");
            connection.prepareStatement("SET SCHEMA 'contacts_book';").execute();
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new CustomException(e.getMessage()==null?"connection err":e.getMessage());
        }
    }
}
