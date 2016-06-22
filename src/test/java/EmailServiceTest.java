import com.cti.config.AppConfig;
import com.cti.config.FreemarkerTemplateEngine;
import com.cti.model.UserAccount;
import com.cti.service.EmailService;
import com.cti.smtp.SMTPMailException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Ignore;
import org.junit.Test;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class EmailServiceTest {
	private EmailService emailService;
	private FreemarkerTemplateEngine templateEngine;
	
	public EmailServiceTest() {
		Injector injector = Guice.createInjector(new AppConfig());
		emailService = injector.getInstance(EmailService.class);
        templateEngine = injector.getInstance(FreemarkerTemplateEngine.class);
	}
	
	@Test
	public void testSendActivationEmail() throws SMTPMailException {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("jackwilshere");
        userAccount.setEmail("ifeify92@gmail.com");
        Map<String, String> model = new HashMap<>();
        model.put("activation_url", "http://www.campustradein.com");
        model.put("username", userAccount.getUsername());
        String message = templateEngine.render(new ModelAndView(model, "activation_email.ftl"));
        emailService.sendActivationEmail(userAccount, message);
	}

}
