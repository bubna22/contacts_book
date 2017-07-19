package com.bubna.dao;

public enum AbstractFactory {
    INSTANCE;

    public enum SourceType {
        FILE_SYSTEM(FSDAOFactory.INSTANCE);

        private DAOFactory daoFactory;

        private DAOFactory getDaoFactory() {
            return daoFactory;
        }

        SourceType(DAOFactory daoFactory) {
            this.daoFactory = daoFactory;
        }
    }

    public DAOFactory getFactory(SourceType type) {
        return type.getDaoFactory();
    }

}
