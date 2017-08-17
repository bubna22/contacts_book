package com.bubna.model;

import com.bubna.dao.cmd.Command;

public interface Model {

    Command getCommand(String name);
    void executeCommand(Command commandObject);
}
