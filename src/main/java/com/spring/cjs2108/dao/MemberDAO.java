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

	public void setMemUpdate(@Param("vo") MemberVO vo);

	public void setMemDelete(@Param("mid") String mid);

	public MemberVO getPwdConfirm(@Param("mid") String mid, @Param("toMail") String toMail);

	public void setPwdChange(@Param("mid") String mid, @Param("pwd") String pwd);

	public ArrayList<MemberVO> getIdConfirm(@Param("toMail") String toMail);

	public void setVisitUpdate(@Param("mid") String mid);

	public void setLastDateUpdate(@Param("mid") String mid, @Param("newPoint") int newPoint, @Param("todayCnt") int todayCnt);

	public int getGuestWriteCnt(@Param("mid") String mid, @Param("nickName") String nickName, @Param("name") String name);

	public int getBoardWriteCnt(@Param("mid") String mid);


}
