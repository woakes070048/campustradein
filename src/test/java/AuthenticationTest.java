import com.cti.common.auth.Encrypter;
import com.cti.common.auth.Password;
import com.cti.common.auth.Password.Builder;
import com.cti.common.auth.Password.PasswordParser;
import com.cti.config.AppConfig;
import com.cti.common.exception.EncryptionException;
import com.cti.common.exception.PasswordParseException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AuthenticationTest {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);
    private Encrypter encrypter;
	
	public AuthenticationTest() {
		Injector injector = Guice.createInjector(new AppConfig());
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
