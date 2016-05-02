package com.cti.smtp;

import com.google.inject.name.Named;
import com.sendgrid.SendGrid;

/**
 * Created by ifeify on 5/1/16.
 */
public class SendGridMailer implements Mailer {
    private final String apiKey;
    private SendGrid sendGrid;

    public SendGridMailer(@Named("SendGrid API Key") String apiKey) {
        this.apiKey = apiKey;
        sendGrid = new SendGrid(apiKey);
    }

    @Override
    public void mail(Email email) throws SMTPMailException {

    }
}
