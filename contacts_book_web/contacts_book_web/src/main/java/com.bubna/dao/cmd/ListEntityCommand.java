package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

import javax.servlet.ServletException;

public class ListEntityCommand extends AbstractEntityCommand {

    public ListEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new ListEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            result = entityDao.list();
        } catch (CustomException e) {
            result = e;
        }
    }

}
