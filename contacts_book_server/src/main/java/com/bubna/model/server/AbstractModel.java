package com.bubna.model.server;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.model.entity.EntityAncestor;
import com.bubna.model.entity.User;
import com.bubna.utils.ObservablePart;

import java.util.Observable;

abstract class AbstractModel<V extends EntityAncestor> implements Model<V> {

    ObservablePart observable;
    DAOFactory daoFactory;
    private Class<? extends EntityAncestor> entityClass;
    protected DAO dao;

    AbstractModel(ObservablePart observable, DAOFactory daoFactory, Class<? extends EntityAncestor> entityClass) {
        this.observable = observable;
        this.daoFactory = daoFactory;
        this.entityClass = entityClass;
    }

    @Override
    public void prepareDAO() throws CustomException {
        observable.setChanged();
        this.dao = daoFactory.getDAO(daoFactory.getSource(), entityClass);
    }

    @Override
    public void login(User user) throws CustomException {
        throw new CustomException("no such interface");
    }

    @Override
    public void unlogin(User user) throws CustomException {
        throw new CustomException("no such interface");
    }

    @Override
    public void list(User user, V entity) throws CustomException {}

    @Override
    public void modify(User user, String name, V entity) throws CustomException {
        prepareDAO();
        dao.modify(user, entity.getName(), entity);
    }

}
