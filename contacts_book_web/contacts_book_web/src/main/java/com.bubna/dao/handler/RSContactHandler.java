package com.bubna.dao.handler;

import com.bubna.dao.map.ContactMap;
import com.bubna.model.entity.Contact;
import org.postgresql.util.PGobject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class RSContactHandler implements Callable<Collection<Contact>> {

    private ResultSet rs;

    public RSContactHandler(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public Collection<Contact> call() throws SQLException {
        Collection<Contact> dataReturned = new ArrayList<>();
        while(rs.next()) {
            dataReturned.add(new ContactMap((PGobject) rs.getObject("data")).getEntity());
        }
        return dataReturned;
    }
}
