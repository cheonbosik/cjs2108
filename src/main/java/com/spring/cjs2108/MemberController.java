package com.spring.cjs2108;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.service.MemberService;
import com.spring.cjs2108.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	String msgFlag = "";
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	// 로그인폼 호출
	@RequestMapping(value="/memLogin", method = RequestMethod.GET)
	public String memLoginGet() {
		return "member/memLogin";
	}
	
	// 로그인 인증처리
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
	
	// 로그인 성공후 만나는 회원메인창보기
	@RequestMapping(value="/memMain", method = RequestMethod.GET)
	public String memMainGet() {
		return "member/memMain";
	}

	// 로그아웃처리
	@RequestMapping(value="/memLogout", method = RequestMethod.GET)
	public String memLogoutGet() {
		msgFlag = "memLogout";
		return "redirect:/msg/" + msgFlag;
	}
	
	// 회원 가입폼
	@RequestMapping(value="/memInput", method = RequestMethod.GET)
	public String memInputGet() {
		return "member/memInput";
	}
	
	// 회원 가입처리하기
	@RequestMapping(value="/memInput", method = RequestMethod.POST)
	public String memInputPost(MultipartFile fName, MemberVO vo) {
		// 아이디 중복체크
		if(memberService.getIdCheck(vo.getMid()) != null) {
			msgFlag = "memIdCheckNo";
			return "redirect:/msg/" + msgFlag;
		}
		// 닉네임 중복체크
		if(memberService.getNickNameCheck(vo.getNickName()) != null) {
			msgFlag = "memNickNameCheckNo";
			return "redirect:/msg/" + msgFlag;
		}
		
		// 비밀번호 암호화처리
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// DB에 가입회원 등록하기
		int res = memberService.setMemInput(fName, vo);
		
		if(res == 1) msgFlag = "memInputOk";
		else msgFlag = "memInputNo";
		
		return "redirect:/msg/" + msgFlag;
	}
	
	// 회원 아이디 검색하기
	@ResponseBody
	@RequestMapping(value="/idCheck", method = RequestMethod.POST)
	public String idCheckPost(String mid) {
		String res = "0";
		MemberVO vo = memberService.getIdCheck(mid);
		if(vo != null) res = "1";
		return res;
	}
	
	// 회원 닉네임 검색하기
	@ResponseBody
	@RequestMapping(value="/nickNameCheck", method = RequestMethod.POST)
	public String nickNameCheckPost(String nickName) {
		String res = "0";
		MemberVO vo = memberService.getNickNameCheck(nickName);
		if(vo != null) res = "1";
		return res;
	}
}
