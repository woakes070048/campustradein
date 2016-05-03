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

    @Inject
    public EmailService(Mailer mailer) {
        this.mailer = mailer;
    }

    public void sendActivationEmail(AuthenticationToken verificationToken)
                                                                throws SMTPMailException, UnknownHostException {
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        String confirmationUrl = new StringBuilder()
                                        .append(ipAddress)
                                        .append(Routes.activate)
                                        .append("?q=")
                                        .append(verificationToken.getToken())
                                        .toString();
        
        Email email = new Email();
        email.setTo(verificationToken.getUser().getEmail());
        email.setFrom(System.getProperty("mail.username"));
        email.setSubject("Campustradein Registration Confirmation");
        // TODO create several html reusable templates
        String body = html().with(
                h1("Please click on the url to start using campustradein"),
                a(confirmationUrl)
        ).render();

        email.setBody(body);
        mailer.mail(email);
    }

    public void sendForgotPasswordUrl(User user) {

    }
}
