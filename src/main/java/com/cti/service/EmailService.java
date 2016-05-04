package com.cti.service;

import static j2html.TagCreator.a;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.html;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.cti.auth.AuthenticationToken;
import com.cti.config.Routes;
import com.cti.model.User;
import com.cti.repository.UserRepository;
import com.cti.smtp.Email;
import com.cti.smtp.Mailer;
import com.cti.smtp.SMTPMailException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class EmailService {
    @Inject
    private Mailer mailer;

    public void sendActivationEmail(User user, String htmlBody) throws SMTPMailException {
        Email email = new Email();
        email.setTo(user.getEmail());
        email.setFrom(System.getProperty("mail.username"));
        email.setSubject("Please verify your email address");
        email.setBody(htmlBody);
        mailer.mail(email);
    }

    public void sendForgotPasswordUrl(User user) {

    }
}
