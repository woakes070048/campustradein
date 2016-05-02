package com.cti.smtp;


/**
 * Created by ifeify on 4/30/16.
 */
public interface Mailer {
    public void mail(Email email) throws SMTPMailException;
}
