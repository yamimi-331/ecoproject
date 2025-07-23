package com.eco.service;

import java.util.List;

import com.eco.domain.ElecUsageVO;
import com.eco.domain.GasUsageVO;
import com.eco.domain.UserVO;

public interface AdminService {
	// 사용자 검색
	public List<UserVO> searchUsers(String user_nm);

	// 전기 사용내역 조회
	public List<ElecUsageVO> getElecUsageByUser(int userCd);
	// 가스 사용내역 조회
	public List<GasUsageVO> getGasUsageByUser(int userCd);
	// 가스 사용량 등록
	public boolean insertGas(GasUsageVO vo);
	// 전기 사용량 등록
	public boolean insertElec(ElecUsageVO vo);
	// 전기 수정
	public boolean updateElec(ElecUsageVO vo);
	// 전기 삭제
	public boolean deleteElec(int usage_cd);
	// 가스 수정
	public boolean updateGas(GasUsageVO vo);
	// 가스 삭제
	public boolean deleteGas(int usage_cd);


}
