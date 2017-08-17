package com.bubna.dao.cmd;

import com.bubna.dao.DAO;

import java.util.HashMap;

abstract class AbstractCommand implements Command {

    protected DAO dao;
    protected HashMap<String, Object> input;
    protected String id;
    Object result;

    AbstractCommand(String id) {
        this.id = id;
        input = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public final void setDAO(DAO dao) {
        this.dao = dao;
    }

    @Override
    public void execute() {
        if (input.size() < 1) return;
        String[] keys = new String[input.size()];
        input.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            dao.addExtra(key, input.get(key));
        }
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
