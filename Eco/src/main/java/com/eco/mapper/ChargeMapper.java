package com.eco.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eco.domain.UserTypeChargeDTO;

public interface ChargeMapper {
	// 이번 달 에너지 요금 합계
	public UserTypeChargeDTO getGasCharge(@Param("user_id") String userId);
	public UserTypeChargeDTO getElecCharge(@Param("user_id") String userId);
	
	// 디폴트값 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> getGasChargeDetail(@Param("user_id") String userId);
	public List<UserTypeChargeDTO> getElecChargeDetail(@Param("user_id") String userId);
	
	// 지정 기간 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> getGasChargePeriod(@Param("user_id") String userId, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
	public List<UserTypeChargeDTO> getElecChargePeriod(@Param("user_id") String userId, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
	
	// 사용자의 월별 사용량 합계
	public List<UserTypeChargeDTO> getGasChargeMonth(@Param("user_id") String userId);
	public List<UserTypeChargeDTO> getElecChargeMonth(@Param("user_id") String userId);
	
	// 간편 요금 조회
	public UserTypeChargeDTO simpleGasCharge(@Param("user_cd") int userCd, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
	public UserTypeChargeDTO simpleElecCharge(@Param("user_cd") int userCd,  @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
		
}
