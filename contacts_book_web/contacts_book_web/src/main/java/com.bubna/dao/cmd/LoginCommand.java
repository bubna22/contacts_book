package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class LoginCommand extends AbstractCommand {

    public LoginCommand(String id) {
        super(id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            dao.prepare();
            dao.update();
            result = Boolean.TRUE;
        } catch (CustomException e) {
            result = e;
        } finally {
            try {
                dao.close();
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
    }

}
