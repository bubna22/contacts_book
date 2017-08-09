package com.bubna.dao.socket_io;

import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class DBGroupDAO extends DBEntityAncestorDAO {


    @Override
    protected String getEntityName() {
        return "group";
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromString(String data) {
        HashMap<String, EntityAncestor> dataRetruned = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Group> groups = gson.fromJson(data, new TypeToken<List<Group>>(){}.getType());

        for (int i = 0; i < groups.size(); i++) {
            Group g = groups.get(i);
            dataRetruned.put(g.getName(), g);
        }

        return dataRetruned;
    }
}
