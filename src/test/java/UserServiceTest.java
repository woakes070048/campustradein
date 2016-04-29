import org.junit.Test;

import com.cti.config.ControllerModule;
import com.cti.dto.UserDto;
import com.cti.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class UserServiceTest {
	private UserService userService;
	
	public UserServiceTest() {
		Injector injector = Guice.createInjector(new ControllerModule());
		this.userService = injector.getInstance(UserService.class);
	}
	
	@Test
	public void testAddNewUser() {
		UserDto userDto = new UserDto();
		userDto.setUsername("admin");
		userDto.setPassword("adminpassword");
		userDto.setMatchingPassword("adminpassword");
		userDto.setEmail("admin@cti.com");
		userDto.setCollege("Admin College");
		userService.registerNewUserAccount(userDto);
	}
	
}
