package com.bubna.controller;

import com.bubna.dao.cmd.Command;
import com.bubna.exception.CustomException;
import com.bubna.model.Model;

import java.util.HashMap;

public class AbstractController implements Controller {

    Model model;
    HashMap<String, Object> input;

    public AbstractController(Model model) {
        this.model = model;
        input = new HashMap<>();
    }

    @Override
    public void addInput(String key, Object value) {
        input.put(key, value);
    }

    public void listen(String cmd) {
        try {
            Command cmdObj = model.getCommand(cmd);
            if (cmdObj == null) try {
                throw new CustomException("no such command");
            } catch (CustomException e) {
                e.printStackTrace();//TODO: err service
            }

            if (input.size() > 0) {
                String[] keys = new String[input.size()];
                input.keySet().toArray(keys);
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    cmdObj.addInput(key, input.get(key));
                }
            }

            model.executeCommand(cmdObj);
        } finally {
            input.clear();
        }
    }

}
