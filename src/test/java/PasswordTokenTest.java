import org.junit.Test;

import com.cti.auth.PasswordHasher;
import com.cti.config.ApplicationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class PasswordTokenTest {
	private Injector injector;
	
	public PasswordTokenTest() {
		injector = Guice.createInjector(new ApplicationModule());
	}
	
	
}
