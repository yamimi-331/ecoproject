package com.eco.controller;

import java.time.LocalDate; 
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eco.domain.UserTypeChargeDTO;
import com.eco.domain.UserVO;
import com.eco.service.UsageService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/usage")
public class UsageContoller {

	private UsageService service;

	// 이번 달 나의 에너지 사용량 가져오기
	@GetMapping("")
	public String usageSelect(Model model, HttpSession session) {
		// session의 유저 정보 가져오기
		UserVO user = (UserVO) session.getAttribute("currentUserInfo");
		model.addAttribute("userId", user.getUser_id());
		model.addAttribute("userName", user.getUser_nm());
		model.addAttribute("IsAdmin", user.getAdmin_yn());
		
		// 이번 달 에너지 사용량 합계
		UserTypeChargeDTO gasTotal = service.readGasusage(user.getUser_id());
		UserTypeChargeDTO elecTotal = service.readElecusage(user.getUser_id());
		model.addAttribute("gasUsage", gasTotal);
		model.addAttribute("elecUsage", elecTotal);
		// 이번 달 사용량이 0일 때 처리
		if (gasTotal == null) {
			model.addAttribute("gasUsageMsg", "이번 달 가스 사용량이 없습니다.");
		}
		if (elecTotal == null) {
			model.addAttribute("elecUsageMsg", "이번 달 전기 사용량이 없습니다.");
		}

		// 디폴트값 가스 상세 내역
		List<UserTypeChargeDTO> gasUse = service.gasUsageDetail(user.getUser_id());
		model.addAttribute("gasUse", gasUse);
		// 디폴트값 전기 상세 내역
		List<UserTypeChargeDTO> elecUse = service.elecUsageDetail(user.getUser_id());
		model.addAttribute("elecUse", elecUse);
		// 사용량이 0일 때 처리
		if (gasUse.isEmpty()) {
			model.addAttribute("gasUsageDetailMsg", "이번 달 가스 사용량이 없습니다.");
		}
		if (elecUse.isEmpty()) {
			model.addAttribute("elecUsageDetailMsg", "이번 달 전기 사용량이 없습니다.");
		}
		
		
		// 월별 사용량 합계 그래프 자료
		List<UserTypeChargeDTO> gasMonth = service.getGasUsageMonth(user.getUser_id());
		List<UserTypeChargeDTO> elecMonth = service.getElecUsageMonth(user.getUser_id());
		Gson gson = new Gson();
		
		Map<String, UserTypeChargeDTO> monthMap = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth current = YearMonth.now();
		
		// 12개월 미리 초기화
		for(int i=11; i>=0; i--) {
			YearMonth ym = current.minusMonths(i);
			String key = ym.format(formatter);
			
			UserTypeChargeDTO dto = new UserTypeChargeDTO();
			dto.setYear(ym.getYear());
			dto.setMonth(ym.getMonthValue());
			dto.setGasUsageAmount(0);
			dto.setElecUsageAmount(0);
			
			monthMap.put(key, dto);
		}
		
		// 사용량 덮어쓰기
		for (UserTypeChargeDTO dto : gasMonth) {
			String yearMonth = dto.getYear() + "-" + String.format("%02d", dto.getMonth());
		    UserTypeChargeDTO target = monthMap.get(yearMonth);
		    if(target != null) {
		    	target.setGasUsageAmount(dto.getGasUsageAmount());
		    }
		}
		for (UserTypeChargeDTO dto : elecMonth) {
		    String yearMonth = dto.getYear() + "-" + String.format("%02d", dto.getMonth());
		    UserTypeChargeDTO target = monthMap.get(yearMonth);
		    if(target != null) {
		    	target.setElecUsageAmount(dto.getElecUsageAmount());
		    }
		}
		
		JsonArray jArray = new JsonArray();

		for(int i=11; i>=0 ;i--) {
			YearMonth ym = current.minusMonths(i);
		    String key = ym.format(formatter);
		    
		    UserTypeChargeDTO monthDTO = monthMap.get(key);
		    JsonObject object = new JsonObject();
		    object.addProperty("user_month", key);
			
			if(monthDTO != null) {
				object.addProperty("gasUsageMonth", (int) monthDTO.getGasUsageAmount());
				object.addProperty("elecUsageMonth", (int) monthDTO.getElecUsageAmount());
			} else {
				object.addProperty("gasUsageMonth", 0);
				object.addProperty("elecUsageMonth", 0);
			}
			
			jArray.add(object);
		}
		String json = gson.toJson(jArray);
		model.addAttribute("json",json);

		return "usage";
	}

