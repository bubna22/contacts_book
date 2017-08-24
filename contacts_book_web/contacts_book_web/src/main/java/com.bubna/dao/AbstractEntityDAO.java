package com.bubna.dao;

import com.bubna.exception.CustomException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

abstract class AbstractEntityDAO<V> implements EntityDAO<V> {

    Session session;
    HashMap<String, Object> extraData;
    @Autowired
    ApplicationContext applicationContext;

    public AbstractEntityDAO() {
        extraData = new HashMap<>();
    }

    @Override
    public void addExtra(String key, Object extra) {
        extraData.put(key, extra);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void create() throws CustomException {}

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void update() throws CustomException {}

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void delete() throws CustomException {}

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public Collection<V> list() throws CustomException {return null;}

    @Override
    public void prepare() throws CustomException {
        session = (Session) applicationContext.getBean("session");
    }

    @Override
    public void close() throws CustomException {
        extraData.clear();
        if (session != null && session.isOpen()) session.close();
    }
}
