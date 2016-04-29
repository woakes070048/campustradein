package com.cti;

import java.util.Set;

import com.cti.config.ApplicationModule;
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

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		try {
    		new App(Integer.valueOf(System.getProperty("port")));
    	} catch(Exception e) {
    		logger.error("Cannot start application", e);
    	}
	}
	
	public App(int port) throws Exception {
		Spark.port(port);
		Spark.staticFileLocation("/views");
		initializeControllers();
	}

	/**
	 * Find all controller classes annotated with the {@code Controller} annotation
     * and use Guice dependency injection framework to instantiate them and
     * automatically inject dependencies
	 * @throws InstantiationException
	 * @throws IllegalAccessException
     */
	private void initializeControllers() throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage("com.cti.controller"))
				.setScanners(new SubTypesScanner(),
						new TypeAnnotationsScanner()));

		Set<Class<?>> controllers = reflections
				.getTypesAnnotatedWith(com.cti.annotation.Controller.class);
		
		Injector injector = Guice.createInjector(new ApplicationModule());
		for (Class<?> clazz : controllers) {
			logger.info("setting up {}", clazz.getName());
			injector.getInstance(clazz);
		}
	}
}
