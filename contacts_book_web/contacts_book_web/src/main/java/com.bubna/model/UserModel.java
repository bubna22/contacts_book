package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.cmd.LoginCommand;
import com.bubna.dao.cmd.UnloginCommand;
import com.bubna.dao.cmd.UserCountCommand;

public class UserModel extends AbstractModel {
    public UserModel(ObservablePart observable, DAO dao) {
        super(observable, dao);
        cmds.put("login", new LoginCommand("login"));
        cmds.put("unlogin", new UnloginCommand("unlogin"));
        cmds.put("unlogin", new UserCountCommand("count"));
    }
}
