package com.bubna.model.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by test on 11.07.2017.
 */
public abstract class EntityAncestor implements Serializable {

    protected String name;

    public String getName() {
        return name;
    }

    public String serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static <V extends EntityAncestor> V deserialize(String data, Class<V> entityClass) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(data, entityClass);
    }
}
