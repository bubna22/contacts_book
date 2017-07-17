package com.bubna.backend.dao;

import com.bubna.entities.EntityAncestor;
import com.bubna.exceptions.InitException;

import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
interface DAOFactory<T>  {
    DAO getDAO(T source, Class<? extends EntityAncestor> daoType);
    T getSource() throws InitException, URISyntaxException;
}
