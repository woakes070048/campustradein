package com.cti.service;

import com.cti.model.User;
import com.cti.repository.UserRepository;
import com.cti.smtp.Email;
import com.cti.smtp.Mailer;
import com.cti.smtp.SMTPMailException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.UUID;

import static j2html.TagCreator.a;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.html;

public class EmailService {
    @Inject
    private Mailer mailer;
    @Inject
    private UserRepository userRepository;
    @Named("app url")
    private String appUrl;

    @Inject
    public EmailService(Mailer mailer) {
        this.mailer = mailer;
    }

    public void sendActivationEmail(User user, @Named("activationRoute") String route)
                                                                throws SMTPMailException {
        String token = UUID.randomUUID().toString();
        //TODO: save to database
        String confirmationUrl = new StringBuilder()
                                        .append(appUrl)
                                        .append(route)
                                        .append(token)
                                        .toString();
        Email email = new Email();
        email.setTo(user.getEmail());
        email.setSubject("Campustradein Registration Confirmation");
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
