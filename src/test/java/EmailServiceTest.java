import com.cti.config.ApplicationConfig;
import com.cti.config.FreemarkerTemplateEngine;
import com.cti.model.User;
import com.cti.service.EmailService;
import com.cti.smtp.SMTPMailException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class EmailServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	private EmailService emailService;
	private FreemarkerTemplateEngine templateEngine;
	
	public EmailServiceTest() {
		Injector injector = Guice.createInjector(new ApplicationConfig());
		emailService = injector.getInstance(EmailService.class);
        templateEngine = injector.getInstance(FreemarkerTemplateEngine.class);
	}
	
	@Test
	public void testSendActivationEmail() throws SMTPMailException {
        User user = new User();
        user.setUsername("jackwilshere");
        user.setEmail("ifeify92@gmail.com");
        Map<String, String> model = new HashMap<>();
        model.put("activation_url", "http://www.campustradein.com");
        model.put("username", user.getUsername());
        String message = templateEngine.render(new ModelAndView(model, "activation_email.ftl"));
        emailService.sendActivationEmail(user, message);
	}

}