	// 지정한 기간 나의 에너지 사용량 가져오기
	@GetMapping("/period")
	public String usageSelectPeriod(Model model, HttpSession session,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		// session의 유저 정보 가져오기
		UserVO user = (UserVO) session.getAttribute("currentUserInfo");
		String userID = user.getUser_id();
		model.addAttribute("userId", user.getUser_id());
		model.addAttribute("userName", user.getUser_nm());

		// 이번 달 에너지 사용량 합계
		UserTypeChargeDTO gasTotal = service.readGasusage(userID);
		UserTypeChargeDTO elecTotal = service.readElecusage(userID);
		model.addAttribute("gasUsage", gasTotal);
		model.addAttribute("elecUsage", elecTotal);
		// 이번 달 사용량이 0일 때 처리
		if (gasTotal == null) {
			model.addAttribute("gasUsageMsg", "이번 달 가스 사용량이 없습니다.");
		}
		if (elecTotal == null) {
			model.addAttribute("elecUsageMsg", "이번 달 전기 사용량이 없습니다.");
		}

		log.info("지정 기간 나의 사용량 가져오기");
		// 지정 기간 가스 상세 내역
		List<UserTypeChargeDTO> gasUse = service.gasUsagePeriod(userID, startDate, endDate);
		model.addAttribute("gasUse", gasUse);
		// 지정 기간 전기 상세 내역
		List<UserTypeChargeDTO> elecUse = service.elecUsagePeriod(userID, startDate, endDate);
		model.addAttribute("elecUse", elecUse);
		// 지정 기간 사용량이 0일 때 처리
		if (gasUse.isEmpty()) {
			model.addAttribute("gasUsageDetailMsg", "해당 기간 가스 사용량이 없습니다.");
		}
		if (elecUse.isEmpty()) {
			model.addAttribute("elecUsageDetailMsg", "해당 기간 전기 사용량이 없습니다.");
		}

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		
		//월별 사용량 합계 그래프 자료
		List<UserTypeChargeDTO> gasMonth = service.getGasUsageMonth(user.getUser_id());
		List<UserTypeChargeDTO> elecMonth = service.getElecUsageMonth(user.getUser_id());
		Gson gson = new Gson();
		
		Map<String, UserTypeChargeDTO> monthMap = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth current = YearMonth.now();
		
		// 12개월 미리 초기화
		for(int i=11; i>=0; i--) {
			YearMonth ym = current.minusMonths(i);
			String key = ym.format(formatter);
			
			UserTypeChargeDTO dto = new UserTypeChargeDTO();
			dto.setYear(ym.getYear());
			dto.setMonth(ym.getMonthValue());
			dto.setGasUsageAmount(0);
			dto.setElecUsageAmount(0);
			
			monthMap.put(key, dto);
		}
		
		// 사용량 덮어쓰기
		for (UserTypeChargeDTO dto : gasMonth) {
			String yearMonth = dto.getYear() + "-" + String.format("%02d", dto.getMonth());
		    UserTypeChargeDTO target = monthMap.get(yearMonth);
		    if(target != null) {
		    	target.setGasUsageAmount(dto.getGasUsageAmount());
		    }
		}
		for (UserTypeChargeDTO dto : elecMonth) {
		    String yearMonth = dto.getYear() + "-" + String.format("%02d", dto.getMonth());
		    UserTypeChargeDTO target = monthMap.get(yearMonth);
		    if(target != null) {
		    	target.setElecUsageAmount(dto.getElecUsageAmount());
		    }
		}
		
		JsonArray jArray = new JsonArray();

		for(int i=11; i>=0 ;i--) {
			YearMonth ym = current.minusMonths(i);
		    String key = ym.format(formatter);
		    
		    UserTypeChargeDTO monthDTO = monthMap.get(key);
		    JsonObject object = new JsonObject();
		    object.addProperty("user_month", key);
			
			if(monthDTO != null) {
				object.addProperty("gasUsageMonth", (int) monthDTO.getGasUsageAmount());
				object.addProperty("elecUsageMonth", (int) monthDTO.getElecUsageAmount());
			} else {
				object.addProperty("gasUsageMonth", 0);
				object.addProperty("elecUsageMonth", 0);
			}
			
			jArray.add(object);
		}
		String json = gson.toJson(jArray);
		model.addAttribute("json",json);

		return "usage";
	}

	//가스/전기 타입 한글명 조회후 model 로 usageForm페이지로 넘겨주기
	@GetMapping("/insert-form")
	public String insertForm(Model model) {
		model.addAttribute("gasList", service.getAllGasTypes());
		model.addAttribute("elecList", service.getAllElecTypes());
		return "usageForm"; 
	}

}
