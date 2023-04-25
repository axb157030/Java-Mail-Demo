package com.email.demo.configuration;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.email.demo.util.EmailConstants;

@Configuration
public class MailConfiguration {
	
	@Autowired
	Environment environment;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(environment.getProperty(EmailConstants.EMAIL_HOST));
		javaMailSenderImpl.setPort(Integer.parseInt(environment.getProperty(EmailConstants.EMAIL_PORT)));
		javaMailSenderImpl.setUsername(environment.getProperty(EmailConstants.EMAIL_SENDER_EMAIL));
		javaMailSenderImpl.setPassword(environment.getProperty(EmailConstants.EMAIL_SENDER_PWD));
		
		Properties properties = javaMailSenderImpl.getJavaMailProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.debug", "true");
		
		return javaMailSenderImpl;
	} 
	@Bean
	public VelocityEngine velocityEngine() {
		VelocityEngine velocityEngine = new VelocityEngine();
		
		Properties properties = new Properties();
		velocityEngine.setProperty("resource.loader", "class");
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		properties.setProperty("file.resource.loader.path", "/resources/templates");
		velocityEngine.init(properties);
		
		return velocityEngine;
	}

}
