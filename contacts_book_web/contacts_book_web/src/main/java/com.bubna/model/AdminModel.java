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
    }

    @PostConstruct
    private void construct() {
        this.adminDAO = (AdminDAO) applicationContext.getBean("adminDAO");
        cmds.put("count", (Command) applicationContext.getBean("userCountCommand"));
        cmds.put("userContactsCount", (Command) applicationContext.getBean("userContactsCountCommand"));
        cmds.put("userGroupsCount", (Command) applicationContext.getBean("userGroupsCountCommand"));
        cmds.put("userGroupsAVGCount", (Command) applicationContext.getBean("userGroupsAVGCountCommand"));
        cmds.put("userContactsAVGCount", (Command) applicationContext.getBean("userContactsAVGCountCommand"));
        cmds.put("inactiveUsers", (Command) applicationContext.getBean("inactiveUsersCommand"));
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
