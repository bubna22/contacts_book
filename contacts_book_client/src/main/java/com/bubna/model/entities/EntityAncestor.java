package com.bubna.model.entities;

import com.bubna.utils.Utils;

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
        try {
            return Utils.INSTANCE.getGson().toJson(this);
        } finally {
            Utils.INSTANCE.putGson();
        }
    }

    public static <V extends EntityAncestor> V deserialize(String data, Class<V> entityClass) {
        try {
            return Utils.INSTANCE.getGson().fromJson(data, entityClass);
        } finally {
            Utils.INSTANCE.putGson();
        }
    }
}
