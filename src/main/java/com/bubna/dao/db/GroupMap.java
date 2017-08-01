package com.bubna.dao.db;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

class GroupMap implements SQLData {

    public String group_name;
    public Integer group_color;

    private String sql_type;

    @Override
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;

        group_name = stream.readString();
        group_color = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(group_name);
        stream.writeInt(group_color);
    }
}
