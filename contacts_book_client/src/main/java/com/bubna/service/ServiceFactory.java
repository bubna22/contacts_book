package com.bubna.service;

import com.bubna.model.entities.EntityAncestor;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public interface ServiceFactory<T>  {
    Service getService(T source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException;
    T getSource() throws InitException, URISyntaxException, IOException;
}
