package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.util.HibernateUtil;
import org.hibernate.Session;

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

    Session session;
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
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    public void close() throws CustomException {
        session.getTransaction().commit();
        extraData.clear();
        if (session != null && session.isOpen()) session.close();
    }
}
