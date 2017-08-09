package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.EntityAncestor;

/**
 * Created by test on 17.07.2017.
 */
public interface DAOFactory<T>  {
    DAO getDAO(T source, Class<? extends EntityAncestor> daoType) throws CustomException;
    T getSource() throws CustomException;
}
