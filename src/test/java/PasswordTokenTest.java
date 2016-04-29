import org.junit.Test;

import com.cti.auth.PasswordToken;
import com.cti.config.ControllerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class PasswordTokenTest {
	private Injector injector;
	
	public PasswordTokenTest() {
		injector = Guice.createInjector(new ControllerModule());
	}
	
	
}
