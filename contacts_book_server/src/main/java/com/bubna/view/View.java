package com.bubna.view;

import com.bubna.exception.CustomException;

import java.util.Observer;

public interface View extends Observer {

    int SOCKET = 0;
    int TEXT_AREA = 1;
    int CONTROLLER = 2;

    void setData(int key, Object data) throws CustomException;

}
