import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import com.cti.dto.UserDto;

public class UserDtoTest {
	private Validator validator;
	
	public UserDtoTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}
	
	@Test
	public void testNewUserWithBadUsername() {
		UserDto newUser = new UserDto();
		newUser.setUsername("ba");
		newUser.setEmail("me@cti.com");
		newUser.setPassword("jjjjjjjj");
		newUser.setMatchingPassword("jjjjjjjj");
		newUser.setCollege("Wichita State University");
		Set<ConstraintViolation<UserDto>> violations = validator.validate(newUser);
		assertEquals(violations.size(), 1);
	}
	
	@Test
	public void testNewUserWithDifferentPasswords() {
		UserDto newUser = new UserDto();
		newUser.setUsername("newuser");
		newUser.setEmail("me@cti.com");
		newUser.setPassword("jjjjjjjjx");
		newUser.setMatchingPassword("dsdsdsddsdssdsds");
		newUser.setCollege("Wichita State University");
		Set<ConstraintViolation<UserDto>> violations = validator.validate(newUser);
		assertEquals(violations.size(), 1);
	}
	
	@Test
	public void testPasswordTooShort() {
		UserDto newUser = new UserDto();
		newUser.setUsername("newuser");
		newUser.setEmail("me@cti.com");
		newUser.setPassword("nnnn");
		newUser.setMatchingPassword("nnnn");
		newUser.setCollege("Wichita State University");
		Set<ConstraintViolation<UserDto>> violations = validator.validate(newUser);
		assertEquals(violations.size(), 2);
	}
}
