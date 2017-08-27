package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UserContactsCountCommand extends AbstractAdminCommand {

    public UserContactsCountCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new UserContactsCountCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            result = adminDAO.userContactsCount();
        } catch (CustomException e) {
            result = e;
        }
    }

}
