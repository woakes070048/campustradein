package com.cti.service;

import com.cti.model.UserAccount;
import com.cti.smtp.Email;
import com.cti.smtp.Mailer;
import com.cti.smtp.SMTPMailException;

import javax.inject.Inject;
import javax.inject.Named;


public class EmailService {
    @Inject
    private Mailer mailer;

    @Inject
    @Named("default.email.sender")
    private String sender;

    public void sendActivationEmail(UserAccount userAccount, String htmlBody) throws SMTPMailException {
        Email email = new Email();
        email.setTo(userAccount.getEmail());
        email.setFrom(sender);
        email.setSubject("Please verify your email address");
        email.setBody(htmlBody);
        mailer.mail(email);
    }

}
