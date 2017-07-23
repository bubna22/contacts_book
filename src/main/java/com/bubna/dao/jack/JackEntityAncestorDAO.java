package com.bubna.dao.jack;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.utils.Utils;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

public enum JackEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, File> {

    CONTACT {
        @Override
        public ArrayList<EntityAncestor> list(Predicate<EntityAncestor> p) throws InitException, NoSuchElementException {
            ArrayList<EntityAncestor> dataReturned = new ArrayList<>();
            for (int i = 0; i < jackData.contacts.contact.length; i++) {
                com.bubna.dao.jack.Contact contact = jackData.contacts.contact[i];
                Contact returnedContact = new Contact(contact.cname, contact.cemail, contact.cnum, contact.cskype, contact.ctelegram, contact.gname);
                if (p != null) {
                    if(p.test(returnedContact)) {
                        dataReturned.add(returnedContact);
                    }
                } else {
                    dataReturned.add(returnedContact);
                }
            }
            return dataReturned;
        }
    },
    GROUP {
        @Override
        public ArrayList<EntityAncestor> list(Predicate<EntityAncestor> p) throws InitException, NoSuchElementException {
            ArrayList<EntityAncestor> dataReturned = new ArrayList<>();
            for (int i = 0; i < jackData.groups.group.length; i++) {
                com.bubna.dao.jack.Group group = jackData.groups.group[i];
                dataReturned.add(new Group(group.gname, group.gcolor));
            }
            return dataReturned;
        }
    };

    protected File source;
    protected JackData jackData;

    @Override
    public DAO setUpdatedSource(File source) throws InitException {
        this.source = source;
        StringBuilder sb = new StringBuilder();
        String line;
        XmlMapper xmlMapper = new XmlMapper();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            jackData = xmlMapper.readValue(sb.toString(), JackData.class);
        } catch (IOException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
        }
        return this;
    }

    @Override
    public HashMap<String, EntityAncestor> read(Predicate<String> pKey, Predicate<EntityAncestor> pVal) throws InitException, NoSuchElementException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        switch (this) {
            case GROUP:
                com.bubna.dao.jack.Group[] groups = jackData.groups.group;
                for (com.bubna.dao.jack.Group g : groups) {
                    if ((pKey != null && pKey.test(g.gname)) || pKey == null) {
                        Group group = new Group(g.gname, g.gcolor);
                        if ((pVal != null && pVal.test(group)) || pVal == null)
                            dataReturned.put(g.gname, group);
                    }
                }
            case CONTACT:
                com.bubna.dao.jack.Contact[] contacts = jackData.contacts.contact;
                for (com.bubna.dao.jack.Contact c : contacts) {
                    if ((pKey != null && pKey.test(c.cname)) || pKey == null) {
                        Contact contact = new Contact(c.cname, c.cemail, c.cnum, c.cskype, c.ctelegram, c.gname);
                        if ((pVal != null && pVal.test(contact)) || pVal == null)
                            dataReturned.put(c.cname, contact);
                    }
                }
        }
        return dataReturned;
    }

    @Override
    public void write(HashMap<String, EntityAncestor> values) throws InitException, NoSuchElementException, IncorrectInputException {
        switch (this) {
            case CONTACT:
                for (int i = 0; i < jackData.contacts.contact.length; i++) {
                    com.bubna.dao.jack.Contact contact = jackData.contacts.contact[i];
                    if (!values.containsKey(contact.cname)) continue;
                    Contact inputContact = (Contact) values.get(contact.cname);
                    if (inputContact == null) {
                        jackData.contacts.contact[i] = null;
                        continue;
                    } else {
                        contact = new com.bubna.dao.jack.Contact();
                        contact.cname = inputContact.getName();
                        contact.cemail = inputContact.getEmail();
                        contact.cnum = inputContact.getNum();
                        contact.cskype = inputContact.getSkype();
                        contact.ctelegram = inputContact.getTelegram();
                        contact.gname = inputContact.getGroupName();
                        jackData.contacts.contact[i] = contact;
                    }
                    values.remove(contact.cname);
                }

                String[] keys = new String[values.size()];
                values.keySet().toArray(keys);
                ArrayList<com.bubna.dao.jack.Contact> newContacts =
                        new ArrayList<>(Arrays.asList(jackData.contacts.contact));
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    Contact inputContact = (Contact) values.get(key);
                    if (inputContact == null) {
                        throw new NoSuchElementException("no contact " + key);
                    } else {
                        com.bubna.dao.jack.Contact contact = new com.bubna.dao.jack.Contact();
                        contact.cname = inputContact.getName()==null?"":inputContact.getName();
                        contact.cemail = inputContact.getEmail()==null?"":inputContact.getEmail();
                        contact.cnum = inputContact.getNum()==null?-1:inputContact.getNum();
                        contact.cskype = inputContact.getSkype()==null?"":inputContact.getSkype();
                        contact.ctelegram = inputContact.getTelegram()==null?"":inputContact.getTelegram();
                        contact.gname = inputContact.getGroupName()==null?"":inputContact.getGroupName();
                        newContacts.add(contact);
                    }
                }
                jackData.contacts.contact = new com.bubna.dao.jack.Contact[newContacts.size()];
                newContacts.toArray(jackData.contacts.contact);
                break;
            case GROUP:
                for (int i = 0; i < jackData.groups.group.length; i++) {
                    com.bubna.dao.jack.Group group = jackData.groups.group[i];
                    if (!values.containsKey(group.gname)) continue;
                    Group inputGroup = (Group) values.get(group.gname);
                    if (inputGroup == null) {
                        jackData.groups.group[i] = null;
                        continue;
                    } else {
                        group = new com.bubna.dao.jack.Group();
                        group.gname = inputGroup.getName();
                        group.gcolor = inputGroup.getColor();
                        jackData.groups.group[i] = group;
                    }
                    values.remove(group.gname);
                }

                keys = new String[values.size()];
                values.keySet().toArray(keys);
                ArrayList<com.bubna.dao.jack.Group> newGroups =
                        new ArrayList<>(Arrays.asList(jackData.groups.group));
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    Group inputGroup = (Group) values.get(key);
                    if (inputGroup == null) {
                        throw new NoSuchElementException("no group " + key);
                    } else {
                        com.bubna.dao.jack.Group group = new com.bubna.dao.jack.Group();
                        group.gname = inputGroup.getName();
                        group.gcolor = inputGroup.getColor();
                        newGroups.add(group);
                    }
                }
                jackData.groups.group = new com.bubna.dao.jack.Group[newGroups.size()];
                newGroups.toArray(jackData.groups.group);
                break;
        }
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(Utils.getDataXml(), jackData);
        } catch (IOException | URISyntaxException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
        }
    }
}
