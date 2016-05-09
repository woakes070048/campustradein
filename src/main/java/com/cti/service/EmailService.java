package com.cti.service;

import com.cti.model.User;
import com.cti.smtp.Email;
import com.cti.smtp.Mailer;
import com.cti.smtp.SMTPMailException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EmailService {
    private ExecutorService executorService = Executors.newWorkStealingPool();

    @Inject
    private Mailer mailer;

    @Inject
    @Named("default.email.sender")
    private String sender;

    public void sendActivationEmail(User user, String htmlBody) throws SMTPMailException {
        Email email = new Email();
        email.setTo(user.getEmail());
        email.setFrom(sender);
        email.setSubject("Please verify your email address");
        email.setBody(htmlBody);
        mailer.mail(email);
    }

    public void sendForgotPasswordUrl(User user) {

    }
}
