package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class CreateCommand extends AbstractCommand {

    public CreateCommand(String id) {
        super(id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("entity") || !input.containsKey("user")) throw new CustomException("incorrect input");
            dao.prepare();
            dao.create();
            result = Boolean.TRUE;
        } catch (CustomException e) {
            result = e;
        } finally {
            try {
                dao.close();
            } catch (CustomException e) {
                e.printStackTrace();//TODO: err service
            }
        }
    }

}
