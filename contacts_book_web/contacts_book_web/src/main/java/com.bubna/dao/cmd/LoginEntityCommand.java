package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class LoginEntityCommand extends AbstractEntityCommand {

    public LoginEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command clone() {
        return new LoginEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            entityDao.prepare();
            entityDao.update();
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
