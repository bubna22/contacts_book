package com.bubna.dao.db;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

final class DBContactDAO extends DBEntityAncestorDAO {


    @Override
    protected String getWriteRemFun() {
        return "SELECT contact_rem( ?, ?, ? );";
    }

    @Override
    protected String getWriteModifyFun() {
        return "SELECT contact_modify( ?, ?, ? );";
    }

    @Override
    protected String getReadListFun() {
        return "SELECT * FROM contact_list( ? );";
    }

    @Override
    protected void initMappingClass(Connection connection) throws InitException {

    }

    @Override
    protected HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) throws SQLException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        while (rs.next()) {
//            ContactMap contactMap = (ContactMap) rs.getObject("data");
            Contact newContact = new Contact(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(1));
            dataReturned.put(newContact.getName(), newContact);
        }
        return dataReturned;
    }

    @Override
    protected SQLData toObjectMap(EntityAncestor entityAncestor) {
        Contact inputContact = (Contact) entityAncestor;
        ContactMap contactMap = new ContactMap();
        contactMap.contact_name = inputContact.getName();
        contactMap.contact_email = inputContact.getEmail();
        contactMap.contact_num = inputContact.getNum();
        contactMap.contact_skype = inputContact.getSkype();
        contactMap.contact_telegram = inputContact.getTelegram();
        contactMap.group_name = inputContact.getGroupName();
        return contactMap;
    }

    @Override
    protected SQLData toObjectMap(String key) {
        Contact contact = new Contact(key, null, 0, null, null, null);
        return toObjectMap(contact);
    }
}