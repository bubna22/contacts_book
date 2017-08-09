package com.bubna.dao.db;

import com.bubna.model.entity.EntityAncestor;
import org.postgresql.util.PGobject;

abstract class CustomMap <V extends EntityAncestor> extends PGobject {

    CustomMap(PGobject data) {
        String str = data.getValue();
        char[] chars = str.toCharArray();
        char[] charsCopy = new char[str.length()];
        int charsCopyOffset = 0;
        for (int i = 0; i < chars.length; i++) {
            boolean charsMovedBySintax = i==0||i==chars.length-1;
            charsCopyOffset += charsMovedBySintax?1:0;
            if (charsMovedBySintax) continue;
            charsCopy[i - charsCopyOffset] = chars[i];
        }
        chars = new char[charsCopy.length - charsCopyOffset];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = charsCopy[i];
        }
        String[] values = new String(chars).split(",");
        prepareInput(values);
    }

    CustomMap(V ea) {}

    protected abstract void prepareInput(String[] values);
    protected abstract void prepareOutput();
    protected abstract V getEntity();

}
