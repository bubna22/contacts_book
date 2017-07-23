package com.bubna.dao;

import com.bubna.model.entities.EntityAncestor;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;

import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public interface DAOFactory<T>  {
    DAO getDAO(T source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException;
    T getSource() throws InitException, URISyntaxException;
    void validateSource() throws InitException;
}
