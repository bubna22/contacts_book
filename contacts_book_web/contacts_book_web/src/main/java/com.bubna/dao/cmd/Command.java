package com.bubna.dao.cmd;

import com.bubna.dao.DAO;
import com.bubna.exception.CustomException;

public interface Command {
    void execute();
    void setDAO(DAO dao);
    void addInput(String key, Object value);
    Object getResult();
    String getId();
}
