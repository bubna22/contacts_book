package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class GetEntityCommand extends AbstractEntityCommand {

    public GetEntityCommand(String id) {
        super(id);
    }

    @Override
    public synchronized Command safeCopy() {
        return new GetEntityCommand(this.id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            result = entityDao.get();
        } catch (CustomException e) {
            result = e;
        }
    }

}
