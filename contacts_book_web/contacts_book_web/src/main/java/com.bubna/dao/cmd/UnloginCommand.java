package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UnloginCommand extends AbstractCommand {

    public UnloginCommand(String id) {
        super(id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            dao.prepare();
            dao.delete();
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
