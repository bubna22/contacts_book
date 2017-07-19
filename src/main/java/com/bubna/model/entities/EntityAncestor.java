package com.bubna.model.entities;

import java.io.*;

/**
 * Created by test on 11.07.2017.
 */
public abstract class EntityAncestor implements Serializable {

    protected String name;

    public final void serilize(FileOutputStream fos) throws IOException {
        ObjectOutputStream ois = new ObjectOutputStream(fos);
        ois.writeObject(this);
        ois.close();
    }

    public static Object deserialize(FileInputStream fis) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object dataReturned = ois.readObject();
        ois.close();

        return dataReturned;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return super.toString();
    }
}
