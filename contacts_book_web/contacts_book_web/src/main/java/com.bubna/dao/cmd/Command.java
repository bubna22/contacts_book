package com.bubna.dao.cmd;

import com.bubna.dao.DAO;

public interface Command {
    void execute();
    void setDAO(DAO dao);
    void addInput(String key, Object value);
    Object getResult();
    String getId();

    Command clone();
}
