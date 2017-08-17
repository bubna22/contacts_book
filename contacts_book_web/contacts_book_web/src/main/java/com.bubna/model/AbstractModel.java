package com.bubna.model;

import com.bubna.dao.EntityDAO;
import com.bubna.dao.cmd.Command;
import com.bubna.util.Answer;

import java.util.HashMap;

abstract class AbstractModel implements Model {
    final HashMap<String, Command> cmds;
    private final ObservablePart observable;
    private final EntityDAO entityDao;

    AbstractModel(ObservablePart observable, EntityDAO entityDao) {
        cmds = new HashMap<>();
        this.observable = observable;
        this.entityDao = entityDao;
    }

    @Override
    public Command getCommand(String name) {
        return cmds.get(name).safeCopy();
    }

    @Override
    public void executeCommand(Command commandObject) {
        observable.setChanged();
        commandObject.setDAO(entityDao);
        synchronized (entityDao) {
            commandObject.execute();
            observable.notifyObservers(new Answer(commandObject.getId(), commandObject.getResult()));
        }
    }
}
