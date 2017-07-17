package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
abstract class CustomExceptionAncestor extends Exception {

    private static String message = "add me;";

    public CustomExceptionAncestor(String m) {
        super(message + " " + m);
    }

    CustomExceptionAncestor() {
        super(message);
    }

    static void setMessage(String m) {
        message = m;
    }
}
