import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import com.cti.dto.UserDTO;

public class UserAccountDtoTest {
	private Validator validator;
	
	public UserAccountDtoTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}
	
	@Test
	public void testNewUserWithBadUsername() {
		UserDTO newUser = new UserDTO();
		newUser.setUsername("ba");
		newUser.setEmail("me@cti.com");
		newUser.setPassword("jjjjjjjj");
		newUser.setMatchingPassword("jjjjjjjj");
		newUser.setCollege("Wichita State University");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(newUser);
		assertEquals(violations.size(), 1);
	}
	
	@Test
	public void testNewUserWithDifferentPasswords() {
		UserDTO newUser = new UserDTO();
		newUser.setUsername("newuser");
		newUser.setEmail("me@cti.com");
		newUser.setPassword("jjjjjjjjx");
		newUser.setMatchingPassword("dsdsdsddsdssdsds");
		newUser.setCollege("Wichita State University");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(newUser);
		assertEquals(violations.size(), 1);
	}
	
	@Test
	public void testPasswordTooShort() {
		UserDTO newUser = new UserDTO();
		newUser.setUsername("newuser");
		newUser.setEmail("me@cti.com");
		newUser.setPassword("nnnn");
		newUser.setMatchingPassword("nnnn");
		newUser.setCollege("Wichita State University");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(newUser);
		assertEquals(violations.size(), 2);
	}
}
