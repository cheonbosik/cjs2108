package com.spring.cjs2108.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.MemberVO;

public interface MemberDAO {
	public MemberVO getMemberVO(@Param("mid") String mid);	// member테이블을 mid로 검색
	
	public ArrayList<MemberVO> getMemberVos(String mid);		// member테이블을 mid로 검색
	
	public MemberVO getIdCheck(@Param("mid") String mid);		// member2테이블을 mid로 검색

	public MemberVO getNickNameCheck(@Param("nickName") String nickName);

	public void setMemInput(@Param("vo") MemberVO vo);


}
