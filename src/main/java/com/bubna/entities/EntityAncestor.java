package com.bubna.entities;

import java.io.*;

/**
 * Created by test on 11.07.2017.
 */
abstract class EntityAncestor implements Serializable {

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
}
