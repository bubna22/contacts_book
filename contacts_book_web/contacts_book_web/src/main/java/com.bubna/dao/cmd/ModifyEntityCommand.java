package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class ModifyEntityCommand extends AbstractEntityCommand {

    public ModifyEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command clone() {
        return new ModifyEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("entity") || !input.containsKey("user")) throw new CustomException("incorrect input");
            entityDao.prepare();
            entityDao.update();
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
