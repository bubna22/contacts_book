package com.bubna.dao.cmd;

import com.bubna.exception.CustomException;

public class ListCommand extends AbstractCommand {

    public ListCommand(String id) {
        super(id);
    }

    @Override
    public void execute() {
        super.execute();
        try {
            if (!input.containsKey("user")) throw new CustomException("incorrect input");
            dao.prepare();
            result = dao.list();
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
