package com.bubna.exceptions;

/**
 * Created by test on 17.07.2017.
 */
public class IncorrectInputException extends CustomExceptionAncestor {
    static {
        IncorrectInputException.setMessage("incorrect input;");
    }

    private String msg;

    public IncorrectInputException(String m) {
        super(m);
        msg = m;
    }

    public IncorrectInputException() {
        super();
    }
}
