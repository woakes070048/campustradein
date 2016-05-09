package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.auth.AuthenticationToken;
import com.cti.config.FreemarkerTemplateEngine;
import com.cti.config.Routes;
import com.cti.dto.UserDto;
import com.cti.exception.InvalidTokenException;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;
import com.cti.service.EmailService;
import com.cti.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

@Controller
public class RegistrationController {
    private final static Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    
    @Inject
	private UserService userService;
    
    @Inject 
    private EmailService emailService;
    
    @Inject 
    private ValidatorFactory validatorFactory;

    @Inject
    private FreemarkerTemplateEngine templateEngine;

    private Validator validator;
	
	@Route
	public void handleNewRegistration() {
		Spark.post(Routes.SIGNUP, (request, response) -> {
			try {
                if(!request.headers("Accept").equalsIgnoreCase("application/json")) {
                    response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
                    String error = new JSONObject()
                                        .put("error", "only json allowed")
                                        .toString();
                    return error;
                }
                response.type("application/json");
                ObjectMapper mapper = new ObjectMapper();
                UserDto userDto = mapper.readValue(request.body(), UserDto.class);
				validateInput(userDto);
				
				// create account and send activation email
				User user = userService.createNewUserAccount(userDto);
				AuthenticationToken verificationToken = userService.createVerificationToken(user);
				// TODO on another thread
				//emailService.sendActivationEmail(verificationToken);
				
				// generate session cookie
				AuthenticationToken sessionToken = userService.startSession(user);
				response.cookie("session", sessionToken.getToken(), 3600, true);
                response.status(HttpStatus.SC_TEMPORARY_REDIRECT);
                response.redirect("/");
                return null;

			} catch(ValidationException e) {
                logger.error("Invalid inputs", e);
                response.status(HttpStatus.SC_BAD_REQUEST);
                String errors[] = e.getMessage().split(".");
                return new JSONObject()
                            .put("errors", errors)
                            .put("status", "invalid inputs")
                            .toString();
			} catch(UserAlreadyExistsException e) {
                logger.error("Duplicate user", e);
                response.status(HttpStatus.SC_CONFLICT);
				return new JSONObject()
                            .put("errors", "Cannot process request")
							.put("status", "User is already registered")
                            .toString();
			}
		});
	}
	
	@Route
	public void handleCheckIfUsernameOrEmailIsValid() {
		Spark.post(Routes.FORM_OK, (request, response) -> {
			if(request.queryParams("username") != null) {
				String username = escapeHtml4(request.queryParams("username"));
				if(userService.isUsernameAlreadyTaken(username)) {
					response.status(HttpStatus.SC_CONFLICT);
                    response.body("Please pick another username");
				} else {
					response.status(HttpStatus.SC_OK);
				}
			} else if(request.queryParams("email") != null){
				String email = escapeHtml4(request.queryParams("email"));
				if(userService.isEmailAlreadyTaken(email)) {
					response.status(HttpStatus.SC_CONFLICT);
                    response.body("Please use another email address");
				} else {
					response.status(HttpStatus.SC_OK);
				}
			} else {
                response.status(HttpStatus.SC_BAD_REQUEST);
                response.body("Invalid use of api");
            }
			return null;
		});
	}
	
	// e.g localhost/register/validate?token=xhssySGGshhnx)
	@Route
    public void handleConfirmRegistration() {
    	Spark.get(Routes.ACTIVATE_ACCOUNT, (request, response) -> {
    		try {
				String token = escapeHtml4(request.queryParams("q"));
				AuthenticationToken verificationToken = userService.getVerificationToken(token);
				if(verificationToken.hasExpired()) {
					// TODO token has expired, resend activation link
					
					return null;
				}
				User user = verificationToken.getUser();
				userService.activateUser(user, verificationToken.getToken());
				// TODO set session cookie and redirect
				return null;
			} catch(InvalidTokenException e) {
				// TODO bad user
				return e.getMessage();
			}
    	});
	}
	
	@Route
	public void handleResendConfirmationLink() {
		
	}

    @Route
    public void renderRegistrationPage() {
        Spark.get(Routes.SIGNUP, (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("login_url", Routes.LOGIN);
            model.put("forgotpassword_url", Routes.RESET_PASSWORD);
            return templateEngine.render(new ModelAndView(model, "signup.ftl"));
        });
    }
    
    	
	private void validateInput(UserDto userDto) throws ValidationException {
		if(validator == null) {
			validator = validatorFactory.getValidator();
		}
		Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if(violations.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for(ConstraintViolation<UserDto> violation : violations) {
                ConstraintDescriptor<?> desc = violation.getConstraintDescriptor();
                stringBuilder.append(desc.getMessageTemplate());
                stringBuilder.append(".");
            }
            throw new ValidationException(stringBuilder.toString());
        }
	}
}
