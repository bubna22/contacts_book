package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class NoSuchGroupException extends CustomExceptionAncestor {
    static {
        NoSuchGroupException.setMessage("no such group;");
    }
}
