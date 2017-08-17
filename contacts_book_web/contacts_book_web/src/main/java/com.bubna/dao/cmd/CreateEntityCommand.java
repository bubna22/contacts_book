package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class CreateEntityCommand extends AbstractEntityCommand {

    public CreateEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new CreateEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("entity") || !input.containsKey("user")) throw new CustomException("incorrect input");
            entityDao.prepare();
            entityDao.create();
            result = Boolean.TRUE;
        } catch (CustomException e) {
            result = e;
        } finally {
            try {
                entityDao.close();
            } catch (CustomException e) {
                e.printStackTrace();//TODO: err service
            }
        }
    }

}
