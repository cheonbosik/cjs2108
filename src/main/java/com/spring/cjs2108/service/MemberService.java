package com.spring.cjs2108.service;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.vo.MemberVO;

public interface MemberService {
	public MemberVO getMemberVO(String mid);
	
	public MemberVO getIdCheck(String mid);

	public MemberVO getNickNameCheck(String nickName);

	public int setMemInput(MultipartFile fName, MemberVO vo);

	public int setMemUpdate(MultipartFile fName, MemberVO vo);

	public void setMemDelete(String mid);

}
