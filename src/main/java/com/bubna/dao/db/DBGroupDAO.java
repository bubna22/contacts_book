package com.bubna.dao.db;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import org.postgresql.util.PGobject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

final class DBGroupDAO extends DBEntityAncestorDAO {


    @Override
    protected String getWriteRemFun() {
        return "SELECT group_rem( ?, ?, ? );";
    }

    @Override
    protected String getWriteModifyFun() {
        return "SELECT group_modify( ?, ?, ? );";
    }

    @Override
    protected String getReadListFun() {
        return "SELECT data FROM group_list( ? ) as data;";
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) throws SQLException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        while (rs.next()) {
            Group newGroup = new GroupMap((PGobject) rs.getObject("data")).getEntity();

            dataReturned.put(newGroup.getName(), newGroup);
        }
        return dataReturned;
    }

    @Override
    protected PGobject toObjectMap(EntityAncestor entityAncestor) {
        return new GroupMap((Group) entityAncestor);
    }

    @Override
    protected PGobject toObjectMap(String key) {
        Group group = new Group(key, 0);
        return toObjectMap(group);
    }
}
