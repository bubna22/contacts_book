package com.bubna.model.entities;

import java.io.*;

/**
 * Created by test on 11.07.2017.
 */
public abstract class EntityAncestor implements Serializable {

    protected String name;

    public String getName() {
        return name;
    }
}
