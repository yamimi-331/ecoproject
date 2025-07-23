package com.eco.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eco.domain.ElecUsageVO;
import com.eco.domain.ElecVO;
import com.eco.domain.GasUsageVO;
import com.eco.domain.GasVO;
import com.eco.domain.UserTypeChargeDTO;

public interface UsageMapper {
	// 이번 달 에너지 사용량 합계
	public UserTypeChargeDTO getGasUsage(@Param("user_id") String userId);
	public UserTypeChargeDTO getElecUsage(@Param("user_id") String userId);

	// 디폴트값 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> getGasUsageDetail(@Param("user_id") String userId);
	public List<UserTypeChargeDTO> getElecUsageDetail(@Param("user_id") String userId);

	// 지정 기간 에너지 사용 상세 내역
	public List<UserTypeChargeDTO> getGasUsagePeriod(@Param("user_id") String userId, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
	public List<UserTypeChargeDTO> getElecUsagePeriod(@Param("user_id") String userId, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);

	// 지역별 가장 최근 달의 에너지 사용량 합계
	public List<UserTypeChargeDTO> getUsageLocalAmount();
	
	// 사용자의 월별 사용량 합계
	public List<UserTypeChargeDTO> getGasUsageMonth(@Param("user_id") String userId);
	public List<UserTypeChargeDTO> getElecUsageMonth(@Param("user_id") String userId);
	
	// 사용자의 가스 사용량 등록
	public void insertGasUsage(GasUsageVO gasUsage);
	// 사용자의 전기 사용량 등록
	public void insertElecUsage(ElecUsageVO elecUsage);
	
	// T_GAS 타입 조회용
	public List<GasVO> getAllGasTypes(); 
	// T_ELEC 타입 조회용
	public List<ElecVO> getAllElecTypes(); 
}
