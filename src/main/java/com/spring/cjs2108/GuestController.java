package com.spring.cjs2108;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.cjs2108.service.GuestService;
import com.spring.cjs2108.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {
	String msgFlag = "";
	
	@Autowired
	GuestService guestService;
	
	@RequestMapping("/guestList")
	public String guestListGet(
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			Model model) {
	
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int pageSize = 2;
	  int totRecCnt = guestService.totRecCnt();		// 전체자료 갯수 검색
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
	  int startIndexNo = (pag - 1) * pageSize;
	  int curScrStrarNo = totRecCnt - startIndexNo;
	  int blockSize = 3;		// 한블록의 크기를 3개의 Page로 본다.(사용자가 지정한다.)
	  int curBlock = (pag - 1) / blockSize;		// 현재페이지의 블록위치
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
	  /* 블록페이징처리 끝 */
		
	  List<GuestVO> vos = guestService.getGuestList(startIndexNo, pageSize);
	  
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "guest/guestList";
	}
	
	@RequestMapping(value="/guestInput", method = RequestMethod.GET)
	public String guestInputGet() {
		return "guest/guestInput";
	}
	
	@RequestMapping(value="/guestInput", method = RequestMethod.POST)
	public String guestInputPost(GuestVO vo) {
		guestService.setGuestInput(vo);
		
		msgFlag = "guestInputOk";
		
		return "redirect:/msg/" + msgFlag;
	}
}
