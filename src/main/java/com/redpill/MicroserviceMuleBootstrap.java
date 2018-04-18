package com.redpill;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;


import org.apache.logging.log4j.LogManager;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.logging.log4j.core.LoggerContext;


/**
 * Microservice Mule bootstrap.
 */

public class MicroserviceMuleBootstrap {


	static final Logger logger = LoggerFactory.getLogger(MicroserviceMuleBootstrap.class);
	static final String[] propertiesFiles = { "mule-app.properties", "mule-deploy.properties" };

	DefaultMuleContextFactory muleContextFactory;
	SpringXmlConfigurationBuilder configBuilder;
	MuleContext muleContext;
	MuleClient client;
	MuleMessage message;
	MuleMessage response;

	public MicroserviceMuleBootstrap() {
		try {
			setup();
		} catch (InitialisationException | ConfigurationException ex) {
			logger.error("Error initializing Mule: ", ex.getMessage(), ex);
			System.exit(10);
		}
	}

	public static void main(String[] args) {

		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);

		MicroserviceMuleBootstrap main = null;
		main = new MicroserviceMuleBootstrap();

		File file = main.getFile("log4j2.xml");
		// this will force a reconfiguration
		context.setConfigLocation(file.toURI());

		try {
			main.startMule();
		} catch (MuleException me) {
			logger.error("Error running flow: ", me);
		}
	}


	Properties getStartUpProperties() {
		Properties props = new Properties();
		for (String propertiesFile : propertiesFiles) {
			try {
				props.load(getSystemResourceAsStream(propertiesFile));
			} catch (IOException | NullPointerException ex) {
				logger.error(format("Could not find properties file \"%s\" on classpath.", propertiesFile));
			}
		}
		return props;
	}

	void setup() throws ConfigurationException, InitialisationException {
		Properties props = getStartUpProperties();
		String str[] = props.getProperty("config.resources").split(",");
		muleContextFactory = new DefaultMuleContextFactory();
		configBuilder = new SpringXmlConfigurationBuilder(str);
		muleContext = muleContextFactory.createMuleContext(configBuilder, props);
	}

	void startMule() throws MuleException {
		logger.info("Starting Mule Context.......");
		muleContext.start();
		client = muleContext.getClient();
	}

	void stopMule() {
		try {
			muleContext.stop();
		} catch (MuleException me) {
			logger.warn("Error stopping mule: " + me.getMessage());
		}
		muleContext.dispose();
	}

	private  File getFile(String fileName) {


		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		return file;

	}

}