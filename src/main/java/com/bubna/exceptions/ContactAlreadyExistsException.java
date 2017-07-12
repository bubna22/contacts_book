package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class ContactAlreadyExistsException extends CustomExceptionAncestor {
    static {
        ContactAlreadyExistsException.setMessage("contact already exists;");
    }
}
