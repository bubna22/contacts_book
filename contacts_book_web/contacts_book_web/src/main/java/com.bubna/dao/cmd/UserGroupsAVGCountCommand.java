package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class UserGroupsAVGCountCommand extends AbstractAdminCommand {

    public UserGroupsAVGCountCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new UserGroupsAVGCountCommand(this.id);
    }

    @Override
    public void execute() {
        try {
            adminDAO.prepare();
            result = adminDAO.userAVGGroupsCount();
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
