package com.bubna.model;

import com.bubna.dao.EntityDAO;
import com.bubna.dao.cmd.*;

public class UserModel extends AbstractModel {
    public UserModel(ObservablePart observable, EntityDAO entityDao) {
        super(observable, entityDao);
        cmds.put("login", new LoginEntityCommand("login"));
        cmds.put("unlogin", new UnloginEntityCommand("unlogin"));
    }
}
