package com.bubna.dao.cmd;

import com.bubna.dao.AdminDAO;
import com.bubna.dao.DAO;

import java.util.HashMap;

abstract class AbstractAdminCommand implements Command {

    protected AdminDAO adminDAO;
    protected HashMap<String, Object> input;
    protected String id;
    Object result;

    AbstractAdminCommand(String id) {
        this.id = id;
        input = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public final void setDAO(DAO adminDao) {
        this.adminDAO = (AdminDAO) adminDao;
    }

    @Override
    public void addInput(String key, Object value) {
        input.put(key, value);
    }

    @Override
    public final Object getResult() {
        input.clear();
        return result;
    }

}

