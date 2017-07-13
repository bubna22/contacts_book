package com.bubna.backend;

import com.bubna.entities.Contact;
import com.bubna.entities.Group;
import com.bubna.exceptions.*;
import com.bubna.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by test on 11.07.2017.
 */
class StorageOverseer {

    private HashMap<String, Contact> contacts;
    private HashMap<String, Group> groups;

    private File storageDir;

    private static StorageOverseer singleton;

    static StorageOverseer getInstance() throws InitException {
        if (singleton == null)
            try {
                singleton = new StorageOverseer();
            } catch (NoSuchRootDirException | ClassNotFoundException | IOException e) {
                throw new InitException("storage");
            }

        return singleton;
    }

    private void serialize() throws NoSuchRootDirException, IOException {
        File root = Utils.getRootDir();
        if (root == null) {
            throw new NoSuchRootDirException();
        }

        String[] rootContent = root.list();

        for (int i = 0; i < rootContent.length; i++) {
            if (rootContent[i].contains("storage")) {
                storageDir = new File(rootContent[i]);
            }
        }
        if (storageDir == null) {
            storageDir = new File(root.getAbsolutePath() + "/storage");
            storageDir.mkdirs();
        }

        String[] keys = new String[contacts.size()];
        contacts.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            FileOutputStream fos = new FileOutputStream(storageDir.getAbsolutePath() + "/" + key + ".contact");
            contacts.get(key).serilize(fos);
        }

        keys = new String[groups.size()];
        groups.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            FileOutputStream fos = new FileOutputStream(storageDir.getAbsolutePath() + "/" + key + ".group");
            groups.get(key).serilize(fos);
        }
    }

    protected void finalize() {
        try {
            serialize();
        } catch (NoSuchRootDirException | IOException e) {
            e.printStackTrace();
        }
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void deserialize() throws IOException, ClassNotFoundException, NoSuchRootDirException {
        File root = Utils.getRootDir();
        if (root == null) {
            throw new NoSuchRootDirException();
        }

        String[] rootContent = root.list();

        for (int i = 0; i < rootContent.length; i++) {
            if (rootContent[i].contains("storage")) {
                storageDir = new File(root.getAbsolutePath() + "/" + rootContent[i]);
            }
        }
        if (storageDir == null) {
            storageDir = new File(root.getAbsolutePath() + "/storage");
            storageDir.mkdirs();
            return;
        }
        if (!storageDir.exists()) storageDir.mkdirs();
        String[] storageContent = storageDir.list();
        for (int i = 0; i < storageContent.length; i++) {
            String path = storageContent[i];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(new File(storageDir + "/" + path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (fis == null) continue;
            if (path.contains(".contact")) {
                Contact c = null;
                c = (Contact) Contact.deserialize(fis);
                contacts.put(c.getName(), c);
            } else if (path.contains(".group")) {
                Group g = null;
                g = (Group) Group.deserialize(fis);
                groups.put(g.getName(), g);
            }
        }
    }

    private StorageOverseer() throws NoSuchRootDirException, IOException, ClassNotFoundException {
        contacts = new HashMap<String, Contact>();
        groups = new HashMap<String, Group>();

        deserialize();
    }

    String contactAdd(Contact c) throws ContactAlreadyExistsException {
        if (c == null) {
            return "null input var";
        }
        if (contacts.containsKey(c.getName())) {
            throw new ContactAlreadyExistsException();
        }
        contacts.put(c.getName(), c);
        try {
            c.serilize(new FileOutputStream(new File(storageDir.getAbsolutePath() + "/" + c.getName() + ".contact")));
        } catch (IOException e) {
            return "file format err";
        }
        return c.getName() + " added";
    }

    String contactRemove(Contact c) throws NoSuchContactException {
        if (c == null) {
            return "null input var";
        }
        if (!contacts.containsKey(c.getName())) {
            throw new NoSuchContactException();
        }
        contacts.remove(c.getName());
        File f = new File(storageDir.getAbsolutePath() + "/" + c.getName() + ".contact");
        if (f.exists()) f.delete();
        return c.getName() + " removed";
    }

    Contact contactGet(String name) throws NoSuchContactException {
        if (name == null || !contacts.containsKey(name)) {
            throw new NoSuchContactException();
        }
        return contacts.get(name);
    }

    ArrayList<Contact> contactGetAll() {
        return contactGetAll(null);
    }

    ArrayList<Contact> contactGetAll(Group g) {
        String[] keys = new String[contacts.size()];
        ArrayList<Contact> dataReturned = new ArrayList<Contact>();
        contacts.keySet().toArray(keys);

        for (int i = 0; i < keys.length; i++) {
            Contact c = contacts.get(keys[i]);
            if (g != null) {
                if (c.getGroupName() != null && c.getGroupName().equals(g.getName())) dataReturned.add(c);
                continue;
            }
            dataReturned.add(c);
        }

        return dataReturned;
    }

    String contactEdit(Contact c) throws NoSuchContactException {
        if (c == null) {
            return "null input var";
        }
        if (!contacts.containsKey(c.getName())) {
            throw new NoSuchContactException();
        }
        contacts.put(c.getName(), c);
        try {
            c.serilize(new FileOutputStream(new File(storageDir.getAbsolutePath() + "/" + c.getName() + ".contact")));
        } catch (IOException e) {
            return ">>>> err while serialise contact " + c.getName();
        }
        return c.getName() + " edited";
    }

    String groupAdd(Group g) throws GroupAlreadyExistsException {
        if (g == null) {
            return "null input var";
        }
        if (groups.containsKey(g.getName())) {
            throw new GroupAlreadyExistsException();
        }
        groups.put(g.getName(), g);
        try {
            g.serilize(new FileOutputStream(new File(storageDir.getAbsolutePath() + "/" + g.getName() + ".group")));
        } catch (IOException e) {
            return  ">>>> err while serialise group " + g.getName();
        }
        return g.getName() + " added";
    }

    String groupRemove(Group g) throws NoSuchGroupException {
        if (g == null) {
            return "null input var";
        }
        if (!groups.containsKey(g.getName())) {
            throw new NoSuchGroupException();
        }
        groups.remove(g.getName());
        File f = new File(storageDir.getAbsolutePath() + "/" + g.getName() + ".group");
        if (f.exists()) f.delete();
        return g.getName() + " removed";
    }

    Group groupGet(String name) throws NoSuchGroupException {
        if (name == null || !groups.containsKey(name)) {
            throw new NoSuchGroupException();
        }
        return groups.get(name);
    }

    ArrayList<Group> groupGetAll() {
        ArrayList<Group> dataReturned = new ArrayList<Group>();
        dataReturned.addAll(groups.values());
        return dataReturned;
    }

    String groupEdit(Group g) throws NoSuchGroupException {
        if (g == null) {
            return "null input var";
        }
        if (!groups.containsKey(g.getName())) {
            throw new NoSuchGroupException();
        }
        groups.put(g.getName(), g);
        try {
            g.serilize(new FileOutputStream(new File(storageDir.getAbsolutePath() + "/" + g.getName() + ".group")));
        } catch (IOException e) {
            return ">>>> err while serialise group " + g.getName();
        }
        return g.getName() + " edited";
    }
}
