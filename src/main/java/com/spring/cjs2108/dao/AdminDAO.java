package com.spring.cjs2108.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.MemberVO;

public interface AdminDAO {

	public int totRecCnt(@Param("level") int level);
	
	public int totRecCntMid(@Param("mid") String mid);

	public ArrayList<MemberVO> getMemberList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("level") int level);
	
	public ArrayList<MemberVO> getMemberListMid(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("mid") String mid);

	public void setLevelUpdate(@Param("idx") int idx, @Param("level") int level);

	public int getNewMember();

}
