package com.greedy.newworker.employee.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.greedy.newworker.employee.repository.MailRepository;
import com.greedy.newworker.util.RedisUtil;

@Service
public class RegistCodeMailService implements MailRepository {
	
	private final RedisUtil redisUtil;
	
	public RegistCodeMailService(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	

	@Autowired
	JavaMailSender emailsender;
	
	// 인증 번호 담을 필드
	private String ePwd;

	/* 인증 번호 발송 */
	@Override
	public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
		
		MimeMessage message = emailsender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("[NEWWORKER] 인증 번호 안내 메일입니다.");
		
		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요.</h1>";
		msgg += "<h1>NEWWORKER입니다.</h1>";
		msgg += "<br>";
		msgg += "<p>귀하가 요청한 인증 번호를 안내 드립니다.<p>";
		msgg += "<br>";
		msgg += "<p>다음 번호를 인증 번호 입력란에 입력해 주세요.<p>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>인증 번호</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += ePwd + "</strong><div><br/> ";
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("newworker9999@gmail.com", "NEWWORKER"));// 보내는 사람

		return message;
	}

	// 인증 번호 작성
	@Override
	public String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}
		
		return key.toString();
		
	}

	
	@Override
	public String sendSimpleMessage(String to) throws Exception {
		ePwd = createKey();
		
		long etime = 60 * 5 * 1000;
		
		redisUtil.setDataExpire(ePwd, to, etime); // 유효 시간 설정하여 Redis에 저장
		
		MimeMessage message = createMessage(to); 
		emailsender.send(message);


		return ePwd;
	}
	
	
	
}
