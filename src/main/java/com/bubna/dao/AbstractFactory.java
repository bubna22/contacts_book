package com.bubna.dao;

import com.bubna.dao.dom.DOMDAOFactory;
import com.bubna.dao.jack.JackDAOFactory;
import com.bubna.dao.stax.StAXDAOFactory;
import com.bubna.exceptions.InitException;

public enum AbstractFactory {
    INSTANCE;

    public enum SourceType {
        DOM(DOMDAOFactory.INSTANCE),
        SAX(StAXDAOFactory.INSTANCE),
        JACK(JackDAOFactory.INSTANCE);

        private DAOFactory daoFactory;

        private DAOFactory getDaoFactory() throws InitException {
            daoFactory.validateSource();
            return daoFactory;
        }

        SourceType(DAOFactory daoFactory) {
            this.daoFactory = daoFactory;
        }
    }

    public DAOFactory getFactory(SourceType type) throws InitException {
        return type.getDaoFactory();
    }

}
