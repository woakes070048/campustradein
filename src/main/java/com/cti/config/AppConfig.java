package com.cti.config;

import com.cti.annotation.Gmail;
import com.cti.annotation.billing.PayPal;
import com.cti.annotation.encrypter.PBKDF2;
import com.cti.auth.Encrypter;
import com.cti.auth.PBKDF2Encrypter;
import com.cti.billing.CreditCardProcessor;
import com.cti.billing.PaypalCreditCardProcessor;
import com.cti.repository.Bookstore;
import com.cti.repository.SessionRepository;
import com.cti.repository.TokenRepository;
import com.cti.repository.UserRepository;
import com.cti.repository.impl.BookstoreImpl;
import com.cti.repository.impl.SessionRepositoryImpl;
import com.cti.repository.impl.TokenRepositoryImpl;
import com.cti.repository.impl.UserRepositoryImpl;
import com.cti.service.BooksApi;
import com.cti.service.GoogleBooksApi;
import com.cti.smtp.Mailer;
import com.cti.smtp.MailgunMailer;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.mongodb.MongoClient;

import java.util.Properties;

public class AppConfig extends AbstractModule {
	private static final String mailgunAPIKey = "key-1fff5fb17694e006fb79a4f6dc19a4e5";
    private static final String googleBooksAPIKey = "AIzaSyCHc2kEbWt_n0VHQd-voq4gIt9sPAo2YBs";
	private static final String payPalAPIKey = "";

	private void setupGmailSSLConfig() {
        // for demonstration only
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		bind(Properties.class).annotatedWith(Gmail.class).toInstance(props);
		bind(String.class).annotatedWith(Names.named("gmail.username")).toInstance("someone@gmail.com");
		bind(String.class).annotatedWith(Names.named("gmail.password")).toInstance("someone");
	}

    private void setupMailgun() {
        bind(String.class).annotatedWith(Names.named("mailgun.apikey"))
                .toInstance(mailgunAPIKey);

        bind(String.class).annotatedWith(Names.named("mailgun.url"))
                .toInstance("https://api.mailgun.net/v3/campustradein.com/messages");

        bind(String.class).annotatedWith(Names.named("mailgun.sender"))
                .toInstance("noreply@campustradein.com");
    }

	private void setupAPIKeys() {
		bind(String.class).annotatedWith(Names.named("PayPal API Key"))
				.toInstance(payPalAPIKey);

        bind(String.class).annotatedWith(Names.named("Google Books API Key"))
                .toInstance(googleBooksAPIKey);
	}

	private void setupMongodb() {
        // read from config file
        // its a heavy-weight object so we want it to be singleton
        // by default, mongodb opens 100 connections to the database
		MongoClient mongoClient = new MongoClient();
        bind(MongoClient.class).toInstance(mongoClient);
	}

	@Override
	protected void configure() {
        bind(String.class).annotatedWith(Names.named("default.email.sender")).toInstance("noreply@campustradein.com");
        setupMailgun();
		setupAPIKeys();
		setupMongodb();

		bind(UserRepository.class).to(UserRepositoryImpl.class);

		bind(TokenRepository.class).to(TokenRepositoryImpl.class);
        bind(SessionRepository.class).to(SessionRepositoryImpl.class);
		bind(Encrypter.class).annotatedWith(PBKDF2.class).to(PBKDF2Encrypter.class);
		bind(Encrypter.class).to(PBKDF2Encrypter.class);

		bind(CreditCardProcessor.class).annotatedWith(PayPal.class).to(
				PaypalCreditCardProcessor.class);

		bind(Mailer.class).to(MailgunMailer.class);

        bind(BooksApi.class).to(GoogleBooksApi.class);
		bind(Bookstore.class).to(BookstoreImpl.class);

	}
}
