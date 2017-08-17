package com.bubna.dao.handler;

import com.bubna.dao.map.ContactMap;
import com.bubna.model.entity.Contact;
import org.postgresql.util.PGobject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class RSContactHandler extends FutureTask<ArrayList<Contact>> {

    public RSContactHandler(ResultSet rs) {
        super(() -> {
            ArrayList<Contact> dataReturned = new ArrayList<>();
            while(rs.next()) {
                dataReturned.add(new ContactMap((PGobject) rs.getObject("data")).getEntity());
            }
            return dataReturned;
        });
    }
}
