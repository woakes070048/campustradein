package com.cti.config;

import com.cti.auth.Encrypter;
import com.cti.auth.EncrypterFactory;
import com.cti.auth.PBKDF2Encrypter;
import com.cti.auth.Password;
import com.cti.repository.InMemoryUserRepository;
import com.cti.repository.UserRepository;
import com.cti.service.UserService;
import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(UserService.class).to(UserService.class);
		bind(UserRepository.class).to(InMemoryUserRepository.class);
		bind(Encrypter.class).to(PBKDF2Encrypter.class);
		bind(EncrypterFactory.class).to(EncrypterFactory.class);
		bind(Password.PasswordBuilder.class).to(Password.PasswordBuilder.class);
		bind(Password.PasswordParser.class).to(Password.PasswordParser.class);
	}
}
