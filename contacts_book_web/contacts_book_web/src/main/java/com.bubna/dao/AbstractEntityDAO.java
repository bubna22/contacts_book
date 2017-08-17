package com.bubna.dao;

import com.bubna.exception.CustomException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

abstract class AbstractEntityDAO<V> implements EntityDAO<V> {

    Connection connection;
    HashMap<String, Object> extraData;

    public AbstractEntityDAO() {
        extraData = new HashMap<>();
    }

    @Override
    public void addExtra(String key, Object extra) {
        extraData.put(key, extra);
    }

    @Override
    public void create() throws CustomException {}

    @Override
    public void update() throws CustomException {}

    @Override
    public void delete() throws CustomException {}

    @Override
    public Collection<V> list() throws CustomException {return null;}

    @Override
    public void prepare() throws CustomException {
        try {
            InitialContext ctx = new InitialContext();
            Context initCtx = (Context) ctx.lookup("java:/comp/env");
            DataSource ds = (DataSource) initCtx.lookup("jdbc/postgres");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while getting connection":e.getMessage());
        }
    }

    @Override
    public void close() throws CustomException {
        extraData.clear();
        try {
            if (connection != null && connection.isClosed()) connection.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while closing connection":e.getMessage());
        }
    }
}
