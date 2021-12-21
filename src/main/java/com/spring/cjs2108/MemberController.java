package com.spring.cjs2108;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.cjs2108.service.MemberService;
import com.spring.cjs2108.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	String msgFlag = "";
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value="/memLogin", method = RequestMethod.GET)
	public String memLoginGet() {
		return "member/memLogin";
	}
	
	@RequestMapping(value="/memLogin", method = RequestMethod.POST)
	public String memLoginPost(String mid, String pwd, HttpSession session) {
		MemberVO vo = memberService.getIdCheck(mid);
		
		if(vo != null) {
			String strLevel = "";
		  if(vo.getLevel() == 0) strLevel = "관리자";
		  else if(vo.getLevel() == 1) strLevel = "특별회원";
		  else if(vo.getLevel() == 2) strLevel = "우수회원";
		  else if(vo.getLevel() == 3) strLevel = "정회원";
		  else if(vo.getLevel() == 4) strLevel = "준회원";
			
			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("sStrLevel", strLevel);
			
			// 쿠키 처리
			
			msgFlag = "memLoginOk";
		}
		else {
			msgFlag = "memLoginNo";
		}
		return "redirect:/msg/" + msgFlag;
	}
	
	@RequestMapping(value="/memMain", method = RequestMethod.GET)
	public String memMainGet() {
		
		return "member/memMain";
	}
	
	@RequestMapping(value="/memLogout", method = RequestMethod.GET)
	public String memLogoutGet() {
		msgFlag = "memLogout";
		
		return "redirect:/msg/" + msgFlag;
	}
}
