package com.spring.cjs2108;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.cjs2108.service.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {
	String msgFlag = "";
	
	@Autowired
	StudyService studyService;
	
	@RequestMapping("/ajax/ajaxMenu")
	public String ajaxMenuGet() {
		return "study/ajax/ajaxMenu";
	}
	
	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.GET)
	public String ajaxTest1Get() {
		return "study/ajax/ajaxTest1";
	}
	
	@RequestMapping(value="/ajax/ajaxTest2", method = RequestMethod.GET)
	public String ajaxTest2Get() {
		return "study/ajax/ajaxTest2";
	}
	
	@RequestMapping(value="/ajax/ajaxTest3", method = RequestMethod.GET)
	public String ajaxTest3Get() {
		return "study/ajax/ajaxTest3";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest1Post(String dodo) {
		//System.out.println("dodo : " + dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
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
		System.out.println("vos : " + vos);
		
		map.put("city", vos);
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest2Post(String dodo) {
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
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest3", method = RequestMethod.POST)
	public String[] ajaxTest3Post(String dodo) {
		String[] str = new String[100];
		
		if(dodo.equals("서울")) {
			str[0] = "강남구";
			str[1] = "강북구";
			str[2] = "강동구";
			str[3] = "강서구";
			str[4] = "종로구";
			str[5] = "서대문구";
			str[6] = "영등포구";
			str[7] = "관악구";
			str[8] = "성북구";
		}
		else if(dodo.equals("경기")) {
			str[0] = "수원시";
			str[1] = "부천시";
			str[2] = "안성시";
			str[3] = "평택시";
			str[4] = "하남시";
			str[5] = "성남시";
			str[6] = "안양시";
			str[7] = "광명시";
			str[8] = "남양주시";
		}
		else if(dodo.equals("충북")) {
			str[0] = "청주시";
			str[1] = "충주시";
			str[2] = "제천시";
			str[3] = "단양군";
			str[4] = "영동군";
			str[5] = "옥천군";
			str[6] = "음성군";
			str[7] = "진천군";
			str[8] = "증평군";
		}
		else if(dodo.equals("충남")) {
			str[0] = "천안시";
			str[1] = "아산시";
			str[2] = "공주시";
			str[3] = "태안군";
			str[4] = "보령시";
			str[5] = "당진시";
			str[6] = "홍성군";
			str[7] = "부여시";
			str[8] = "논산시";
		}
		return str;
	}
}
