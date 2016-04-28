package com.cti.controller;

import javax.inject.Inject;

import com.cti.annotation.Controller;
import com.cti.service.UserService;

@Controller
public class RegistrationFormController {
	private UserService userService;
	
	@Inject
	public RegistrationFormController(UserService userService) {
		this.userService = userService;
	}
}
