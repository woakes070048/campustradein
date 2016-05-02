package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Controller
public class RegistrationFormController {
    private final static Logger logger = LoggerFactory.getLogger(RegistrationFormController.class);
	private UserService userService;
	
	@Inject
	public RegistrationFormController(UserService userService) {
		this.userService = userService;
	}

    // e.g localhost/register/validate?token=xhssySGGshhnx)
    private void confirmRegistration() {

	}
}
