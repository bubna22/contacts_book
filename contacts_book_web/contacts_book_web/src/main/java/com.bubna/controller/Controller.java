package com.bubna.controller;

public interface Controller {

    void listen(String cmd);
    void addInput(String key, Object value);

}
