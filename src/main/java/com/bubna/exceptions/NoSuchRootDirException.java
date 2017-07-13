package com.bubna.exceptions;

/**
 * Created by test on 11.07.2017.
 */
public class NoSuchRootDirException extends CustomExceptionAncestor {

    static {
        NoSuchRootDirException.setMessage("root folder does not exists; see Utils");
    }
}
