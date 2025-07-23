package com.eco.service;

import com.eco.domain.UserVO;

public interface UserService {
	public void signup(UserVO user);
	
	// 로그인 기능 구현
	public UserVO login(UserVO user);
	
	// 사용자 아이디 존재여부 확인
	public UserVO findByUserId(String user_id, String user_type);
	
	// 회원 정보 수정
	public void userModify(UserVO user);
	public UserVO findByUserCD(int user_cd);
	
	// 회원 탈퇴
	public void userDelete(int user_cd);
	
	// 간편 요금 조회에서 사용자 검색
	public UserVO findByUserCdUserNm(int user_cd, String user_nm);
	
}
	