package com.eco.controller;

import java.sql.Timestamp; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eco.domain.ElecUsageVO;
import com.eco.domain.GasUsageVO;
import com.eco.domain.UserVO;
import com.eco.service.AdminService;
import com.eco.service.UsageService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	// 관리자 서비스
	private AdminService adminService;
	// 사용량 서비스
	private UsageService service; 
	
	// admin 페이지로 이동
	@GetMapping("")
	public String adminPage(Model model, HttpSession session) {
		// session의 유저 정보 가져오기
		UserVO user = (UserVO) session.getAttribute("currentUserInfo");
		model.addAttribute("currentUserInfo", user);
		// 가스/전기 타입 목록 조회 후 admin 페이지로 전달
		model.addAttribute("gasList", service.getAllGasTypes());
	    model.addAttribute("elecList", service.getAllElecTypes());
	    return "admin"; 
	}

	// 사용자 검색
	@GetMapping("/search-users")
	@ResponseBody
	public List<UserVO> searchUser(@RequestParam String keyword) {
		// 사용자 이름 기반으로 검색하여 결과를 List로 반환
		List<UserVO> result = adminService.searchUsers(keyword);
		return result;
	}

	// 전기 사용내역 조회
	@GetMapping("/user/{userCd}/elec-usage")
	@ResponseBody
	public List<ElecUsageVO> getElecUsage(@PathVariable int userCd) {
		// 사용자 cd값 기준 전기 사용량 전체 조회
		return adminService.getElecUsageByUser(userCd);
	}

	// 가스 사용내역 조회
	@GetMapping("/user/{userCd}/gas-usage")
	@ResponseBody
	public List<GasUsageVO> getGasUsage(@PathVariable int userCd) {
		// 사용자 cd값 기준 가스 사용량 전체 조회
		return adminService.getGasUsageByUser(userCd);
	}

	// 가스 사용량 등록
	@PostMapping("/gas/insert")
	@ResponseBody
	public Map<String, Object> insertGasUsage(@RequestBody GasUsageVO vo) {
		// Date ( YYYY-MM-DD )형식 포맷을위한 Timestamp 사용
		if (vo.getGas_time() != null) {
	        Timestamp timestamp = new Timestamp(vo.getGas_time().getTime());
	        vo.setGas_time(timestamp);
		}
		
		boolean result = adminService.insertGas(vo);
		return Map.of("success", result);
	}

	// 전기 사용량 등록
	@PostMapping("/elec/insert")
	@ResponseBody
	public Map<String, Object> insertElecUsage(@RequestBody ElecUsageVO vo) {
		// Date ( YYYY-MM-DD )형식 포맷을위한 Timestamp 사용
		if (vo.getElec_time() != null) {
	        Timestamp timestamp = new Timestamp(vo.getElec_time().getTime());
	        vo.setElec_time(timestamp);
		} 
		boolean result = adminService.insertElec(vo);
		return Map.of("success", result);
	}

	// 전기 수정
	@PostMapping("/elec/update")
	@ResponseBody
	public Map<String, Object> updateElec(@RequestBody ElecUsageVO vo) {
		boolean result = adminService.updateElec(vo);
		// 서비스 결과에 따라 "success" 키와 함께 boolean 값을 JSON 응답으로 반환
		return Map.of("success", result);
	}

	// 전기 삭제
	@PostMapping("/elec/delete")
	@ResponseBody
	public Map<String, Object> deleteElec(@RequestBody ElecUsageVO vo) {
		boolean result = adminService.deleteElec(vo.getUsage_cd());
		// 서비스 결과에 따라 "success" 키와 함께 boolean 값을 JSON 응답으로 반환
		return Map.of("success", result);
	}

	// 가스 수정
	@PostMapping("/gas/update")
	@ResponseBody
	public Map<String, Object> updateGas(@RequestBody GasUsageVO vo) {
		boolean result = adminService.updateGas(vo);
		// 서비스 결과에 따라 "success" 키와 함께 boolean 값을 JSON 응답으로 반환
		return Map.of("success", result);
	}

	// 가스 삭제
	@PostMapping("/gas/delete")
	@ResponseBody
	public Map<String, Object> deleteGas(@RequestBody GasUsageVO vo) {
		boolean result = adminService.deleteGas(vo.getUsage_cd());
		// 서비스 결과에 따라 "success" 키와 함께 boolean 값을 JSON 응답으로 반환
		return Map.of("success", result);
	}
	
}
