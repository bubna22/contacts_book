package com.bubna.dao.cmd;

import com.bubna.dao.UserDAO;
import com.bubna.exception.CustomException;

public class UserCountCommand extends AbstractCommand {

    public UserCountCommand(String id) {
        super(id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            dao.prepare();
            result = ((UserDAO) dao).userCount();
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
