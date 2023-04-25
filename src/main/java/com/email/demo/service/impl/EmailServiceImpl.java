package com.email.demo.service.impl;

import java.util.Date;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import java.io.StringWriter;

import com.email.demo.service.EmailService;
import com.email.demo.util.EmailConstants;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	

	@Override
	public void emailHappyBirthDay(String name) {
		
		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper msg = new MimeMessageHelper(mimeMessage);
				
				msg.setTo(environment.getProperty(EmailConstants.EMAIL_RECIPIENT_DETAIL_EMAIL));
				msg.setFrom(EmailConstants.EMAIL_SENDER_EMAIL);
				msg.setSubject(EmailConstants.EMAIL_SUBJECT);
				msg.setSentDate(new Date());
				
				VelocityContext velocityContext = new VelocityContext();
				StringWriter writer = new StringWriter();
				velocityContext.put("name", name);
				Template happyBirthdayTemplate = velocityEngine.getTemplate("templates/EmailHappyBirthdayTemplate.vm");
				happyBirthdayTemplate.merge( velocityContext, writer);
				msg.setText(writer.toString(), true);
			}
		};
		
		try {
			javaMailSender.send(mimeMessagePreparator);
		}
		catch(Exception e) {
			logger.info("Exception when sending email", e);
			throw e;
		}
		
		
	}

}
