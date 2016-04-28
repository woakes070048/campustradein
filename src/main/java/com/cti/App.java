package com.cti;

import java.util.Set;

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
		initializeGuiceContainer();
		initializeControllers();
	}

	private void initializeControllers() throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage("com.cti.controller"))
				.setScanners(new SubTypesScanner(),
						new TypeAnnotationsScanner()));

		Set<Class<?>> controllers = reflections
				.getTypesAnnotatedWith(com.cti.annotation.Controller.class);
		
		
		for (Class<?> clazz : controllers) {
			logger.info("setting up {}", clazz.getName());
			clazz.newInstance();
		}
	}
	
	private void initializeGuiceContainer() {}
}
