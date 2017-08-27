package com.bubna.dao;

import com.bubna.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;

@Repository
abstract class AbstractEntityDAO<V> implements EntityDAO<V> {
    @PersistenceContext
    @Autowired
    EntityManager entityManager;
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
    @Transactional
    public void create() throws CustomException {}

    @Override
    @Transactional
    public void update() throws CustomException {}

    @Override
    @Transactional
    public void delete() throws CustomException {}

    @Override
    @Transactional
    public V get() throws CustomException {return null;}

    @Override
    @Transactional
    public Collection<V> list() throws CustomException {return null;}

}
