package com.eco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eco.domain.UserVO;
import com.eco.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class SignupController {
	
	private UserService service;
	
	// 회원가입 페이지로 이동
	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
	// 회원가입 DB INSERT
	@PostMapping("/signup")
	public String signupPost(UserVO user) {
		user.setUser_type("B");
		service.signup(user);
		return "login";
	}
	
	// 아이디 중복 확인
	@GetMapping("/check-id")
	@ResponseBody
	public String checkUserId(@RequestParam("user_id") String userId) {
	    // 일반회원(user_type = "B") 기준으로만 중복 확인
	    UserVO user = service.findByUserId(userId, "B");
	    return (user != null) ? "duplicate" : "available";
	}

}
