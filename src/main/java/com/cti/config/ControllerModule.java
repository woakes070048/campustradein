package com.cti.config;

import com.cti.service.UserService;
import com.cti.service.UserServiceImpl;
import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
	}
}
