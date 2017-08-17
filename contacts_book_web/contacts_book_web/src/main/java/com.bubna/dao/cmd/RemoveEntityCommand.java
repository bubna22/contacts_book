package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class RemoveEntityCommand extends AbstractEntityCommand {

    public RemoveEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new RemoveEntityCommand(this.id);
    }


    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("entity") || !input.containsKey("user")) throw new CustomException("incorrect input");
            entityDao.prepare();
            entityDao.delete();
            result = Boolean.TRUE;
        } catch (CustomException e) {
            result = e;
        } finally {
            try {
                entityDao.close();
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
    }

}
