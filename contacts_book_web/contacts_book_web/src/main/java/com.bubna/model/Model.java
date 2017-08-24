package com.bubna.model;

import com.bubna.dao.cmd.Command;

public interface Model {

    Command getCommand(String name);
    Object executeCommand(Command commandObject);
}
