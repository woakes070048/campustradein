package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.auth.AuthenticationToken;
import com.cti.config.Routes;
import com.cti.dto.UserDto;
import com.cti.exception.InvalidTokenException;
import com.cti.model.User;
import com.cti.service.EmailService;
import com.cti.service.UserService;
import org.apache.http.HttpStatus;
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
    private EmailService authenticationService;
    
    @Inject 
    private ValidatorFactory validatorFactory;
    
    private Validator validator;
	
	@Inject
	public RegistrationController(UserService userService, ValidatorFactory validatorFactory) {
		this.userService = userService;
		this.validatorFactory = validatorFactory;
		this.validator = validatorFactory.getValidator();
	}
	
	@Route
	private void handleNewRegistration() {
		Spark.post(Routes.signup, (request, response) -> {
			try {
				String username = request.queryParams("username");
				String email = request.queryParams("email");
				String password = request.queryParams("password");
				String password2 = request.queryParams("password2");
				String college = request.queryParams("college");
				
				// validate input
				UserDto userDto = new UserDto();
				userDto.setUsername(escapeHtml4(username));
				userDto.setEmail(escapeHtml4(email));
				userDto.setPassword(escapeHtml4(password));
				userDto.setMatchingPassword(escapeHtml4(password2));
				userDto.setCollege(escapeHtml4(college));
				validateInput(userDto);
				
				// create account and send activation email
				User user = userService.createNewUserAccount(userDto);
				AuthenticationToken verificationToken = userService.createVerificationToken(user);
				// TODO on another thread
				authenticationService.sendActivationEmail(verificationToken);
				
				// generate session cookie
				AuthenticationToken sessionToken = userService.startSession(user);
				Map<String, String> model = new HashMap<>();
				model.put("username", username);
				model.put("needsActivation", "true"); // put anything, doesn't matter
				response.cookie("session", sessionToken.getToken(), 3600, true);
				return new ModelAndView(model, "welcome.ftl");
			} catch(ValidationException e) {
				return e.getMessage();
			} catch(Exception e) {
				return e.getMessage();
			}
		});
	}
	
	@Route
	public void handleCheckIfUsernameOrEmailIsValid() {
		Spark.post(Routes.formOK, (request, response) -> {
			if(request.queryParams("username") != null) {
				String username = escapeHtml4(request.queryParams("username"));
				if(userService.isUsernameAlreadyTaken(username)) {
					response.status(HttpStatus.SC_CONFLICT);
				} else {
					response.status(HttpStatus.SC_OK);
				}
			} else {
				String email = escapeHtml4(request.queryParams("email"));
				if(userService.isEmailAlreadyTaken(email)) {
					response.status(HttpStatus.SC_CONFLICT);
				} else {
					response.status(HttpStatus.SC_OK);
				}
			}
			return null;
		});
	}
	
	// e.g localhost/register/validate?token=xhssySGGshhnx)
	@Route
    public void handleConfirmRegistration() {
    	Spark.get(Routes.activate, (request, response) -> {
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
    
    	
	private void validateInput(UserDto userDto) throws ValidationException {
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
