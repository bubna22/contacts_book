package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class InactiveUsersCommand extends AbstractAdminCommand {

    public InactiveUsersCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new InactiveUsersCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            result = adminDAO.inactiveUsers();
        } catch (CustomException e) {
            result = e;
        }
    }

}
