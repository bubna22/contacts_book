package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UserCountCommand extends AbstractAdminCommand {

    public UserCountCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command clone() {
        return new UserCountCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            adminDAO.prepare();
            result = adminDAO.userCount();
        } catch (CustomException e) {
            result = e;
        } finally {
            try {
                adminDAO.close();
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
    }

}
