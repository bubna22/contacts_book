package com.bubna.model;

import com.bubna.dao.EntityDAO;
import com.bubna.dao.cmd.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

abstract class AbstractModel implements Model {
    HashMap<String, Command> cmds;
    EntityDAO entityDao;
    @Autowired
    ApplicationContext applicationContext;

    AbstractModel() {
        cmds = new HashMap<>();
    }

    @Override
    public Command getCommand(String name) {
        return cmds.get(name);
    }

    @Override
    public Object executeCommand(Command commandObject) {
        commandObject.setDAO(entityDao);
        commandObject.execute();
        return commandObject.getResult();
    }
}
