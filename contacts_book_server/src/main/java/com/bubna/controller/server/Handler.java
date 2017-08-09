package com.bubna.controller.server;

import com.bubna.exception.CustomException;
import com.bubna.utils.TransferObject;

public interface Handler {
    void handle(TransferObject transferObject) throws CustomException;
    void setNext(Handler handler);
}
