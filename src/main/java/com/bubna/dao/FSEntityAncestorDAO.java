package com.bubna.dao;

import com.bubna.entities.EntityAncestor;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by test on 17.07.2017.
 */
enum FSEntityAncestorDAO implements DAO<String, EntityAncestor, File> {

    CONTACT(".contact"),
    GROUP(".group");

    private HashMap<String, EntityAncestor> cached;
    private File source;
    private String format;

    FSEntityAncestorDAO(String format) {
        cached = new HashMap<>();
        this.format = format;
    }

    @Override
    public EntityAncestor get(String pk) throws NoSuchElementException, InitException {
        if (cached.containsKey(pk)) return cached.get(pk);
        else {
            File[] files = source.listFiles((dir, name) -> name.endsWith(pk + format));
            if (files == null || files.length < 1) throw new NoSuchElementException("\nentity not exists");
            EntityAncestor c;
            try {
                c = (EntityAncestor) EntityAncestor.deserialize(new FileInputStream(files[0]));
            } catch (IOException | ClassNotFoundException e) {
                throw new InitException("init element error");
            }
            if (!c.getName().equals(pk)) throw new NoSuchElementException("\nincorrect inputed name: " + c.getName());
            cached.put(pk, c);
            return c;
        }
    }

    @Override
    public void add(EntityAncestor e) throws IOException {
        String pk = e.getName();
        cached.put(pk, e);
        File f = new File(source.getAbsolutePath() + "/" + pk + format);
        if (!f.exists()) f.createNewFile();
        e.serilize(new FileOutputStream(f));
    }

    @Override
    public void edit(EntityAncestor e) throws InitException, IOException {
        String pk = e.getName();
        File f = new File(source.getAbsolutePath() + "/" + pk + format);
        if (!f.exists()) throw new InitException("\ninit error while deserializing " + f.getName());
        if (cached.containsKey(pk)) cached.put(pk, e);
        e.serilize(new FileOutputStream(f));
    }


    @Override
    public void rem(String pk) throws NoSuchElementException {
        if (cached.containsKey(pk)) cached.remove(pk);
        File[] files = source.listFiles((dir, name) -> {
            if (name.endsWith(pk + format)) return true;
            return false;
        });
        if (files == null || files.length < 1) throw new NoSuchElementException("\nentity not exists");
        EntityAncestor c;
        try {
            c = (EntityAncestor) EntityAncestor.deserialize(new FileInputStream(files[0]));
        } catch (IOException | ClassNotFoundException e) {
            throw new NoSuchElementException("\nerror while deserialize " + files[0].getName());
        }
        if (!c.getName().equals(pk)) throw new NoSuchElementException("\nincorrect inputed name: " + c.getName());
        files[0].delete();
    }

    @Override
    public ArrayList<EntityAncestor> list(Predicate<EntityAncestor> p) throws InitException {
        List<EntityAncestor> entities = cached.values().stream().filter(p).collect(Collectors.toList());
        File[] files = source.listFiles((dir, name) -> {
            if (name.endsWith(format)) return true;
            return false;
        });
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                String fname = f.getName().replace(format, "");
                boolean contains = false;
                for (int j = 0; j < entities.size(); j++) {
                    if (entities.get(j).getName().equals(fname)) {
                        contains = true;
                        break;
                    }
                }
                if (contains) continue;
                EntityAncestor e;
                try {
                    e = (EntityAncestor) EntityAncestor.deserialize(new FileInputStream(f));
                } catch (IOException | ClassNotFoundException e1) {
                    throw new InitException("\ninit error while deserializing " + f.getName());
                }
                if (!p.test(e)) continue;
                cached.put(e.getName(), e);
                entities.add(e);
            }
        }
        return Collections.list(Collections.enumeration(entities));
    }

    @Override
    public DAO setUpdatedSource(File source) {
        this.source = source;
        return this;
    }
}
