package com.userManagement.service;

public interface EmailService {

	public boolean sendEmail(String subject, String body, String to);
}
