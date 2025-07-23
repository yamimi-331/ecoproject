package com.eco.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eco.domain.UserVO;
import com.eco.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class MypageController {
	
	private UserService service;
	
	//회원 정보 수정 페이지 이동
	@GetMapping("/mypage")
	public String myPage(Model model, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("currentUserInfo");
		model.addAttribute("userId", user.getUser_id());
		model.addAttribute("userPw", user.getUser_pw());
		model.addAttribute("userName", user.getUser_nm());
		model.addAttribute("userLocal", user.getUser_local());
		model.addAttribute("userCd", user.getUser_cd());
		model.addAttribute("userType", user.getUser_type());
		return "mypage";
	}
	
	//회원 정보 수정
	@PostMapping("/mypageUpdate")
	public String userUpdate(UserVO user, HttpSession session, RedirectAttributes redirectAttributes) {
		service.userModify(user);
		
		// 수정된 최신 사용자 정보를 다시 조회, 세션에 최신 정보 반영
	    UserVO updatedUser = service.findByUserCD(user.getUser_cd());
	    session.setAttribute("currentUserInfo", updatedUser);
	    
		// alert용 메시지 전달
	    redirectAttributes.addFlashAttribute("msg", "회원정보가 수정 되었습니다.");		
		return "redirect:/";
	}
	
	//회원 탈퇴
	@PostMapping("/mypageDelete")
	public String userDelete(HttpSession session, RedirectAttributes redirectAttributes) {
		UserVO user = (UserVO) session.getAttribute("currentUserInfo");
		service.userDelete(user.getUser_cd());
		
		// 로그아웃 후 메인페이지로 이동
		session.invalidate();
		redirectAttributes.addFlashAttribute("msg", "회원탈퇴가 완료 되었습니다.");		
		return "redirect:/";
	}
}
