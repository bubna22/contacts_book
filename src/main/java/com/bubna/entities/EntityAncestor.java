package com.bubna.entities;

import java.io.*;

/**
 * Created by test on 11.07.2017.
 */
abstract class EntityAncestor implements Serializable {

    public final void serilize(FileOutputStream fos) {
        try {
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            ois.writeObject(this);
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(FileInputStream fis) {
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object dataReturned = ois.readObject();
            ois.close();

            return dataReturned;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
