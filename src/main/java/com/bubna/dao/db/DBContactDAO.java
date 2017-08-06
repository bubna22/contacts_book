package com.bubna.dao.db;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import org.postgresql.util.PGobject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
        return "SELECT data FROM contact_list( ? ) as data;";
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) throws SQLException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        while (rs.next()) {
            Contact newContact = new ContactMap((PGobject) rs.getObject("data")).getEntity();
            dataReturned.put(newContact.getName(), newContact);
        }
        return dataReturned;
    }

    @Override
    protected PGobject toObjectMap(EntityAncestor entityAncestor) {
        return new ContactMap((Contact) entityAncestor);
    }

    @Override
    protected PGobject toObjectMap(String key) {
        Contact contact = new Contact(key, null, 0, null, null, null);
        return toObjectMap(contact);
    }
}
