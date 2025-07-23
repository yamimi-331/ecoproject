package com.eco.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eco.domain.ElecUsageVO;
import com.eco.domain.GasUsageVO;
import com.eco.domain.UserVO;

public interface AdminMapper {

	public List<UserVO> selectUsers(@Param("user_nm") String user_nm);

	public List<ElecUsageVO> selectElecUsageByUser(@Param("user_cd") int user_cd);

	public List<GasUsageVO> selectGasUsageByUser(@Param("user_cd") int user_cd);

	public int insertGasUsage(GasUsageVO vo);

	public int insertElecUsage(ElecUsageVO vo);

	public int updateElec(ElecUsageVO vo);

	public int deleteElec(int usage_cd);

	public int updateGas(GasUsageVO vo);

	public int deleteGas(int usage_cd);

}
