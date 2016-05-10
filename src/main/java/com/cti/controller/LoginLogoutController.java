package com.cti.controller;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;

import com.cti.config.FreemarkerTemplateEngine;
import com.cti.config.Routes;
import com.cti.dto.LoginDto;
import com.cti.dto.UserDto;
import com.cti.smtp.Email;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;

import org.apache.commons.lang3.exception.ExceptionContext;
import org.apache.commons.logging.Log;
import org.apache.http.HttpStatus;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.auth.AuthenticationToken;
import com.cti.model.User;
import com.cti.service.AuthenticationService;
import com.cti.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ifeify on 4/30/16.
 */
@Controller
public class LoginLogoutController {
    private static final Logger logger = LoggerFactory.getLogger(LoginLogoutController.class);
	@Inject
    private UserService userService;
	
	@Inject
	private AuthenticationService authService;

    @Inject
    private ValidatorFactory validatorFactory;

    @Inject
    private FreemarkerTemplateEngine templateEngine;

    private Validator validator;
	
	@Route
    public void handleLogout() {
        Spark.get(Routes.LOGOUT, (request, response) -> {
            try {
                String sessionID = request.cookie("session");
                if (sessionID != null && !sessionID.isEmpty()) {
                    userService.endSession(sessionID);
                    response.removeCookie("session");
                }
                response.redirect("/");
            } catch(Exception e) {
                logger.error("Error logging user with ip {} out", request.ip(), e);
            }
            return null;
        });
    }
	
	@Route
	public void handleLogin() {
		Spark.post(Routes.LOGIN, (request, response) -> {
			try {
                if(!request.headers("Accept").contains("application/json")) {
                    response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
                    return new JSONObject()
                                .put("error", "Only json allowed")
                                .toString();
                }
                ObjectMapper mapper = new ObjectMapper();
                LoginDto loginDto = mapper.readValue(request.body(), LoginDto.class);
                validateInput(loginDto);
				User user = null;
				if (loginDto.getUsernameOrEmail().contains("@")) {
					user = userService.findByEmail(loginDto.getUsernameOrEmail());
				} else {
					user = userService.findByUsername(loginDto.getUsernameOrEmail());
				}
                // TODO: userService.findByUserId()

				if(user != null && authService.isPasswordCorrect(user.getPassword(), loginDto.getPassword())) {
					AuthenticationToken token = userService.startSession(user);
					response.cookie("session", token.getToken());
                    response.status(HttpStatus.SC_OK);
                    response.redirect("/");
				} else { // password incorrect or user not found
                    response.status(HttpStatus.SC_UNAUTHORIZED);
                }
			} catch(ValidationException e) {
                response.status(HttpStatus.SC_BAD_REQUEST);
                String errors[] = e.getMessage().split(".");
                return new JSONObject()
                        .put("errors", errors)
                        .put("status", "invalid inputs")
                        .toString();
			} catch(Exception e) {
                logger.error("Failed to log user with ip {} and login params {}", request.ip(), request.body(), e);
                response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                response.redirect(Routes.ERROR);
            }
            return null;
		});
	}

    @Route
    public void getLoginPage() {
        Spark.get(Routes.LOGIN, (request, response) -> {
            try {
                String sessionID = request.cookie("session");
                if (sessionID != null && !sessionID.isEmpty()) {
                    User user = userService.findUserBySessionID(sessionID);
                    if(user != null) {
                        response.redirect("/");
                        return null;
                    }
                }
                Map<String, String> model = new HashMap<>();
                model.put("signup_url", Routes.SIGNUP);
                return templateEngine.render(new ModelAndView(model, "login.ftl"));
            } catch(Exception e) {
                logger.error("Cannot render login page", e);
                response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                response.redirect(Routes.ERROR);
                return null;
            }

        });
    }

    private void validateInput(LoginDto loginDto) throws ValidationException {
        if(validator == null) {
            validator = validatorFactory.getValidator();
        }
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        if(violations.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for(ConstraintViolation<LoginDto> violation : violations) {
                ConstraintDescriptor<?> desc = violation.getConstraintDescriptor();
                stringBuilder.append(desc.getMessageTemplate());
                stringBuilder.append(".");
            }
            throw new ValidationException(stringBuilder.toString());
        }
    }
}
