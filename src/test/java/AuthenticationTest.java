import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cti.auth.Encrypter;
import com.cti.auth.Password;
import com.cti.auth.Password.Builder;
import com.cti.auth.Password.PasswordParser;
import com.cti.config.ApplicationConfig;
import com.cti.exception.EncryptionException;
import com.cti.exception.PasswordParseException;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class AuthenticationTest {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);
    private Encrypter encrypter;
	
	public AuthenticationTest() {
		Injector injector = Guice.createInjector(new ApplicationConfig());
		this.encrypter = injector.getInstance(Encrypter.class);
	}

	@Test
	public void testPasswordMismatch() throws EncryptionException {
		Password password = encryptPassword("OlivierGiroud");
        Password password2 = encryptPassword("oliviergiroud");
        logger.info("password1: {}\npassword2: {}", password, password2);
        assertFalse(password.equals(password2));
    }
	
	@Test
	public void testParsingCorrectPassword() throws EncryptionException, PasswordParseException {
        // generate new password
        Password password = encryptPassword("xV_ihjaAs");
        Password parsedPassword = PasswordParser.parse(password.toString());
        assertTrue(password.equals(parsedPassword));
	}

    @Test
    public void testFailedPasswordVerificationAttempt() throws EncryptionException, PasswordParseException {
        String savedPassword = encryptPassword("jackwilshere").toString();
        Password parsedSavedPassword = PasswordParser.parse(savedPassword);
        Password wrongPassword = encryptPassword("jackWishere", parsedSavedPassword);
        assertFalse(parsedSavedPassword.equals(wrongPassword));
    }

    @Test
    public void testSuccessfulPasswordVerificationAttempt() throws EncryptionException, PasswordParseException {
        String savedPassword = encryptPassword("mesutOzil_11").toString();
        Password parsedSavedPassword = PasswordParser.parse(savedPassword);
        Password correctPassword = encryptPassword("mesutOzil_11", parsedSavedPassword);

        logger.info("Parsed saved password: {}", parsedSavedPassword);
        logger.info("Password generated using template {}", correctPassword);
        assertTrue(parsedSavedPassword.equals(correctPassword));
    }

    private Password encryptPassword(String plainTextPassword) throws EncryptionException {
        return new Builder()
                    .plainTextPassword(plainTextPassword)
                    .useEncrypter(encrypter)
                    .hash();
    }

    private Password encryptPassword(String plainTextPassword, Password template) throws EncryptionException {
        return new Builder()
                    .plainTextPassword(plainTextPassword)
                    .useSalt(template.getSalt())
                    .iterations(template.getIterations())
                    .hashSize(template.getHashSize())
                    .useEncrypter(template.getEncrypter())
                    .hash();
    }


}
