package com.bubna.model;

import com.bubna.dao.DAO;
import com.bubna.dao.cmd.Command;
import com.bubna.util.Answer;

import java.util.HashMap;

abstract class AbstractModel implements Model {
    final HashMap<String, Command> cmds;
    private final ObservablePart observable;
    private final DAO dao;

    AbstractModel(ObservablePart observable, DAO dao) {
        cmds = new HashMap<>();
        this.observable = observable;
        this.dao = dao;
    }

    @Override
    public Command getCommand(String name) {
        return cmds.get(name);
    }

    @Override
    public void executeCommand(Command commandObject) {
        observable.setChanged();
        commandObject.setDAO(dao);
        synchronized (commandObject) {
            commandObject.execute();
            observable.notifyObservers(new Answer(commandObject.getId(), commandObject.getResult()));
        }
    }
}
