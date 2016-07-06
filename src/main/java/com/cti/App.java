package com.cti;

import com.cti.common.annotation.Route;
import com.cti.config.AppConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		try {
			if(System.getProperty("port") == null && System.getProperty("domainName") == null) {
				logger.error("Please specify a port and domain name");
				System.exit(-1);
			}
    		new App(Integer.valueOf(System.getProperty("port")));
    	} catch(Exception e) {
    		logger.error("Failed to launch application", e);
    	}
	}
	
	public App(int port) throws Exception {
		Spark.port(port);
		Spark.staticFileLocation("/html");
		initializeControllers();
//        Spark.awaitInitialization();
	}

	/**
	 * Find all controller classes annotated with the {@code Controller} annotation
     * and use Guice dependency injection framework to instantiate them and
     * automatically inject dependencies
	 * @throws InstantiationException
	 * @throws IllegalAccessException
     */
	private void initializeControllers() throws InstantiationException, IllegalAccessException, InvocationTargetException {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage("com.cti.controller"))
				.setScanners(new SubTypesScanner(),
						new TypeAnnotationsScanner()));

		Set<Class<?>> controllers = reflections
				.getTypesAnnotatedWith(com.cti.common.annotation.Controller.class);
		
		Injector injector = Guice.createInjector(new AppConfig());
		for (Class<?> clazz : controllers) {
			logger.info("setting up {}", clazz.getName());

			Object controller = injector.getInstance(clazz);
			Method[] methods = controller.getClass().getMethods();
			for(Method method : methods) {
				if(method.isAnnotationPresent(Route.class)) {
					method.invoke(controller);
				}
			}
		}
	}
}
