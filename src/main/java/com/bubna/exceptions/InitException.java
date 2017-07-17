package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class InitException extends CustomExceptionAncestor {

    public String msg = "";

    public InitException(String m) {
        super(m);
        msg = m;
    }

    static {
        InitException.setMessage("Initialization error;");
    }
}
