package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class ModifyEntityCommand extends AbstractEntityCommand {

    public ModifyEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new ModifyEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("entity") || !input.containsKey("user")) throw new CustomException("incorrect input");
            entityDao.update();
            result = Boolean.TRUE;
        } catch (CustomException e) {
            result = e;
        }
    }

}
