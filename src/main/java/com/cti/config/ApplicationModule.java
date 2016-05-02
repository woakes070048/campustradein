package com.cti.config;

import com.cti.annotation.Gmail;
import com.cti.annotation.billing.PayPal;
import com.cti.annotation.encrypter.PBKDF2;
import com.cti.auth.Encrypter;
import com.cti.auth.PBKDF2Encrypter;
import com.cti.billing.CreditCardProcessor;
import com.cti.billing.PaypalCreditCardProcessor;
import com.cti.repository.InMemoryUserRepository;
import com.cti.repository.UserRepository;
import com.cti.smtp.GmailSMTPMailer;
import com.cti.smtp.Mailer;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Properties;

public class ApplicationModule extends AbstractModule {
    private static final String sendGridAPIKey = "";
    private static final String payPalAPIKey = "";
    private static final Properties gmailProperties = new Properties();

    private ApplicationModule() {
        super();
        gmailProperties.put("mail.smtp.host", "smtp.gmail.com");
        gmailProperties.put("mail.smtp.socketFactory.port", "465");
        gmailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        gmailProperties.put("mail.smtp.auth", "true");
        gmailProperties.put("mail.smtp.port", "465");
        gmailProperties.put("username", System.getProperty("username"));
        gmailProperties.put("password", System.getProperty("password"));
    }

	@Override
	protected void configure() {
		bind(UserRepository.class).to(InMemoryUserRepository.class);
		bind(Encrypter.class)
                .annotatedWith(PBKDF2.class)
                .to(PBKDF2Encrypter.class);
        bind(Encrypter.class).to(PBKDF2Encrypter.class);

        bind(String.class)
                .annotatedWith(Names.named("SendGrid API Key"))
                .toInstance(sendGridAPIKey);

        bind(String.class)
                .annotatedWith(Names.named("PayPal API Key"))
                .toInstance(payPalAPIKey);

        bind(String.class)
                .annotatedWith(Names.named("app url"))
                .toInstance(System.getProperty("appUrl"));

        bind(Properties.class)
                .annotatedWith(Gmail.class)
                .toInstance(gmailProperties);

        bind(CreditCardProcessor.class)
                .annotatedWith(PayPal.class)
                .to(PaypalCreditCardProcessor.class);

        bind(Mailer.class).to(GmailSMTPMailer.class);

	}
}
