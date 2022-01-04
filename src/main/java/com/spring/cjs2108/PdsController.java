package com.spring.cjs2108;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.cjs2108.encryption.SecurityUtil;
import com.spring.cjs2108.service.PdsService;
import com.spring.cjs2108.vo.PdsVO;

@Controller
@RequestMapping("/pds")
public class PdsController {
	String msgFlag = "";
	
	@Autowired
	PdsService pdsService;
	
	@RequestMapping(value="/pdsList", method=RequestMethod.GET)
	public String pdsListGet(
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			Model model) {
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int totRecCnt = pdsService.totRecCnt(part);		// 전체자료 갯수 검색
	  int pageSize = 5;
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
	  int startIndexNo = (pag - 1) * pageSize;
	  int curScrStartNo = totRecCnt - startIndexNo;
	  int blockSize = 3;		// 한블록의 크기를 3개의 Page로 본다.(사용자가 지정한다.)
	  int curBlock = (pag - 1) / blockSize;		// 현재페이지의 블록위치
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
	  /* 블록페이징처리 끝 */
		
		List<PdsVO> vos = pdsService.getPdsList(startIndexNo, pageSize, part);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		model.addAttribute("part", part);
		
		return "pds/pdsList";
	}
	
	@RequestMapping(value="/pdsInput", method=RequestMethod.GET)
	public String pdsInputGet() {
		return "pds/pdsInput";
	}
	
	// 자료실 업로드 처리
	@RequestMapping(value="/pdsInput", method=RequestMethod.POST)
	public String pdsInputPost(MultipartHttpServletRequest file, PdsVO vo) {
		String pwd = vo.getPwd();
		SecurityUtil security = new SecurityUtil();
		pwd = security.encryptSHA256(pwd);
		vo.setPwd(pwd);
		
		pdsService.pdsInput(file, vo);
		
		msgFlag = "pdsInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	// 다운로드수 증가
	@ResponseBody
	@RequestMapping(value="/downCheck", method=RequestMethod.GET)
	public int downCheckGet(int idx) {
		pdsService.setDownCheck(idx);
		return 1;
	}
	
	@RequestMapping(value="/pdsDown", method=RequestMethod.GET)
	public String pdsDownGet(HttpServletRequest request, PdsVO vo) throws IOException {
		pdsService.setDownCheck(vo.getIdx());  // 다운횟수 증가
		
		String directory = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
	  
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		FileInputStream fis = null;
		
		String zipPath = directory + "imsi/";
		String zipName = vo.getTitle() + ".zip";
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		
		byte[] buffer = new byte[2048];
		
		for(int i=0; i<fSNames.length; i++) {
			File file = new File(directory + fSNames[i]);
			File moveAndRename = new File(directory + "imsi/" + fNames[i]);
			
			file.renameTo(moveAndRename);
			
			fis = new FileInputStream(moveAndRename);
			zout.putNextEntry(new ZipEntry(fNames[i]));
			
			int data;
			while((data = fis.read(buffer, 0, buffer.length)) != -1) {
				zout.write(buffer, 0, data);
			}
			
			zout.closeEntry();
			fis.close();
			
			moveAndRename.renameTo(file);
		}
		zout.close();
		
		return "redirect:/pds/pdsDownAction?file="+java.net.URLEncoder.encode(zipName, "UTF-8");
	}
	
	@RequestMapping(value="/pdsDownAction", method=RequestMethod.GET)
	public void downActionGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fSName = request.getParameter("file");
		String downPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/imsi/")+fSName;
		File downFile = new File(downPath);
		
		String downFileName = null;
		if(request.getHeader("user-agent").indexOf("MSIE") == -1) {
			downFileName = new String(fSName.getBytes("UTF-8"), "8859_1");
		}
		else {
			downFileName = new String(fSName.getBytes("EUC-KR"), "8859_1");
		}
		
		response.setHeader("Content-Disposition", "attachment;filename="+downFileName);
		
		FileInputStream fis = new FileInputStream(downFile);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] b = new byte[1024];
		int data = 0;
		
		while((data = fis.read(b, 0, b.length)) != -1) {
			sos.write(b, 0, data);
		}
		sos.flush();
		
		sos.close();
		fis.close();
		
		new File(downPath).delete();
	}
	
	@RequestMapping(value="/pdsContent")
	public String pdsContentGet(int idx, Model model) {
		PdsVO vo = pdsService.getPdsContent(idx);
		model.addAttribute("vo", vo);
		
		return "pds/pdsContent";
	}
	
	@ResponseBody
	@RequestMapping(value="/pdsPwdCheck", method = RequestMethod.POST)
	public String pdsPwdCheckGet(int idx, String pwd) {
		SecurityUtil security = new SecurityUtil();
		pwd = security.encryptSHA256(pwd);
		
		PdsVO vo = pdsService.getPdsContent(idx);
		if(!pwd.equals(vo.getPwd())) return "0";
		pdsService.pdsDelete(vo);
		return "1";
	}
}
