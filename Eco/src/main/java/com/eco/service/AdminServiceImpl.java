package com.eco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eco.domain.ElecUsageVO;
import com.eco.domain.GasUsageVO;
import com.eco.domain.UserVO;
import com.eco.exception.ServiceException;
import com.eco.mapper.AdminMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class AdminServiceImpl implements AdminService {

	private AdminMapper mapper;

	// 사용자 검색
	@Override
	public List<UserVO> searchUsers(String user_nm) {
		try {
			return mapper.selectUsers(user_nm);
		} catch (Exception e) {
			throw new ServiceException("사용자 검색 실패", e);
		}
	}

	// 전기 사용내역 조회
	@Override
	public List<ElecUsageVO> getElecUsageByUser(int userCd) {
		try {
			return mapper.selectElecUsageByUser(userCd);
		} catch (Exception e) {
			throw new ServiceException("전기 사용내역 조회 실패", e);
		}
	}

	// 가스 사용내역 조회
	@Override
	public List<GasUsageVO> getGasUsageByUser(int userCd) {
		try {
			return mapper.selectGasUsageByUser(userCd);
		} catch (Exception e) {
			throw new ServiceException("가스 사용내역 조회 실패", e);
		}
	}

	// 가스 사용량 등록
	@Override
	public boolean insertGas(GasUsageVO vo) {
		try {
			int rows = mapper.insertGasUsage(vo);
			return rows > 0;
		} catch (Exception e) {
			throw new ServiceException("가스 사용량 등록 실패", e);
		}
	}

	// 전기 사용량 등록
	@Override
	public boolean insertElec(ElecUsageVO vo) {
		try {
			int rows = mapper.insertElecUsage(vo);
			return rows > 0;
		} catch (Exception e) {
			throw new ServiceException("전기 사용량 등록 실패", e);
		}
	}

	// 전기 수정
	@Override
	public boolean updateElec(ElecUsageVO vo) {
		try {
			int affectedRows = mapper.updateElec(vo);
			return affectedRows > 0;
		} catch (Exception e) {
			throw new ServiceException("전기 사용량 수정 실패", e);
		}
	}

	// 전기 삭제
	@Override
	public boolean deleteElec(int usage_cd) {
		try {
			int affectedRows = mapper.deleteElec(usage_cd);
			return affectedRows > 0;
		} catch (Exception e) {
			throw new ServiceException("전기 사용량 삭제 실패", e);
		}
	}

	// 가스 수정
	@Override
	public boolean updateGas(GasUsageVO vo) {
		try {
			int affectedRows = mapper.updateGas(vo);
			return affectedRows > 0;
		} catch (Exception e) {
			throw new ServiceException("가스 사용량 수정 실패", e);
		}
	}

	// 가스 삭제
	@Override
	public boolean deleteGas(int usage_cd) {
		try {
			int affectedRows = mapper.deleteGas(usage_cd);
			return affectedRows > 0;
		} catch (Exception e) {
			throw new ServiceException("가스 사용량 삭제 실패", e);
		}
	}

}
