package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UnloginEntityCommand extends AbstractEntityCommand {

    public UnloginEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new UnloginEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            entityDao.delete();
            result = Boolean.TRUE;
        } catch (CustomException e) {
            result = e;
        }
    }
}
