package com.greedy.newworker.employee.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.repository.MailRepository;

@Service
public class FindPwdMailService implements MailRepository {

	@Autowired
	JavaMailSender emailsender;
	
	// 임시 비밀번호 담을 필드
	private String tempPwd;

	// 임시 비밀번호 발송
	@Override
	public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
		
		MimeMessage message = emailsender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("[NEWWORKER] 임시 비밀번호 발송 메일입니다.");
		
		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요.</h1>";
		msgg += "<h1>NEWWORKER입니다.</h1>";
		msgg += "<br>";
		msgg += "<p>귀하의 임시 비밀번호를 안내 드립니다.<p>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>임시 비밀번호</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += tempPwd + "</strong><div><br/> ";
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("newworker9999@gmail.com", "NEWWORKER"));// 보내는 사람

		return message;
	}

	@Override
	public String createKey() {
		StringBuffer key = new StringBuffer();
		Random ran = new Random();
		
		for (int i = 0; i < 8; i++) {
			int index = ran.nextInt(3);
			
			switch (index) {
			case 0:
				key.append((char) ((int) (ran.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (ran.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((ran.nextInt(10)));
				// 0~9
				break;
			}
		}
		return key.toString();
	}

	@Override
	public String sendSimpleMessage(String to) throws Exception {
		tempPwd = createKey();
		
		MimeMessage message = createMessage(to);
//		try {
			emailsender.send(message);
//		} catch(MailException es) {
//			es.printStackTrace();
//			throw new IllegalArgumentException();
//		}
		
		return tempPwd;
	}
	
}
