package com.bubna.dao.socket_io;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Predicate;

abstract class DBEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, Socket> {

    protected Socket source;

    public User login(User acc) throws InitException, IOException {throw new InitException("no such interface;");}
    public User unlogin(User acc) throws InitException, IOException {throw new InitException("no such interface;");}

    @Override
    public final DAO setUpdatedSource(Socket source) throws InitException {
        this.source = source;
        return this;
    }

    @Override
    public final HashMap<String, EntityAncestor> read(User acc, Predicate<String> pKey, Predicate<EntityAncestor> pValue) throws InitException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();

        try {
            DataInputStream dis = new DataInputStream(source.getInputStream());
            boolean b = false;
            while (!b) {
                TransferObject transferObject = TransferObject.deserialize(dis.readUTF());
                if (!transferObject.getWho().equals(getEntityName())) continue;
                b = true;
                dataReturned = getFromString(transferObject.getData());
            }

            String[] keys = new String[dataReturned.size()];
            dataReturned.keySet().toArray(keys);
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if (pKey != null && !pKey.test(key)) {
                    dataReturned.remove(key);
                    continue;
                }
                EntityAncestor value = dataReturned.get(key);
                if (pValue != null && !pValue.test(value)) dataReturned.remove(key);
            }
        } catch (IOException e) {
            throw new InitException(e.getMessage()==null?"read query error;":e.getMessage());
        }
        return dataReturned;
    }

    @Override
    public final synchronized void write(User acc, HashMap<String, EntityAncestor> values) throws InitException, NoSuchElementException, IncorrectInputException, IOException {
        DataOutputStream dos = new DataOutputStream(source.getOutputStream());

        String[] keys = new String[values.size()];
        values.keySet().toArray(keys);

        for (int i = 0; i < keys.length; i++) {
            TransferObject transferObject = new TransferObject(getEntityName(), "modify", values.get(keys[i]).serialize());
            dos.writeUTF(transferObject.serialize());
        }
    }

    public synchronized void sendRequest(TransferObject transferObject) throws IOException {
        DataOutputStream dos = new DataOutputStream(source.getOutputStream());
        dos.writeUTF(transferObject.serialize());
    }

    protected abstract String getEntityName();
    protected HashMap<String, EntityAncestor> getFromString(String data) {return null;}
}
