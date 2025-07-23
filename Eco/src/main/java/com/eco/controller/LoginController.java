package com.eco.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eco.domain.UserVO;
import com.eco.service.OAuthService;
import com.eco.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
	// 사용자 서비스
	private final UserService service;
	private final OAuthService oAuthService;
        
    // 로그인 페이지 진입
	@GetMapping("")
	public String loginPage() {
		return "login";
	}

	// 로그인 버튼 클릭시 실행
	@PostMapping("")
	public String loginPost(UserVO user, HttpSession session, Model model) {
		UserVO rtnUser = service.login(user);
		if (rtnUser != null) {
			// 로그인 성공 처리
			session.setAttribute("currentUserInfo", rtnUser);
			return "redirect: /";
		} else { 
			// 로그인 실패 처리
	        model.addAttribute("errorMessage", "아이디 및 비밀번호가 틀립니다.");
			return "login";
		}
	}

	// # 구글 로그인 start --------------------------------------------------------
	// 1. 사용자 로그인 URL을 반환
	@GetMapping("/googleLogin")
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(oAuthService.getGoogleLoginUrl());
	}

	// 2. 예외 처리
	@GetMapping("/oauth2callback")
	public String oauth2Callback(@RequestParam("code") String code, HttpSession session, Model model) throws IOException {
        UserVO user = oAuthService.processGoogleLogin(code);
        
        if (user == null) {
        	// 로그인 실패 처리
	        model.addAttribute("errorMessage", "구글 로그인 실패");
            return "redirect:/login";
        }
        // 세션에 사용자 정보 저장
        session.setAttribute("currentUserInfo", user);
		return "redirect:/";
	}
	// # 구글 로그인 End --------------------------------------------------------

	// # Naver Login Start ---------------------------------------------------
	// 네이버 로그인 페이지로 리다이렉트
    @GetMapping("/naverLogin")
    public void naverLogin(HttpServletResponse response) throws IOException {
    	 // 서비스에서 네이버 로그인 URL 생성
        String naverAuthUrl = oAuthService.getNaverLoginUrl();
        response.sendRedirect(naverAuthUrl);
    }

	// 2. 콜백 처리 - code와 state 받음
    @GetMapping("/oauth2/callback/naver")
    public String naverCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state,
                                HttpSession session,
                                Model model) throws IOException {
    	// 서비스에 로그인 처리 위임
        UserVO user = oAuthService.processNaverLogin(code, state);

        if (user == null) {
        	// 로그인 실패 처리
	        model.addAttribute("errorMessage", "네이버 로그인 실패");
            return "redirect:/login";
        }

        // 세션에 사용자 정보 저장
        session.setAttribute("currentUserInfo", user);

        return "redirect:/"; 
    }
	// # Naver Login End ---------------------------------------------------

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";  // 홈이나 로그인 페이지로 이동
    }
}
