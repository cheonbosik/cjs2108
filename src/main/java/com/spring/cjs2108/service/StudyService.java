package com.spring.cjs2108.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.vo.Goods1VO;
import com.spring.cjs2108.vo.Goods2VO;
import com.spring.cjs2108.vo.Goods3VO;
import com.spring.cjs2108.vo.MemberVO;

public interface StudyService {

	public ArrayList<String> getCity(String dodo);

	public String[] getCityString(String dodo);

	public MemberVO getMemberVO(String mid);

	public ArrayList<MemberVO> getMemberVos(String mid);

	public int fileUpload(MultipartFile fName);

	public List<Goods1VO> getProduct1();

	public ArrayList<Goods2VO> getProduct2(String product1);

	public ArrayList<Goods3VO> getProduct3(String product1, String product2);

}
