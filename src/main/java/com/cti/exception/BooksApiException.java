package com.cti.exception;

/**
 * Created by ifeify on 6/13/16.
 */
public class BooksApiException extends Exception {
    public BooksApiException() {
        super();
    }

    public BooksApiException(String message) {
        super(message);
    }

    public BooksApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BooksApiException(Throwable cause) {
        super(cause);
    }

    protected BooksApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
