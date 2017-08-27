package com.bubna.model;

import com.bubna.dao.EntityDAO;
import com.bubna.dao.cmd.*;

import javax.annotation.PostConstruct;

public class UserModel extends AbstractModel {
    public UserModel() {
        cmds.put("login", new LoginEntityCommand("login"));
        cmds.put("unlogin", new UnloginEntityCommand("unlogin"));
        cmds.put("get", new GetEntityCommand("get"));
    }

    @PostConstruct
    private void construct() {
        this.entityDao = (EntityDAO) applicationContext.getBean("userDAO");
    }
}
