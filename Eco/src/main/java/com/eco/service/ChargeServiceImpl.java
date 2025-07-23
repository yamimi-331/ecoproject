package com.eco.service;

import java.time.LocalDate; 
import java.util.List; 

import org.springframework.stereotype.Service;

import com.eco.domain.UserTypeChargeDTO;
import com.eco.exception.ServiceException;
import com.eco.mapper.ChargeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChargeServiceImpl implements ChargeService{
	private ChargeMapper mapper;
	
	// 이번 달 에너지 요금 합계
	@Override
	public UserTypeChargeDTO readGasCharge(String userId) {
		try {
			return mapper.getGasCharge(userId);
		} catch(Exception e) {
			throw new ServiceException("당월 가스 요금 합계 조회 실패", e);
		}
	}
	@Override
	public UserTypeChargeDTO readElecCharge(String userId) {
		try {
			return mapper.getElecCharge(userId);
		} catch(Exception e) {
			throw new ServiceException("당월 전기 요금 합계 조회 실패", e);
		}
	}
	
	// 디폴트값 에너지 사용 상세 내역
	@Override
	public List<UserTypeChargeDTO> gasChargeDetail(String userId) {
		try {
		 return mapper.getGasChargeDetail(userId);
		} catch(Exception e) {
			throw new ServiceException("당월 가스 요금 상세 내역 조회 실패", e);
		}
	}
	@Override
	public List<UserTypeChargeDTO> elecChargeDetail(String userId) {
		try {
			return mapper.getElecChargeDetail(userId);
		} catch(Exception e) {
			throw new ServiceException("당월 전기 요금 상세 내역 조회 실패", e);
		}
	}
	
	// 지정 기간 에너지 사용 상세 내역
	@Override
	public List<UserTypeChargeDTO> gasChargePeriod(String userId, LocalDate startDate, LocalDate endDate) {
		try {
			return mapper.getGasChargePeriod(userId, startDate, endDate);
		} catch(Exception e) {
			throw new ServiceException("지정 기간 가스 요금 내역 조회 실패", e);
		}
	}
	@Override
	public List<UserTypeChargeDTO> elecChargePeriod(String userId, LocalDate startDate, LocalDate endDate) {
		try {
			return mapper.getElecChargePeriod(userId, startDate, endDate);
		} catch(Exception e) {
			throw new ServiceException("지정 기간 전기 요금 내역 조회 실패", e);
		}
	}
	
	// 사용자의 월별 사용량 합계
	@Override
	public List<UserTypeChargeDTO> getGasChargeMonth(String userId) {
		try {
			return mapper.getGasChargeMonth(userId);
		} catch(Exception e) {
			throw new ServiceException("사용자의 월별 가스 요금 합계 조회 실패", e);
		}
	}
	@Override
	public List<UserTypeChargeDTO> getElecChargeMonth(String userId) {
		try {
			return mapper.getElecChargeMonth(userId);
		} catch(Exception e) {
			throw new ServiceException("사용자의 월별 전기 요금 합계 조회 실패", e);
		}
	}
	
	// 간편 요금 조회
	@Override
	public UserTypeChargeDTO simpleGasCharge(int userCd, LocalDate startDate, LocalDate endDate) {
		try {
			return mapper.simpleGasCharge(userCd, startDate, endDate);
		} catch(Exception e) {
			throw new ServiceException("가스 사용시 간편 요금 조회 실패", e);
		}
	}
	@Override
	public UserTypeChargeDTO simpleElecCharge(int userCd, LocalDate startDate, LocalDate endDate) {
		try {
			return mapper.simpleElecCharge(userCd, startDate, endDate);
		} catch(Exception e) {
			throw new ServiceException("전기 사용시 간편 요금 조회 실패", e);
		}
	}

}
