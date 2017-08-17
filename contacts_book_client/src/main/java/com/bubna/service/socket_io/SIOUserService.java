package com.bubna.service.socket_io;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;

import java.io.DataInputStream;
import java.io.IOException;

final class SIOUserService extends SIOEntityAncestorService {

    @Override
    public final User login(User acc) throws InitException, IOException {
        DataInputStream dis = new DataInputStream(source.getInputStream());
        TransferObject transferObject = TransferObject.deserialize(dis.readUTF());
        return User.deserialize(transferObject.getData(), User.class);
    }

    @Override
    public final User unlogin(User acc) throws InitException, IOException {
        DataInputStream dis = new DataInputStream(source.getInputStream());
        TransferObject transferObject = TransferObject.deserialize(dis.readUTF());
        return User.deserialize(transferObject.getData(), User.class);
    }

    @Override
    protected String getEntityName() {
        return "user";
    }
}
