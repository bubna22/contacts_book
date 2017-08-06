package com.bubna.dao.db;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.HashMap;
import java.util.function.Predicate;

abstract class DBEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, Connection> {

    protected Connection source;

    protected abstract HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) throws SQLException;
    protected abstract PGobject toObjectMap(EntityAncestor entityAncestor);
    protected abstract PGobject toObjectMap(String key);
    protected abstract String getWriteRemFun();
    protected abstract String getWriteModifyFun();
    protected abstract String getReadListFun();

    public User login(User acc) throws InitException {throw new InitException("no such interface;");}
    public User unlogin(User acc) throws InitException {throw new InitException("no such interface;");}

    @Override
    public final DAO setUpdatedSource(Connection source) throws InitException {
        this.source = source;
        return this;
    }

    @Override
    public final HashMap<String, EntityAncestor> read(User acc, Predicate<String> pKey, Predicate<EntityAncestor> pValue) throws InitException {
        HashMap<String, EntityAncestor> dataReturned;

        try {
            PreparedStatement preparedStatement = source.prepareStatement(getReadListFun());
            preparedStatement.setObject(1, new UserMap(acc));

            dataReturned = getFromResultSet(preparedStatement.executeQuery());

            String[] keys = new String[dataReturned.size()];
            dataReturned.keySet().toArray(keys);
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if (pKey != null && !pKey.test(key)) {
                    dataReturned.remove(key);
                    continue;
                }
                EntityAncestor value = dataReturned.get(key);
                if (pValue != null && !pValue.test(value)) dataReturned.remove(key);
            }
            if (!source.isClosed()) source.close();
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"read query error;":e.getMessage());
        }
        return dataReturned;
    }

    @Override
    public final void write(User acc, HashMap<String, EntityAncestor> values) throws InitException, NoSuchElementException, IncorrectInputException {
        String[] keys = new String[values.size()];
        values.keySet().toArray(keys);
        try {
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                EntityAncestor value = values.get(key);
                if (value == null) {
                    PreparedStatement preparedStatement = source.prepareStatement(getWriteRemFun());
                    preparedStatement.setObject(1, new UserMap(acc));
                    preparedStatement.setObject(2, key);
                    preparedStatement.setObject(3, toObjectMap(key));
                    preparedStatement.execute();
                    continue;
                }
                PreparedStatement preparedStatement = source.prepareStatement(getWriteModifyFun());
                preparedStatement.setObject(1, new UserMap(acc));
                preparedStatement.setObject(2, key);
                preparedStatement.setObject(3, toObjectMap(value));
                preparedStatement.execute();
            }

            if (!source.isClosed()) source.close();
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"write query error;":e.getMessage());
        }
    }
}
