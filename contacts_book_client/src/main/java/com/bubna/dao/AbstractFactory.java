package com.bubna.dao;

import com.bubna.dao.socket_io.SIODAOFactory;
import com.bubna.exceptions.InitException;

public enum AbstractFactory {
    INSTANCE;

    public DAOFactory getFactory() throws InitException {
        return SIODAOFactory.INSTANCE;
    }

}
