package com.userManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.userManagement.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public boolean sendEmail(String subject, String body, String to) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

			helper.setSubject(subject);
			helper.setText(body);
			helper.setTo(to);

			mailSender.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
