package com.cti.controller;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.ws.WebEndpoint;

import com.cti.Payload;
import com.cti.PayloadType;
import com.cti.RequestHandler;
import com.cti.annotation.Controller;
import com.cti.dto.UserDto;
import com.cti.exception.EmailExistsException;
import com.cti.exception.UsernameExistsException;
import com.cti.model.Session;
import com.cti.model.User;
import com.cti.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;

import java.util.Map;

@Singleton
@Controller
public class RegistrationFormController {
    private final static Logger logger = LoggerFactory.getLogger(RegistrationFormController.class);
	private UserService userService;
	
	@Inject
	public RegistrationFormController(UserService userService) {
		this.userService = userService;
	}

    /**
     * if operation was successful, return 200 ok code
     */
    public void setupEndpoints() {
//        Spark.post("/register", new RequestHandler<UserDto>() {
//            @Override
//            public Payload doHandle(UserDto userDto, Map<String, String> queryParams, PayloadType payloadType) {
//                try {
//                    return null;
//                } catch(UsernameExistsException | EmailExistsException e) {
//                    logger.error("Cannot register new user {}", userDto, e);
//                    return null;
//                }
//            }
//        });
    }
}
