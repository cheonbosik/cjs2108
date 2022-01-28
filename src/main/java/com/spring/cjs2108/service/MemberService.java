package com.spring.cjs2108.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.vo.MemberVO;

public interface MemberService {
	public MemberVO getMemberVO(String mid);
	
	public MemberVO getIdCheck(String mid);

	public MemberVO getNickNameCheck(String nickName);
	
	public void getMemberTodayProcess(int todayCnt);

	public int setMemInput(MultipartFile fName, MemberVO vo);

	public int setMemUpdate(MultipartFile fName, MemberVO vo);

	public void setMemDelete(String mid);

	public MemberVO getPwdConfirm(String mid, String toMail);

	public void setPwdChange(String mid, String pwd);

	public ArrayList<MemberVO> getIdConfirm(String toMail);

	public int getGuestWriteCnt(String mid, String nickName, String name);

	public int getBoardWriteCnt(String mid);

	public int getPdsWriteCnt(String mid);

}
