import com.cti.config.ApplicationConfig;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.exception.UserNotFoundException;
import com.cti.model.UserAccount;
import com.cti.repository.UserRepository;
import com.cti.repository.UserRepository2;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author ifeify
 */
@Ignore
public class UserRepositoryTest {
    private UserRepository2 userRepository;
    private UserAccount userAccount;

    public UserRepositoryTest() {
        userRepository = Guice.createInjector(new ApplicationConfig()).getInstance(UserRepository2.class);
    }

    @Before
    public void setUp() throws UserAlreadyExistsException {
        userAccount = new UserAccount();
        userAccount.setUsername("jackwilshere");
        userAccount.setEmail("jwilly@arsenal.com");
        userAccount.setPassword("iamnumber10");
        userAccount.setCollege("Arsenal FC");
        userRepository.addUser(userAccount);
    }


    @Test(expected = UserAlreadyExistsException.class)
    public void testAddingDuplicateUser() throws UserAlreadyExistsException {
        userRepository.addUser(userAccount);
    }

    @After
    public void cleanUp() {

    }
}
