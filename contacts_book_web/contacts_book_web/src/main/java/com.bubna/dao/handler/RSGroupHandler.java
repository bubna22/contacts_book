package com.bubna.dao.handler;

import com.bubna.dao.map.GroupMap;
import com.bubna.model.entity.Group;
import org.postgresql.util.PGobject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class RSGroupHandler implements Callable<ArrayList<Group>> {

    private ResultSet rs;

    public RSGroupHandler(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public ArrayList<Group> call() throws SQLException {
        ArrayList<Group> dataReturned = new ArrayList<>();
        while(rs.next()) {
            dataReturned.add(new GroupMap((PGobject) rs.getObject("data")).getEntity());
        }
        return dataReturned;
    }
}
