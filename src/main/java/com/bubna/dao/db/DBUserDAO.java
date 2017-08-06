package com.bubna.dao.db;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

final class DBUserDAO extends DBEntityAncestorDAO {

    @Override
    public final User login(User acc) throws InitException {
        try {
            PreparedStatement preparedStatement = source.prepareStatement("SELECT data from login( ? ) as data;");
            preparedStatement.setObject(1, new UserMap(acc));
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new UserMap((PGobject) rs.getObject("data")).getEntity();
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"login error;":e.getMessage());
        }
    }

    @Override
    public final User unlogin(User acc) throws InitException {
        try {
            PreparedStatement preparedStatement = source.prepareStatement("SELECT user_unlogin( ? );");
            preparedStatement.setObject(1, new UserMap(acc));
            ResultSet rs = preparedStatement.executeQuery();
            return new UserMap((PGobject) rs.getObject("data")).getEntity();
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"unlogin error;":e.getMessage());
        }
    }

    @Override
    protected String getWriteRemFun() {
        return null;
    }

    @Override
    protected String getWriteModifyFun() {
        return null;
    }

    @Override
    protected String getReadListFun() {
        return null;
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) {return null;}
    @Override
    protected PGobject toObjectMap(EntityAncestor entityAncestor) {
        return new ContactMap((Contact) entityAncestor);
    }

    @Override
    protected PGobject toObjectMap(String key) {
        Contact contact = new Contact(key, null, 0, null, null, null);
        return toObjectMap(contact);
    }
}
