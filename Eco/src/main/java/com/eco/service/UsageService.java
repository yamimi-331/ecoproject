package com.eco.service;

import java.time.LocalDate; 
import java.util.List;

import com.eco.domain.ElecVO;
import com.eco.domain.GasVO;
import com.eco.domain.UserTypeChargeDTO;

public interface UsageService {
	// 이번 달 에너지 사용량 합계
	public UserTypeChargeDTO readGasusage(String userId);
	public UserTypeChargeDTO readElecusage(String userId);
	
	// 디폴트값 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> gasUsageDetail(String userId);
	public List<UserTypeChargeDTO> elecUsageDetail(String userId);
	
	// 지정 기간 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> gasUsagePeriod(String userId, LocalDate startDate, LocalDate endDate);
	public List<UserTypeChargeDTO> elecUsagePeriod(String userId, LocalDate startDate, LocalDate endDate);

	// 지역별 가장 최근 달의 에너지 사용량 합계
	public List<UserTypeChargeDTO> usageAmount();
	
	// 사용자의 월별 사용량 합계
	public List<UserTypeChargeDTO> getGasUsageMonth(String userId);
	public List<UserTypeChargeDTO> getElecUsageMonth(String userId);
	
	// 가스 타입 조회
	public List<GasVO> getAllGasTypes(); 
	// 전기 타입 조회
	public List<ElecVO> getAllElecTypes(); 
	
}
