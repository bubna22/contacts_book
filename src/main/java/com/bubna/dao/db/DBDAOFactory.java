package com.bubna.dao.db;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by test on 17.07.2017.
 */
public enum DBDAOFactory implements DAOFactory<Connection> {

    INSTANCE;

    private boolean jdbcClassInited = false;

    @Override
    public DAO getDAO(Connection source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException {
        if (daoType.equals(Contact.class)) {
            return new DBContactDAO().setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return new DBGroupDAO().setUpdatedSource(source);
        }
        throw new IncorrectInputException("Invalid format of requested data");
    }

    @Override
    public synchronized Connection getSource() throws InitException, URISyntaxException {
        try {
            if (!jdbcClassInited) Class.forName("org.postgresql.Driver");
            jdbcClassInited = true;
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:32768/postgres", "postgres", "");
            connection.prepareStatement("SET SCHEMA 'contacts_book';").execute();
            connection.prepareStatement(
                    "SET pljava.libjvm_location TO '/usr/lib/jvm/java-8-oracle/jre/lib/amd64/server/libjvm.so';\n" +
                    "CREATE EXTENSION IF NOT EXISTS pljava;" +
//                    "SELECT sqlj.install_jar('/opt/contacts_book/contacts_book-1.1.jar', 'c', false);" +
                    "SELECT sqlj.set_classpath('contacts_book', 'c');" +
                    "SELECT sqlj.add_type_mapping('user_type', 'com.bubna.dao.db.UserMap');").execute();
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new InitException("connection err");
        }
    }
}
