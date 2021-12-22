package com.spring.cjs2108.service;

import com.spring.cjs2108.vo.MemberVO;

public interface MemberService {
	public MemberVO getMemberVO(String mid);
	
	public MemberVO getIdCheck(String mid);

}
