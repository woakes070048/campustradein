package com.cti.config;

import com.cti.auth.PBKDF2Encrypter;
import com.cti.auth.Encrypter;
import com.cti.repository.InMemoryUserRepository;
import com.cti.repository.UserRepository;
import com.cti.service.UserService;
import com.cti.service.UserServiceImpl;
import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
		bind(UserRepository.class).to(InMemoryUserRepository.class);
		bind(Encrypter.class).to(PBKDF2Encrypter.class);
	}
}
