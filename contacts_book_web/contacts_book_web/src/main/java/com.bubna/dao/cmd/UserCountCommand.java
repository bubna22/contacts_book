package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UserCountCommand extends AbstractAdminCommand {

    public UserCountCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new UserCountCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            result = adminDAO.userCount();
        } catch (CustomException e) {
            result = e;
        }
    }

}
