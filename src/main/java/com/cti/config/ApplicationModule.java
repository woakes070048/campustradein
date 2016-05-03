package com.cti.config;

import java.util.Properties;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import com.cti.annotation.Gmail;
import com.cti.annotation.billing.PayPal;
import com.cti.annotation.encrypter.PBKDF2;
import com.cti.auth.Encrypter;
import com.cti.auth.PBKDF2Encrypter;
import com.cti.billing.CreditCardProcessor;
import com.cti.billing.PaypalCreditCardProcessor;
import com.cti.repository.TokenRepository;
import com.cti.repository.UserRepository;
import com.cti.repository.impl.InMemoryTokenRepository;
import com.cti.repository.impl.InMemoryUserRepository;
import com.cti.smtp.GmailSMTPMailer;
import com.cti.smtp.Mailer;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ApplicationModule extends AbstractModule {
	private static final String sendGridAPIKey = "";
	private static final String payPalAPIKey = "";

	private void setupGmailSSLConfig() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		bind(Properties.class).annotatedWith(Gmail.class).toInstance(props);
	}


	private void setupAPIKeys() {
		bind(String.class).annotatedWith(Names.named("SendGrid API Key"))
				.toInstance(sendGridAPIKey);

		bind(String.class).annotatedWith(Names.named("PayPal API Key"))
				.toInstance(payPalAPIKey);
	}
	
	private void setupEnv() {
		System.setProperty("mail.username", "campustradein@gmail.com");
		System.setProperty("mail.password", "SysAdm1n");
	}

	@Override
	protected void configure() {
		setupEnv();
		setupAPIKeys();
		setupGmailSSLConfig();

		bind(UserRepository.class).to(InMemoryUserRepository.class);
		bind(TokenRepository.class).to(InMemoryTokenRepository.class);
		bind(Encrypter.class).annotatedWith(PBKDF2.class).to(
				PBKDF2Encrypter.class);
		bind(Encrypter.class).to(PBKDF2Encrypter.class);

		bind(CreditCardProcessor.class).annotatedWith(PayPal.class).to(
				PaypalCreditCardProcessor.class);

		bind(Mailer.class).to(GmailSMTPMailer.class);
		
		bind(ValidatorFactory.class).toInstance(Validation.buildDefaultValidatorFactory());

	}
}
