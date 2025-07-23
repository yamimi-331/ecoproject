package com.eco.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eco.domain.UserTypeChargeDTO;

public interface ChargeService {
	// 이번 달 가스요금
	public UserTypeChargeDTO readGasCharge(String userId);
	// 이번 달 전기요금
	public UserTypeChargeDTO readElecCharge(String userId);
	
	// 디폴트값 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> gasChargeDetail(String userId);
	public List<UserTypeChargeDTO> elecChargeDetail(String userId);
	
	// 지정 기간 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> gasChargePeriod(String userId, LocalDate startDate, LocalDate endDate);
	public List<UserTypeChargeDTO> elecChargePeriod(String userId, LocalDate startDate, LocalDate endDate);

	// 사용자의 월별 사용량 합계
	public List<UserTypeChargeDTO> getGasChargeMonth(String userId);
	public List<UserTypeChargeDTO> getElecChargeMonth(String userId);
	
	// 간편 요금 조회
	public UserTypeChargeDTO simpleGasCharge(@Param("user_cd") int userCd, LocalDate startDate, LocalDate endDate);
	public UserTypeChargeDTO simpleElecCharge(@Param("user_cd") int userCd, LocalDate startDate, LocalDate endDate);

}
