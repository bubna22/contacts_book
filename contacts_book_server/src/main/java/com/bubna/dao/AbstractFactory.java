package com.bubna.dao;

import com.bubna.dao.db.DBDAOFactory;
import com.bubna.exception.CustomException;

public enum AbstractFactory {
    INSTANCE;

    public DAOFactory getFactory() throws CustomException {
        return DBDAOFactory.INSTANCE;
    }

}
