package com.bubna.dao.handler;

import com.bubna.dao.map.GroupMap;
import com.bubna.model.entity.Group;
import org.postgresql.util.PGobject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class RSGroupHandler extends FutureTask<ArrayList<Group>> {

    public RSGroupHandler(ResultSet rs) {
        super(() -> {
            ArrayList<Group> dataReturned = new ArrayList<>();
            while(rs.next()) {
                dataReturned.add(new GroupMap((PGobject) rs.getObject("data")).getEntity());
            }
            return dataReturned;
        });
    }
}
