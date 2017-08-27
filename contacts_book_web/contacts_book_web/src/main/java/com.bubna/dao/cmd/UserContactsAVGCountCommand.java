package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UserContactsAVGCountCommand extends AbstractAdminCommand {

    public UserContactsAVGCountCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new UserContactsAVGCountCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            result = adminDAO.userAVGContactsCount();
        } catch (CustomException e) {
            result = e;
        }
    }

}
