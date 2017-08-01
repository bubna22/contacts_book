package com.bubna.dao.db;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

class UserMap implements SQLData {

    public String user_login;
    public String user_pass;
    public String user_ip;

    private String sql_type;

    @Override
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;

        user_login = stream.readString();
        user_pass = stream.readString();
        user_ip = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(user_login);
        stream.writeString(user_pass);
        stream.writeString(user_ip);
    }
}
