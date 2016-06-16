package com.cti.exception;

/**
 * @author ifeify
 */
public class InvalidISBNException extends Exception {
    public InvalidISBNException() {
        super();
    }

    public InvalidISBNException(String message) {
        super(message);
    }

    public InvalidISBNException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidISBNException(Throwable cause) {
        super(cause);
    }

    protected InvalidISBNException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
