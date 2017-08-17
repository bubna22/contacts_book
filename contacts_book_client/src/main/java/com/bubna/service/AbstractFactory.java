package com.bubna.service;

import com.bubna.service.socket_io.SIOServiceFactory;
import com.bubna.exceptions.InitException;

public enum AbstractFactory {
    INSTANCE;

    public ServiceFactory getFactory() throws InitException {
        return SIOServiceFactory.INSTANCE;
    }

}
