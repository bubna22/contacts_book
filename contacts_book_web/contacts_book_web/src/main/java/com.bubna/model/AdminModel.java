package com.bubna.model;

import com.bubna.dao.AdminDAO;
import com.bubna.dao.cmd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;

public class AdminModel implements Model {
    final HashMap<String, Command> cmds;
    private AdminDAO adminDAO;
    @Autowired
    private ApplicationContext applicationContext;

    public AdminModel() {
        cmds = new HashMap<>();
        cmds.put("count", new UserCountCommand("count"));
        cmds.put("userContactsCount", new UserContactsCountCommand("userContactsCount"));
        cmds.put("userGroupsCount", new UserGroupsCountCommand("userGroupsCount"));
        cmds.put("userGroupsAVGCount", new UserGroupsAVGCountCommand("userGroupsAVGCount"));
        cmds.put("userContactsAVGCount", new UserContactsAVGCountCommand("userContactsAVGCount"));
        cmds.put("inactiveUsers", new InactiveUsersCommand("inactiveUsers"));
    }

    @PostConstruct
    private void construct() {
        this.adminDAO = (AdminDAO) applicationContext.getBean("adminDAO");
    }

    @Override
    public Command getCommand(String name) {
        return cmds.get(name);
    }

    @Override
    public Object executeCommand(Command commandObject) {
        commandObject.setDAO(adminDAO);
        commandObject.execute();
        return commandObject.getResult();
    }
}
