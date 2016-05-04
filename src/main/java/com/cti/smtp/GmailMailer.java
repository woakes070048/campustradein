package com.cti.smtp;

import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cti.annotation.Gmail;

/**
 * Created by ifeify on 5/1/16.
 */
public class GmailMailer implements Mailer {
    private Properties properties;
    
    @Inject
    public GmailMailer(@Gmail Properties properties) {
        this.properties = properties;
    }

    @Override
    public void mail(Email email) throws SMTPMailException {
        try {
            String username = System.getProperty("mail.username");
            String password = System.getProperty("mail.password");
            
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
            message.setContent(email.getBody(), "text/html");
            Transport.send(message);
        } catch(MessagingException e) {
            throw new SMTPMailException("Failed to send email to " + email.getTo(), e);
        }
    }
}
