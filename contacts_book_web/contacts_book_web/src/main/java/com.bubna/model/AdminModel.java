package com.bubna.model;

import com.bubna.dao.AdminDAO;
import com.bubna.dao.cmd.*;
import com.bubna.util.Answer;

import java.util.HashMap;

public class AdminModel implements Model {
    final HashMap<String, Command> cmds;
    private final ObservablePart observable;
    private final AdminDAO adminDAO;

    public AdminModel(ObservablePart observable, AdminDAO adminDAO) {
        cmds = new HashMap<>();
        this.observable = observable;
        this.adminDAO = adminDAO;

        cmds.put("count", new UserCountCommand("count"));
        cmds.put("userContactsCount", new UserContactsCountCommand("userContactsCount"));
        cmds.put("userGroupsCount", new UserGroupsCountCommand("userGroupsCount"));
        cmds.put("userGroupsAVGCount", new UserGroupsAVGCountCommand("userGroupsAVGCount"));
        cmds.put("userContactsAVGCount", new UserContactsAVGCountCommand("userContactsAVGCount"));
        cmds.put("inactiveUsers", new InactiveUsersCommand("inactiveUsers"));
    }

    @Override
    public Command getCommand(String name) {
        return cmds.get(name);
    }

    @Override
    public void executeCommand(Command commandObject) {
        observable.setChanged();
        commandObject.setDAO(adminDAO);
        synchronized (adminDAO) {
            commandObject.execute();
            observable.notifyObservers(new Answer(commandObject.getId(), commandObject.getResult()));
        }
    }
}
