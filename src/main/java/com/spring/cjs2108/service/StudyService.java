package com.spring.cjs2108.service;

import java.util.ArrayList;

import com.spring.cjs2108.vo.MemberVO;

public interface StudyService {

	public ArrayList<String> getCity(String dodo);

	public String[] getCityString(String dodo);

	public MemberVO getMemberVO(String mid);

	public ArrayList<MemberVO> getMemberVos(String mid);

}
