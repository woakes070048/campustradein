package com.cti.smtp;


import com.google.inject.name.Named;


import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.Map;


/**
 * Created by ifeify on 5/3/16.
 */
public class MailgunMailer implements Mailer {

    private final String host;
    private final String apiKey;
    private final String sender;

    @Inject
    public MailgunMailer(@Named("mailgun.apikey") String apiKey,
                         @Named("mailgun.url") String host,
                         @Named("mailgun.sender") String sender) {
        this.apiKey = apiKey;
        this.host = host;
        this.sender = sender;
    }

    @Override
    public void mail(Email email) throws SMTPMailException {
        Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic("api", apiKey));
        WebTarget target = client.target(host);
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("from", sender);
        formData.add("to", email.getTo());
        formData.add("subject", email.getSubject());
        formData.add("html", email.getBody());
        Response response = target.request(MediaType.APPLICATION_FORM_URLENCODED).post(Entity.form(formData));
        if(response.getStatus() != HttpStatus.SC_OK) {
            throw new SMTPMailException("mailgun failed to deliver email to " + email.getTo() + "." + response.toString());
        }
    }
}
