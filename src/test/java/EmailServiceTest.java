import java.net.UnknownHostException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cti.auth.AuthenticationToken;
import com.cti.config.ApplicationModule;
import com.cti.model.User;
import com.cti.service.EmailService;
import com.cti.smtp.SMTPMailException;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class EmailServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	private EmailService emailService;
	
	public EmailServiceTest() {
		Injector injector = Guice.createInjector(new ApplicationModule());
		emailService = injector.getInstance(EmailService.class);
	}
	
	@Test
	public void testSendActivationEmail() throws SMTPMailException, UnknownHostException {
		User user = new User();
		user.setUsername("jwilshere");
		user.setEmail("ifeify92@gmail.com");
		user.setCollege("Arsenal FC");
		AuthenticationToken verificationToken = new AuthenticationToken(user);
		emailService.sendActivationEmail(verificationToken);
		logger.info(verificationToken.toString());
	}

}
