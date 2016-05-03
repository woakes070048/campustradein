import com.cti.config.ApplicationModule;
import com.cti.dto.UserDto;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;
import com.cti.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	private UserService userService;

	public UserServiceTest() {
		Injector injector = Guice.createInjector(new ApplicationModule());
		this.userService = injector.getInstance(UserService.class);
	}
	
	@Before
	public void addOneUser() throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setUsername("jackwilshere");
        userDto.setEmail("jwilshere@arsenal.com");
        userDto.setPassword("iAmNumber10");
        userDto.setMatchingPassword("iAmNumber10");
        userDto.setCollege("Arsenal Football Club");
        User user = userService.createNewUserAccount(userDto);
        logger.info("First user added: {}", user);
	}

    @Test(expected = UserAlreadyExistsException.class)
    public void testAddingDuplicateUsername() throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setUsername("jackwilshere");
        userDto.setEmail("jack.wilshere@arsenal.com");
        userDto.setPassword("justGotBackFromInjuryBoy");
        userDto.setMatchingPassword("justGotBackFromInjuryBoy");
        userDto.setCollege("Arsenal Football Club");
        User user = userService.createNewUserAccount(userDto);
    }
}
