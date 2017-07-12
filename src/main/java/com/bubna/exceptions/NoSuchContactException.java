package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class NoSuchContactException extends CustomExceptionAncestor {
    static {
        NoSuchContactException.setMessage("no such contact;");
    }
}
