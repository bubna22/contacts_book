package com.bubna.dao.socket_io;

import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class DBContactDAO extends DBEntityAncestorDAO {

    @Override
    protected String getEntityName() {
        return "contact";
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromString(String data) {
        HashMap<String, EntityAncestor> dataRetruned = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Contact> contacts = gson.fromJson(data, new TypeToken<List<Contact>>(){}.getType());

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            dataRetruned.put(c.getName(), c);
        }

        return dataRetruned;
    }
}
