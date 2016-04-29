import org.junit.Test;

import com.cti.auth.Encrypter;
import com.cti.auth.PasswordHasher;
import com.cti.auth.PasswordVerifier;
import com.cti.config.ApplicationModule;
import com.cti.exception.EncryptionException;
import com.cti.exception.PasswordMismatchException;
import com.cti.exception.PasswordParseException;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class AuthenticationTest {
	private PasswordHasher passwordToken;
	private Encrypter encrypter;
	
	public AuthenticationTest() {
		Injector injector = Guice.createInjector(new ApplicationModule());
		this.encrypter = injector.getInstance(Encrypter.class);
		passwordToken = new PasswordHasher(encrypter);
	}
	
	@Test(expected = PasswordMismatchException.class)
	public void testPasswordMismatch() throws EncryptionException, PasswordMismatchException, PasswordParseException {
		String passwordToStore = passwordToken.generateHash("password");
		PasswordVerifier verifier = new PasswordVerifier(passwordToStore, "password2");
		verifier.verify();
	}
	
	@Test
	public void testGoodPassword() throws EncryptionException, PasswordMismatchException, PasswordParseException {
		String passwordToStore = passwordToken.generateHash("ax_U-sxUim$v");
		PasswordVerifier verifier = new PasswordVerifier(passwordToStore, "ax_U-sxUim$v");
		verifier.verify();
	}
	
}
