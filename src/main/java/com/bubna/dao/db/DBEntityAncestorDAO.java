package com.bubna.dao.db;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;

import java.sql.*;
import java.util.HashMap;
import java.util.function.Predicate;

abstract class DBEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, Connection> {

    private Connection source;

    protected abstract void initMappingClass(Connection connection) throws InitException;
    protected abstract HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) throws SQLException;
    protected abstract SQLData toObjectMap(EntityAncestor entityAncestor);
    protected abstract SQLData toObjectMap(String key);
    protected abstract String getWriteRemFun();
    protected abstract String getWriteModifyFun();
    protected abstract String getReadListFun();

    @Override
    public final DAO setUpdatedSource(Connection source) throws InitException {
        this.source = source;
        initMappingClass(source);
        return this;
    }

    @Override
    public final HashMap<String, EntityAncestor> read(Predicate<String> pKey, Predicate<EntityAncestor> pValue) throws InitException {
        HashMap<String, EntityAncestor> dataReturned;

        try {
            PreparedStatement preparedStatement = source.prepareStatement(getReadListFun());
            UserMap userMap = new UserMap();
            userMap.user_login = "bubna";
            userMap.user_pass = "";
            userMap.user_ip = "1";
            preparedStatement.setObject(1, userMap);

            dataReturned = getFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"read query error;":e.getMessage());
        } finally {
            try {
                source.close();
            } catch (SQLException e) {
                throw new InitException(e.getMessage()==null?"close read query error;":e.getMessage());
            }
        }

        String[] keys = new String[dataReturned.size()];
        dataReturned.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (!pKey.test(key)) {
                dataReturned.remove(key);
                continue;
            }
            EntityAncestor value = dataReturned.get(key);
            if (!pValue.test(value)) dataReturned.remove(key);
        }
        try {
            source.close();
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"close read query error;":e.getMessage());
        }
        return dataReturned;
    }

    @Override
    public final void write(HashMap<String, EntityAncestor> values) throws InitException, NoSuchElementException, IncorrectInputException {
        String[] keys = new String[values.size()];
        values.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            EntityAncestor value = values.get(key);
            try {
                if (value == null) {
                    PreparedStatement preparedStatement = source.prepareStatement(getWriteRemFun());
                    preparedStatement.setString(1, "bubna");
                    preparedStatement.setString(2, "1");
                    preparedStatement.setObject(3, toObjectMap(key));
                    preparedStatement.execute();
                    continue;
                }
                PreparedStatement preparedStatement = source.prepareStatement(getWriteModifyFun());
                preparedStatement.setString(1, "bubna");
                preparedStatement.setString(2, "1");
                preparedStatement.setObject(3, toObjectMap(value));
                preparedStatement.execute();

            } catch (SQLException e) {
                throw new InitException(e.getMessage()==null?"read query error;":e.getMessage());
            } finally {
                try {
                    source.close();
                } catch (SQLException e) {
                    throw new InitException(e.getMessage()==null?"close read query error;":e.getMessage());
                }
            }
        }
        try {
            source.close();
        } catch (SQLException e) {
            throw new InitException(e.getMessage()==null?"close read query error;":e.getMessage());
        }
    }
}
