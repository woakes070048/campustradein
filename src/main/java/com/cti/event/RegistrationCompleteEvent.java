package com.cti.event;

import com.cti.model.User;
import com.cti.service.UserService;
import com.cti.smtp.Email;
import com.cti.smtp.Mailer;
import com.cti.smtp.SMTPMailException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static j2html.TagCreator.*;

/**
 * Created by ifeify on 5/2/16.
 */
public class RegistrationCompleteEvent implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationCompleteEvent.class);
    @Inject
    private Mailer mailer;
    @Inject
    private UserService userService;
    @Named("app url")
    private String appUrl;
    private User user;

    public RegistrationCompleteEvent(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            confirmRegistration();
        } catch(SMTPMailException e) {
            logger.error("Failed to deliver email to " + e.getReceipient(), e);
        }
    }

    private void confirmRegistration() throws SMTPMailException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String confirmationUrl = appUrl + "/register/validate?token=" + token;
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
}
