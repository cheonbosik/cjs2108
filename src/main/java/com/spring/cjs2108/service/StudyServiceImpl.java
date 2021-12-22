package com.spring.cjs2108.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2108.dao.MemberDAO;
import com.spring.cjs2108.dao.StudyDAO;
import com.spring.cjs2108.vo.MemberVO;

@Service
public class StudyServiceImpl implements StudyService {
	@Autowired
	StudyDAO studyDAO;

	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public ArrayList<String> getCity(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("강북구");
			vos.add("강동구");
			vos.add("강서구");
			vos.add("종로구");
			vos.add("서대문구");
			vos.add("영등포구");
			vos.add("관악구");
			vos.add("성북구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("부천시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("하남시");
			vos.add("성남시");
			vos.add("안양시");
			vos.add("광명시");
			vos.add("남양주시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("영동군");
			vos.add("옥천군");
			vos.add("음성군");
			vos.add("진천군");
			vos.add("증평군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("공주시");
			vos.add("태안군");
			vos.add("보령시");
			vos.add("당진시");
			vos.add("홍성군");
			vos.add("부여시");
			vos.add("논산시");
		}
		return vos;
	}

	@Override
	public String[] getCityString(String dodo) {
		String[] strArr = new String[100];
		if(dodo.equals("서울")) {
			strArr[0] = "강남구";
			strArr[1] = "강북구";
			strArr[2] = "강동구";
			strArr[3] = "강서구";
			strArr[4] = "종로구";
			strArr[5] = "서대문구";
			strArr[6] = "영등포구";
			strArr[7] = "관악구";
			strArr[8] = "성북구";
		}
		else if(dodo.equals("경기")) {
			strArr[0] = "수원시";
			strArr[1] = "부천시";
			strArr[2] = "안성시";
			strArr[3] = "평택시";
			strArr[4] = "하남시";
			strArr[5] = "성남시";
			strArr[6] = "안양시";
			strArr[7] = "광명시";
			strArr[8] = "남양주시";
		}
		else if(dodo.equals("충북")) {
			strArr[0] = "청주시";
			strArr[1] = "충주시";
			strArr[2] = "제천시";
			strArr[3] = "단양군";
			strArr[4] = "영동군";
			strArr[5] = "옥천군";
			strArr[6] = "음성군";
			strArr[7] = "진천군";
			strArr[8] = "증평군";
		}
		else if(dodo.equals("충남")) {
			strArr[0] = "천안시";
			strArr[1] = "아산시";
			strArr[2] = "공주시";
			strArr[3] = "태안군";
			strArr[4] = "보령시";
			strArr[5] = "당진시";
			strArr[6] = "홍성군";
			strArr[7] = "부여시";
			strArr[8] = "논산시";
		}
		return strArr;
	}

	@Override
	public MemberVO getMemberVO(String mid) {
		//return studyDAO.getMemberVO(mid);
		return memberDAO.getMemberVO(mid);
	}

	@Override
	public ArrayList<MemberVO> getMemberVos(String mid) {
		return memberDAO.getMemberVos(mid);
	}
	
}
