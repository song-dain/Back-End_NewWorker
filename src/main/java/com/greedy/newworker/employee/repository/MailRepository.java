package com.greedy.newworker.employee.repository;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface MailRepository {

	MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;
	
	String createKey();
	
	String sendSimpleMessage(String to) throws Exception;
}
