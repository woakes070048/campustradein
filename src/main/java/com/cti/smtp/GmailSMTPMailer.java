package com.cti.smtp;

import com.cti.annotation.Gmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by ifeify on 5/1/16.
 */
public class GmailSMTPMailer implements Mailer {
    private Properties properties;

    public GmailSMTPMailer(@Gmail Properties properties) {
        this.properties = properties;
    }

    @Override
    public void mail(Email email) throws SMTPMailException {
        try {
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            properties.remove("username");
            properties.remove("password");
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            Transport.send(message);
        } catch(MessagingException e) {
            throw new SMTPMailException("Failed to send email to " + email.getTo(), e);
        }
    }
}
