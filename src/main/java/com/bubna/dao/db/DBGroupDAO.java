package com.bubna.dao.db;

import com.bubna.exceptions.InitException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
        return "SELECT * FROM group_list( ? );";
    }

    @Override
    protected void initMappingClass(Connection connection) throws InitException {
        try {
            Map map = new HashMap();
            map.put("contacts_book.group_type", Class.forName("com.bubna.dao.db.GroupMap"));
            connection.setTypeMap(map);
        } catch (SQLException | ClassNotFoundException e) {
            throw new InitException("class-mapper initialization error; " + this.getClass().getName());
        }
    }

    @Override
    protected HashMap<String, EntityAncestor> getFromResultSet(ResultSet rs) throws SQLException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        while (rs.next()) {
            GroupMap groupMap = (GroupMap) rs.getObject("data");
            Group newGroup = new Group(
                    groupMap.group_name,
                    groupMap.group_color);
            dataReturned.put(newGroup.getName(), newGroup);
        }
        return dataReturned;
    }

    @Override
    protected SQLData toObjectMap(EntityAncestor entityAncestor) {
        Group inputGroup = (Group) entityAncestor;
        GroupMap groupMap = new GroupMap();
        groupMap.group_name = inputGroup.getName();
        groupMap.group_color = inputGroup.getColor();
        return groupMap;
    }

    @Override
    protected SQLData toObjectMap(String key) {
        Group group = new Group(key, 0);
        return toObjectMap(group);
    }
}
