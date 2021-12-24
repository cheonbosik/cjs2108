package com.spring.cjs2108.service;

import java.util.ArrayList;

import com.spring.cjs2108.vo.MemberVO;

public interface AdminService {

	public int totRecCnt(int level);
	
	public int totRecCntMid(String mid);

	public ArrayList<MemberVO> getMemberList(int startIndexNo, int pageSize, int level);
	
	public ArrayList<MemberVO> getMemberListMid(int startIndexNo, int pageSize, String mid);

	public void setLevelUpdate(int idx, int level);

	public int getNewMember();

}
