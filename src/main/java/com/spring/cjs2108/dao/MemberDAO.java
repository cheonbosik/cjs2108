package com.spring.cjs2108.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getIdCheck(@Param("mid") String mid);

}
