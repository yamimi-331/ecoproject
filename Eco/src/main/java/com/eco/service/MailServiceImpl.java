package com.eco.service;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eco.domain.UserVO;
import com.eco.exception.ServiceException;
import com.eco.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Service
@Log4j
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;

	private final UserMapper mapper;
	
	@Value("${spring.mail.username}") // application.properties 에서 읽어옴
	private String fromEmail;

	@Override
	public String sendAuthCode(String toEmail) {
		String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(toEmail);
			message.setSubject("[Eco] 이메일 인증 코드");
			message.setText("인증코드는: " + code);
			message.setFrom(fromEmail);
			mailSender.send(message);
		} catch (Exception e) {
	        throw new ServiceException("이메일 전송 중 오류가 발생했습니다.", e);
		}
		return code;
	}

	@Override
	public boolean existsByEmail(String email) {
		boolean result = false;
		try {
			log.info("이메일 중복 체크 실행: " + email);
			UserVO rtnValue = mapper.existsByEmail(email);
			if(rtnValue != null) {
				result = true;
			}
			else {
				result = false;
			}
		} catch (Exception e) {
	        throw new ServiceException("이메일 중복 검사중 오류가 발생했습니다." 
	        	    + Arrays.toString(e.getStackTrace()) + "\n스트링: " + e.toString(),
	        	    e);
		}
		return result;
	}

}
