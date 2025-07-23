package com.eco.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eco.service.MailService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EmailAuthController {
	private MailService mailService;
	
	@PostMapping(value="/send-code", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String sendCode(@RequestParam("email") String email, HttpSession session) {
		if (email == null || email.trim().isEmpty()) {
			return "잘못된 이메일 주소입니다.";
		}

	   if (mailService.existsByEmail(email)) {
	        return "이미 등록된 이메일입니다.";
	    }
		try {
			String code = mailService.sendAuthCode(email);
			session.setAttribute("authCode", code);
			return "인증 코드가 발송되었습니다.";
		} catch (Exception e) {
			e.printStackTrace();
			return "메일 발송 중 오류가 발생했습니다.";
		}
	}

	@PostMapping("/verify-code")
	@ResponseBody
	public String verifyCode(@RequestParam("code") String code, HttpSession session) {
		String sessionCode = (String) session.getAttribute("authCode");
		if (sessionCode != null && sessionCode.equals(code)) {
			return "success";
		} else {
			return "fail";
		}
	}
}
