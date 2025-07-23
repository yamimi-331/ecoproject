package com.eco.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eco.domain.UserTypeChargeDTO;
import com.eco.service.UsageService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class IndexController {
	
	private UsageService usageService;
	
	@GetMapping("/")
	public String Index(Model model) {
		// 지역별 가장 최근 달의 에너지 사용량 합계
		List<UserTypeChargeDTO> usageAmount = usageService.usageAmount();
		Gson gson = new Gson();
		JsonArray jArray = new JsonArray();
		Iterator<UserTypeChargeDTO> amount = usageAmount.iterator();
		while(amount.hasNext()){
			UserTypeChargeDTO amountDTO = amount.next();
			JsonObject object = new JsonObject();
			String user_local = amountDTO.getUser_local();
			int gasUsageAmount = (int) amountDTO.getGasUsageAmount();
			int elecUsageAmount = (int) amountDTO.getElecUsageAmount();
			
			object.addProperty("user_local", user_local);
			object.addProperty("gasUsageAmount", gasUsageAmount);
			object.addProperty("elecUsageAmount", elecUsageAmount);
			
			jArray.add(object);
		}
		String json = gson.toJson(jArray);
		model.addAttribute("json", json);
		
		return "index";
	}
	
	
}
