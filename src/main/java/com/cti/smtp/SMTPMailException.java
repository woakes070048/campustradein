package com.cti.smtp;

/**
 * Created by ifeify on 5/1/16.
 */
public class SMTPMailException extends Exception {
    public SMTPMailException() {
    }

    public SMTPMailException(String message) {
        super(message);
    }

    public SMTPMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SMTPMailException(Throwable cause) {
        super(cause);
    }

    public SMTPMailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getReceipient() {
        return null;
    }
}
