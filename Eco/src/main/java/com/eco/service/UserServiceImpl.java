package com.eco.service;

import org.springframework.stereotype.Service;

import com.eco.domain.UserVO;
import com.eco.exception.ServiceException;
import com.eco.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class UserServiceImpl implements UserService {

	private UserMapper mapper;

	// 회원가입 
	@Override
	public void signup(UserVO user) {
		try {
			mapper.userInsert(user);
		} catch (Exception e) {
			throw new ServiceException("회원가입 실패", e);
		}
	}

	// 로그인
	@Override
	public UserVO login(UserVO user) {
		try {
			return mapper.getUserSelect(user);
		} catch (Exception e) {
			throw new ServiceException("로그인 실패", e);
		}
	}

	// 사용자 ID, Type으로 사용자 정보 반환
	@Override
	public UserVO findByUserId(String user_id, String user_type) {
		try {
			return mapper.findByUserId(user_id, user_type);
		} catch (Exception e) {
			throw new ServiceException("사용자 조회 실패", e);
		}
	}

	// 회원 정보 수정
	@Override
	public void userModify(UserVO user) {
		try {
			mapper.userUpdate(user);
		} catch (Exception e) {
			throw new ServiceException("회원 정보 수정 실패", e);
		}
	}
	
	// 사용자 CD값으로 사용자 정보 반환
	@Override
	public UserVO findByUserCD(int user_cd) {
		try {
			return mapper.findByUserCD(user_cd);
		} catch (Exception e) {
			throw new ServiceException("사용자 조회 실패", e);
		}
	}

	// 회원 탈퇴
	@Override
	public void userDelete(int user_cd) {
		try {
			mapper.userDelete(user_cd);
		} catch (Exception e) {
			throw new ServiceException("회원 탈퇴 실패", e);
		}
	}

	// 간편 요금 조회에서 사용자 검색
	@Override
	public UserVO findByUserCdUserNm(int user_cd, String user_nm) {
		try {
			return mapper.findByUserCdUserNm(user_cd, user_nm);
		} catch (Exception e) {
			throw new ServiceException("간편 요금 조회 실패", e);
		}
	}
}
