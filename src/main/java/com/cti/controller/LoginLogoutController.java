package com.cti.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cti.annotation.Controller;
import com.cti.service.AuthenticationService;
import com.cti.service.UserService;

/**
 * Created by ifeify on 4/30/16.
 */
@Controller
public class LoginLogoutController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginLogoutController.class);
	@Inject
    private UserService userService;

	@Inject
	private AuthenticationService authService;

    @Inject
    public LoginLogoutController(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

//	@Route
//    public void handleLogout() {
//        Spark.get(Routes.LOGOUT, (request, response) -> {
//            try {
//                String sessionID = request.cookie("session");
//                if (sessionID != null && !sessionID.isEmpty()) {
//                    userService.endSession(sessionID);
//                    response.removeCookie("session");
//                }
//            } catch(Exception e) {
//                logger.error("Error logging user with ip {} out", request.ip(), e);
//            }
//            response.redirect("/");
//            return null;
//        });
//    }
//
//	@Route
//	public void handleLogin() {
//		Spark.post(Routes.LOGIN, (request, response) -> {
//			try {
//                if(!request.headers("Accept").contains("application/json")) {
//                    response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
//                    return new JSONObject()
//                                .put("error", "Only json allowed")
//                                .toString();
//                }
//                ObjectMapper mapper = new ObjectMapper();
//                LoginDTO loginDTO = mapper.readValue(request.body(), LoginDTO.class);
//                validateInput(loginDTO);
//				UserAccount userAccount = null;
//				if (loginDTO.getUsernameOrEmail().contains("@")) {
//					userAccount = userService.findByEmail(loginDTO.getUsernameOrEmail());
//				} else {
//					userAccount = userService.findByUsername(loginDTO.getUsernameOrEmail());
//				}
//                // TODO: userService.findByUserId()
//
//				if(userAccount != null && authService.isPasswordCorrect(userAccount.getPassword(), loginDTO.getPassword())) {
//					TokenGenerator token = userService.startSession(userAccount);
//					response.cookie("session", token.getToken());
//                    response.status(HttpStatus.SC_OK);
//                    return new JSONObject()
//                                .put("redirect", "/")
//                                .toString();
//				} else { // password incorrect or userAccount not found
//                    response.status(HttpStatus.SC_UNAUTHORIZED);
//                }
//			} catch(ValidationException e) {
//                logger.error("Invalid inputs", e);
//                response.status(HttpStatus.SC_BAD_REQUEST);
//			} catch(Exception e) {
//                logger.error("Failed to log user with ip {} and login params {}", request.ip(), request.body(), e);
//                response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//            }
//            return null;
//		});
//	}
//
//    @Route
//    public void getLoginPage() {
//        Spark.get(Routes.LOGIN, (request, response) -> {
//            try {
//                String sessionID = request.cookie("session");
//                if (sessionID != null && !sessionID.isEmpty()) {
//                    UserAccount userAccount = userService.findUserBySessionID(sessionID);
//                    if(userAccount != null) {
//                        response.redirect("/");
//                        return null;
//                    }
//                }
//                Map<String, String> model = new HashMap<>();
//                model.put("signup_url", Routes.SIGNUP);
//                return templateEngine.render(new ModelAndView(model, "login.ftl"));
//            } catch(Exception e) {
//                logger.error("Cannot render login page", e);
//                response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//                response.redirect(Routes.ERROR);
//                return null;
//            }
//
//        });
//    }
//
//    private void validateInput(LoginDTO loginDTO) throws ValidationException {
//        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);
//        if(violations.size() > 0) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for(ConstraintViolation<LoginDTO> violation : violations) {
//                ConstraintDescriptor<?> desc = violation.getConstraintDescriptor();
//                stringBuilder.append(desc.getMessageTemplate());
//                stringBuilder.append(".");
//            }
//            throw new ValidationException(stringBuilder.toString());
//        }
//    }
}
