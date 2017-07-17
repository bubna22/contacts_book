package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class NoSuchElementException extends CustomExceptionAncestor {
    static {
        NoSuchElementException.setMessage("no such element;");
    }
}
