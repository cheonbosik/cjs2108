package com.spring.cjs2108;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.cjs2108.service.AdminService;
import com.spring.cjs2108.service.GuestService;
import com.spring.cjs2108.vo.GuestVO;
import com.spring.cjs2108.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	String msgFlag = "";
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	GuestService guestService;
	
	@RequestMapping(value="/adMenu", method = RequestMethod.GET)
	public String adMenuGet() {
		return "admin/adMenu";
	}
	
	@RequestMapping(value="/adLeft", method = RequestMethod.GET)
	public String adLeftGet() {
		return "admin/adLeft";
	}
	
	@RequestMapping(value="/adContent", method = RequestMethod.GET)
	public String adContentGet(Model model) {
		int newMember = adminService.getNewMember();
		model.addAttribute("newMember", newMember);
		return "admin/adContent";
	}
	
	@RequestMapping(value="/adMemberList", method = RequestMethod.GET)
	public String adMemberListGet(
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="level", defaultValue="99", required=false) int level,
			@RequestParam(name="mid", defaultValue="", required=false) String mid,
			Model model) {
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int pageSize = 5;
	  int totRecCnt = 0;
	  if(mid.equals("")) {
	  	totRecCnt = adminService.totRecCnt(level);// 전체자료 갯수 검색(level처리)
	  }
	  else {
	  	totRecCnt = adminService.totRecCntMid(mid);	// 개별자료 검색
	  }
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
	  int startIndexNo = (pag - 1) * pageSize;
	  int curScrStrarNo = totRecCnt - startIndexNo;
	  int blockSize = 3;		// 한블록의 크기를 3개의 Page로 본다.(사용자가 지정한다.)
	  int curBlock = (pag - 1) / blockSize;		// 현재페이지의 블록위치
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
	  /* 블록페이징처리 끝 */
	  
	  ArrayList<MemberVO> vos = new ArrayList<MemberVO>();
	  if(mid.equals("")) {	// 전체자료 갯수 검색(level처리)
	  	vos = adminService.getMemberList(startIndexNo, pageSize, level);
	  }
	  else {								// 개별자료 검색
	  	vos = adminService.getMemberListMid(startIndexNo, pageSize, mid);
	  }
	  
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		model.addAttribute("level", level);
		model.addAttribute("mid", mid);
		model.addAttribute("totRecCnt", totRecCnt);
		
		return "admin/member/adMemberList";
	}
	
	@ResponseBody
	@RequestMapping(value="/adMemberLevel", method = RequestMethod.POST)
	public String adMemberLevelPost(int idx, int level) {
		adminService.setLevelUpdate(idx, level);
		return "";
	}
	
	// 방명록 리스트 보기
	@RequestMapping(value="/adGuestList", method = RequestMethod.GET)
	public String adGuestListGet(
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			Model model) {
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int pageSize = 5;
	  int totRecCnt = guestService.totRecCnt();	// 전체자료 갯수 검색
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
		model.addAttribute("totRecCnt", totRecCnt);
		
		return "admin/guest/adGuestList";
	}
	
	// 방명록 선택항목 삭제하기
	@ResponseBody
	@RequestMapping(value="/adGuestList", method = RequestMethod.POST)
	public String adGuestListPost(String delItems) {
		String[] idxs = delItems.split("/");
		for(String idx : idxs) {
			guestService.setGuestDelete(Integer.parseInt(idx));
		}
		return "";
	}
	
	// 임시파일 삭제 메뉴 부르기
	@RequestMapping(value="/imsiFileDelete")
	public String imsiFileDeleteGet() {
		return "admin/file/tempDelete";
	}
	
	// 게시판 임시파일 삭제 하기
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/boardTempDelete", method=RequestMethod.GET)
	public String boardTempDeleteGet(HttpServletRequest request) {
		// board작업시에 생성된 'data/ckeditor/'폴더의 모든 그림파일들을 삭제처리시킨다.
		String uploadPath = request.getRealPath("/resources/data/ckeditor/");
		int fileCnt = adminService.imgDelete(uploadPath);
		msgFlag = "imgDeleteOk$"+fileCnt;
		return "redirect:/msg/" + msgFlag;
	}
	
	// 상품등록 임시파일 삭제 하기
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/productTempDelete", method=RequestMethod.GET)
	public String productTempDeleteGet(HttpServletRequest request) {
		String uploadPath = request.getRealPath("/resources/data/dbShop/");
		int fileCnt = adminService.imgDelete(uploadPath);
		msgFlag = "imgDeleteOk$"+fileCnt;
		return "redirect:/msg/" + msgFlag;
	}
	
	// data폴더의 임시파일 삭제 하기
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/dataTempDelete", method=RequestMethod.GET)
	public String dataTempDeleteGet(HttpServletRequest request) {
		String uploadPath = request.getRealPath("/resources/data/");
		int fileCnt = adminService.imgDelete(uploadPath);
		msgFlag = "imgDeleteOk$"+fileCnt;
		return "redirect:/msg/" + msgFlag;
	}
	
	// data/thumbnail폴더의 연습파일 삭제 하기
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/thumbnailTempDelete", method=RequestMethod.GET)
	public String thumbnailTempDeleteGet(HttpServletRequest request) {
		String uploadPath = request.getRealPath("/resources/data/thumbnail/");
		int fileCnt = adminService.imgDelete(uploadPath);
		msgFlag = "imgDeleteOk$"+fileCnt;
		return "redirect:/msg/" + msgFlag;
	}
	
	// 임시폴더의 파일내역보기
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value="/tempFileLoad")
	public String[] tempFileLoadGet(HttpServletRequest request, String folderName) throws IOException {
		String realPath = request.getRealPath("/resources/");
		
		if(folderName.equals("DATA")) {
			realPath += "data/";
		}
		else if(folderName.equals("THUMB")) {
			System.out.println(folderName);
			realPath += "data/thumbnail/";
		}
		else if(folderName.equals("DBSHOP")) {
			realPath += "data/dbShop/";
		}
		else if(folderName.equals("BOARD")) {
			realPath += "data/ckeditor/";
		}
		String[] files = new File(realPath).list();
		
		return files;
	}
	
	// 임시폴더의 파일 1개씩 삭제하기
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value="/fileDelete")
	public String fileDeleteGet(HttpServletRequest request, String folderName, String file) throws IOException {
		String realPath = request.getRealPath("/resources/");
		
		if(folderName.equals("DATA")) {
			realPath += "data/";
		}
		else if(folderName.equals("THUMB")) {
			System.out.println(folderName);
			realPath += "data/thumbnail/";
		}
		else if(folderName.equals("DBSHOP")) {
			realPath += "data/dbShop/";
		}
		else if(folderName.equals("BOARD")) {
			realPath += "board/";
		}
		File delFile = new File(realPath+file);
		if(delFile.exists()) {
			if(delFile.isDirectory()) return "0";
			else delFile.delete();
			return "1";
		}
		else return "0";
	}
	
	
}

