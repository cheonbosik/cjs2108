package com.spring.cjs2108;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String memLoginGet(HttpServletRequest request) {
		// 로그인폼 호출시 기존에 저장된 쿠키가 있으면 불러올수 있게 한다.
		Cookie[] cookies = request.getCookies();	// 기존에 저장된 현재 사이트의 쿠키를 불러와서 배열로 저장한다.
		String mid = "";
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("cMid")) {
				mid = cookies[i].getValue();
				request.setAttribute("mid", mid);
				break;
			}
		}
		
		return "member/memLogin";
	}
	
	// 로그인 인증처리
	@RequestMapping(value="/memLogin", method = RequestMethod.POST)
	public String memLoginPost(String mid, String pwd, HttpSession session, HttpServletResponse response, HttpServletRequest request, Model model) {
		MemberVO vo = memberService.getIdCheck(mid);
		
		if(vo != null && passwordEncoder.matches(pwd, vo.getPwd()) && vo.getUserDel().equals("NO")) {
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
			
		  // 최종 접속일을 세션에 저장시켜준다.
			session.setAttribute("sLastDate", vo.getLastDate().substring(0, vo.getLastDate().lastIndexOf(" ")));
			
			// 방문시에 처리할 내용들을 서비스객체에서 처리시킨다.(오늘방문카운트누적, 포인트누적, 등등등~~)
			memberService.getMemberTodayProcess(vo.getTodayCnt());
			
			// 아이디에 대한 정보를 쿠키로 저장처리...
			String idCheck = request.getParameter("idCheck")==null? "" : request.getParameter("idCheck");
			// 쿠키 처리(아이디에 대한 정보를 쿠키로 저장할지를 처리한다)-jsp에서 idCheck변수에 값이 체크되어서 넘어오면 'on'값이 담겨서 넘어오게 된다.
			if(idCheck.equals("on")) {				// 앞의 jsp에서 쿠키를 저장하겠다고 넘겼을경우...
				Cookie cookie = new Cookie("cMid", mid);
				cookie.setMaxAge(60*60*24*4); 	// 쿠키의 만료시간을 4일로 정했다.(단위: 초)
				response.addCookie(cookie);
			}
			else {		// jsp에서 쿠키저장을 취소해서 보낸다면? 쿠키명을 삭제처리한다.
				Cookie[] cookies = request.getCookies();	// 기존에 저장되어 있는 현재 사이트의 쿠키를 불러와서 배열로 저장한다.
				for(int i=0; i<cookies.length; i++) {
					if(cookies[i].getName().equals("cMid")) {
						cookies[i].setMaxAge(0);		//  저장된 쿠키명중 'cMid' 쿠키를 찾아서 삭제한다.
						response.addCookie(cookies[i]);
						break;
					}
				}
			}
			msgFlag = "memLoginOk";
		}
		else {
			msgFlag = "memLoginNo";
		}
		return "redirect:/msg/" + msgFlag;
	}
	
	// 로그인 성공후 만나는 회원메인창보기
	@RequestMapping(value="/memMain", method = RequestMethod.GET)
	public String memMainGet(HttpSession session, Model model) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.getIdCheck(mid);
		model.addAttribute("vo", vo);
		
		// 방명록에 올린 글수 가져오기
		int guestCnt = memberService.getGuestWriteCnt(mid, vo.getNickName(), vo.getName());
		model.addAttribute("guestCnt", guestCnt);
		
		// 게시판에 올린 글수 가져오기
		int boardCnt = memberService.getBoardWriteCnt(mid);
		model.addAttribute("boardCnt", boardCnt);
		
		// 자료실에 올린 글수 가져오기
		int pdsCnt = memberService.getPdsWriteCnt(mid);
		model.addAttribute("pdsCnt", pdsCnt);
		
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
	
	@RequestMapping(value="/memPwdCheck", method = RequestMethod.GET)
	public String memPwdCheckGet() {
		return "member/memPwdCheck";
	}
	
	@RequestMapping(value="/memPwdCheck", method = RequestMethod.POST)
	public String memPwdCheckPost(String pwd, HttpSession session, Model model) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.getIdCheck(mid);
		if(vo != null && passwordEncoder.matches(pwd, vo.getPwd())) {
			session.setAttribute("sPwd", pwd);
			model.addAttribute("vo", vo);
			return "member/memUpdate";
		}
		else {
			msgFlag = "pwdCheckNo";
			return "redirect:/msg/" + msgFlag;
		}
	}
	
	// 회원정보변경폼 불러오기
	@RequestMapping(value="/memUpdate", method = RequestMethod.GET)
	public String memUpdateGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.getIdCheck(mid);
		model.addAttribute("vo", vo);
		return "member/memUpdate";
	}
	
	// 회원 정보 변경하기
	@RequestMapping(value="/memUpdate", method = RequestMethod.POST)
	public String memUpdatePost(MultipartFile fName, MemberVO vo, HttpSession session) {
		String nickName = (String) session.getAttribute("sNickName");
		
		// 닉네임 중복체크하기(닉네임이 변경되었으면 새롭게 닉네임을 세션에 등록시켜준다.)
		if(!nickName.equals(vo.getNickName())) {
			if(memberService.getNickNameCheck(vo.getNickName()) != null) {
				msgFlag = "memNickNameCheckNo";
				return "redirect:/msg/" + msgFlag;
			}
			else {
				session.setAttribute("sNickName", vo.getNickName());
			}
		}
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		int res = memberService.setMemUpdate(fName, vo);
		
		if(res == 1) {
			msgFlag = "memUpdateOk";
		}
		else {
			msgFlag = "memUpdateNo";
		}
		return "redirect:/msg/" + msgFlag;
	}
	
	@RequestMapping(value="/memDelete")
	public String memDeleteGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		memberService.setMemDelete(mid);
		msgFlag = "memDeleteOk";
		return "redirect:/msg/" + msgFlag;
	}

	// 비밀번호 찾기 폼
	@RequestMapping(value="/pwdConfirm", method = RequestMethod.GET)
	public String pwdConfirmGet() {
		return "member/pwdConfirm";
	}
	
	// 임시 비밀번호 발급해서 메일로 보낼 준비처리
	@RequestMapping(value="/pwdConfirm", method = RequestMethod.POST)
	public String pwdConfirmPost(String mid, String toMail) {
		MemberVO vo = memberService.getPwdConfirm(mid, toMail);
		if(vo != null) {
			// 임시비밀번호를 만들자
			UUID uid = UUID.randomUUID();
			String pwd = uid.toString().substring(0,8);
			memberService.setPwdChange(mid, passwordEncoder.encode(pwd));
			String content = pwd;
			//return "redirect:mail/pwdConfirmSend?toMail="+toMail+"&content="+content;
			return "redirect:/mail/pwdConfirmSend/"+toMail+"/"+content+"/";
		}
		else {
			msgFlag = "pwdConfirmNo";
			return "redirect:/msg/" + msgFlag;
		}
	}
	
	// 아이디 찾기 폼
	@RequestMapping(value="/idConfirm", method = RequestMethod.GET)
	public String idConfirmGet() {
		return "member/idConfirm";
	}
	
	// 아이디를 찾아서 메일로 보낼 준비처리(여기서는 사용하지 않았음.....)
//	@RequestMapping(value="/idConfirm", method = RequestMethod.POST)
//	public String idConfirmPost(String toMail) {
//		MemberVO vo = memberService.getIdConfirm(toMail);
//		if(vo != null) {
//			return "redirect:/mail/idConfirmSend/"+toMail+"/"+vo.getMid()+"/";
//		}
//		else {
//			msgFlag = "pwdConfirmNo";
//			return "redirect:/msg/" + msgFlag;
//		}
//	}
	
  // 아이디를 찾아서 화면에 리스트로 출력처리준비하는곳
	@RequestMapping(value="/idConfirm", method = RequestMethod.POST)
	public String idConfirmPost(String toMail, Model model) {
		ArrayList<MemberVO> vos = memberService.getIdConfirm(toMail);
		if(vos.size() != 0) {
			model.addAttribute("vos", vos);
			return "member/idSearchList";
		}
		else {
			msgFlag = "pwdConfirmNo";
			return "redirect:/msg/" + msgFlag;
		}
	}
	
}
