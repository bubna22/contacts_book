package com.bubna.dao.db;

import org.postgresql.util.PGobject;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

class ContactMap extends PGobject {

    String contact_name;
    String contact_email;
    String contact_telegram;
    Integer contact_num;
    String contact_skype;
    String group_name;

    private String sql_type;

    @Override
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;

        contact_name = stream.readString();
        contact_email = stream.readString();
        contact_telegram = stream.readString();
        contact_num = stream.readInt();
        contact_skype = stream.readString();
        group_name = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(contact_name);
        stream.writeString(contact_email);
        stream.writeString(contact_telegram);
        stream.writeInt(contact_num);
        stream.writeString(contact_skype);
        stream.writeString(group_name);
    }
}
