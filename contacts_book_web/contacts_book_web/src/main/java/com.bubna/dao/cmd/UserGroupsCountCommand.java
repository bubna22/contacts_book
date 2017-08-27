package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UserGroupsCountCommand extends AbstractAdminCommand {

    public UserGroupsCountCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new UserGroupsCountCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            result = adminDAO.userGroupsCount();
        } catch (CustomException e) {
            result = e;
        }
    }

}
