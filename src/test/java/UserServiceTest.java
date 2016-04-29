import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cti.config.ApplicationModule;
import com.cti.dto.UserDto;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;
import com.cti.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class UserServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	private UserService userService;
	private UserDto userDto;
	
	public UserServiceTest() {
		Injector injector = Guice.createInjector(new ApplicationModule());
		this.userService = injector.getInstance(UserService.class);
		
		userDto = new UserDto();
		userDto.setUsername("jackwilshere");
		userDto.setEmail("jwilshere@arsenal.com");
		userDto.setPassword("iAmNumber10");
		userDto.setMatchingPassword("iAmNumber10");
		userDto.setCollege("Arsenal Football Club");
	}
	
	@Test
	public void testNewUserRegistration() throws UserAlreadyExistsException {
		User user = userService.registerNewUserAccount(userDto);
		logger.info(user.toString());
	}
}
