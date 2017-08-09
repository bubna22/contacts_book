package com.bubna.controller;

import com.bubna.exception.CustomException;

public interface Controller {

    void listen(String cmd) throws CustomException;
}
