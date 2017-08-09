package com.bubna.dao.db;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.User;
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

    public synchronized User login(User acc) throws CustomException {throw new CustomException("no such interface;");}
    public synchronized User unlogin(User acc) throws CustomException {throw new CustomException("no such interface;");}

    @Override
    public synchronized final DAO setUpdatedSource(Connection source) throws CustomException {
        this.source = source;
        return this;
    }

    @Override
    public synchronized final HashMap<String, EntityAncestor> read(User acc, Predicate<String> pKey, Predicate<EntityAncestor> pValue) throws CustomException {
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
            throw new CustomException(e.getMessage()==null?"read query error;":e.getMessage());
        }
        return dataReturned;
    }

    @Override
    public synchronized final void write(User acc, HashMap<String, EntityAncestor> values) throws CustomException {
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
            throw new CustomException(e.getMessage()==null?"write query error;":e.getMessage());
        }
    }
}
