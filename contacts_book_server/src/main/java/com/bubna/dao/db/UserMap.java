package com.bubna.dao.db;

import com.bubna.model.entity.User;
import org.postgresql.util.PGobject;

class UserMap extends CustomMap<User> {

    String user_login;
    String user_pass;
    String user_ip;

    UserMap(PGobject data) {
        super(data);
    }

    UserMap(User user) {
        super(user);
        this.user_login = user.getName();
        this.user_pass = user.getPass();
        this.user_ip = user.getIp();
        prepareOutput();
    }

    @Override
    protected void prepareInput(String[] values) {
        user_login = values[0].replace("'", "");
        user_pass = values[1].replace("'", "");
        user_ip = values[2].replace("'", "");
    }

    @Override
    protected void prepareOutput() {
        type = "user_type";
        value = "(" + user_login + "," + user_pass + "," + user_ip + ")";
    }

    @Override
    protected User getEntity() {
        return new User(this.user_login,
                        this.user_pass,
                        this.user_ip);
    }
}
