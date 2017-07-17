package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class ObserverNotRegisteredException extends CustomExceptionAncestor {
    static {
        ObserverNotRegisteredException.setMessage("no such observer");
    }
}
