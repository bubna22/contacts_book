package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class GroupAlreadyExistsException extends CustomExceptionAncestor {

    static {
        GroupAlreadyExistsException.setMessage("group already exists;");
    }
}
