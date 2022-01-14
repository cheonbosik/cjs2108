package com.spring.cjs2108;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.encryption.ARIAUtil;
import com.spring.cjs2108.service.StudyService;
import com.spring.cjs2108.vo.Goods3VO;
import com.spring.cjs2108.vo.MainImageVO;
import com.spring.cjs2108.vo.MemberVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	String msgFlag = "";
	
	@Autowired
	StudyService studyService;
	
//	@Autowired
//	MemberService memberService;
	
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
	
	// HashMap을 이용한 자료의 전송
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest1Post(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		vos = studyService.getCity(dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		
		return map;
	}
	
	// ArrayList를 이용한 자료의 전송
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest2Post(String dodo) {
//		ArrayList<String> vos = new ArrayList<String>();
//		vos = studyService.getCity(dodo);
//		System.out.println("vos : " + vos);
//		return vos;
		return studyService.getCity(dodo);
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest3", method = RequestMethod.POST)
	public String[] ajaxTest3Post(String dodo) {
//		String[] strArr = new String[100];
//		strArr = studyService.getCityString(dodo);
//		return strArr;
		return studyService.getCityString(dodo);
	}
	
	// 아이디를 데이터베이스에서 검색하기위한 폼호출
	@RequestMapping(value="/ajax/ajaxTest4", method = RequestMethod.GET)
	public String ajaxTest4Get() {
		return "study/ajax/ajaxTest4";
	}
	
	// 아이디를 입력받아서 데이터베이스 'member'테이블에서 검색하기(동일한 자료 1건만 검색하여 vo를 돌려준다.)....
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4", method = RequestMethod.POST)
	public MemberVO ajaxTest4Post(String mid) {
//		MemberVO vo = new MemberVO();
//		vo = studyService.getMemberVO(mid);
		//vo = memberService.getMemberVO(mid);
//		return vo;
		return studyService.getMemberVO(mid);
	}
	
	// 아이디를 입력받아서 데이터베이스 'member'테이블에서 검색하기(like연산자를 이용한 '여러건'의 자료 검색해서 vos를 돌려준다.)....
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest4_2", method = RequestMethod.POST)
	public ArrayList<MemberVO> ajaxTest4_2Post(String mid) {
//		ArrayList<MemberVO> vos = studyService.getMemberVos(mid);
//		System.out.println("vos: " + vos);
//		return vos;
		return studyService.getMemberVos(mid);
	}
	
	// 상품등록테스트화면(대/중/소/상품명 등록창)
	@RequestMapping("/goods")
	public String goodsGet(Model model) {
		List<Goods3VO> vos = studyService.getProduct1();
		model.addAttribute("vos", vos);
		return "study/ajax/goods";
	}
	
	// 대분류선택시
	@ResponseBody
	@RequestMapping(value="/goods1", method = RequestMethod.POST)
	public ArrayList<Goods3VO> goods1Post(String product1) {
//		ArrayList<Goods2VO> vos = studyService.getProduct2(product1);
//		return vos;
		return studyService.getProduct2(product1);
	}
	
	// 중분류선택시
	@ResponseBody
	@RequestMapping(value="/goods2", method = RequestMethod.POST)
	public ArrayList<Goods3VO> goods2Post(Goods3VO vo) {
		return studyService.getProduct3(vo.getProduct1(), vo.getProduct2());
	}
	
	@RequestMapping(value="/uuid", method = RequestMethod.GET)
	public String uuidGet() {
		return "study/uuid/uid";
	}
	
	@ResponseBody
	@RequestMapping(value="/uuid", method = RequestMethod.POST)
	public String uuidPost() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}
	
	@RequestMapping(value="/fileUpload", method = RequestMethod.GET)
	public String fileUploadGet() {
		return "study/fileUpload/fileUpload";
	}
	
	@RequestMapping(value="/fileUpload", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName) {
		int res = studyService.fileUpload(fName);
		if(res == 1) {		
			msgFlag = "fileUploadOk";
		}
		else {
			msgFlag = "fileUploadNo";
		}
		return "redirect:/msg/" + msgFlag;
	}
	
	@RequestMapping(value="/aria", method = RequestMethod.GET)
	public String ariaGet() {
		return "study/aria/aria";
	}
	
	@ResponseBody
	@RequestMapping(value="/aria", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String ariaPost(String pwd) {
		String encPwd="", decPwd="";
		// 비밀번호 암호화(ARIA)
		try {
			encPwd = ARIAUtil.ariaEncrypt(pwd);
			decPwd = ARIAUtil.ariaDecrypt(encPwd);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// pwd = "Encrypt : " + encPwd + " / Decrypt : " + decPwd;
		pwd = "암호화 : " + encPwd + " / 복호화 : " + decPwd;
		return pwd;
	}
	
  // 달력내역 가져오기
	@RequestMapping(value="/calendar", method=RequestMethod.GET)
	public String calendarGet() {
		studyService.getCalendar();
		return "study/calendar/calendar";
	}
	
  // 메인 상품/포토/이벤트 이미지관리(ckeditor로 등록된 메인 이미지 처리) - 등록폼 보기
	@RequestMapping(value="/mainImage", method = RequestMethod.GET)
	public String mainImageGet() {
		return "study/mainImage/mainImage";
	}
	
	// ckeditor에서 등록한 이미지 업로드...
	@ResponseBody
	@RequestMapping(value="/mainImage/imageUpload")
	public void mainImageUploadGet(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
		response.setCharacterEncoding("utf-8");
   response.setContentType("text/html;charset=utf-8");
	  
	  String fileName=upload.getOriginalFilename();

   Date date = new Date();
   SimpleDateFormat imsi = new SimpleDateFormat("yyMMddHHmmss");
   fileName = imsi.format(date)+"_"+fileName;
   byte[] bytes = upload.getBytes();
	  
   // 이곳은 그림파일을 서버의 파일시스템으로 저장하는것(아래로 3줄)
	  String uploadPath = request.getSession().getServletContext().getRealPath("/")+"/resources/data/";
   OutputStream outStr = new FileOutputStream(new File(uploadPath + fileName));
   outStr.write(bytes);
   
   // 아래는 ckeditor화면에 그림을 출력시켜주는것...(아래로 3줄)
   PrintWriter out=response.getWriter();
   String fileUrl=request.getContextPath()+"/data/"+fileName;

   out.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
   out.flush();
   outStr.close();
	}
	
 // 메인 상품 이미지관리(ckeditor로 등록된 메인 이미지 처리) - 'data'폴더에서 'data/mainImage'폴더로 복사작업..
	@RequestMapping(value="/mainImage", method = RequestMethod.POST)
	public String mainImagePost(MainImageVO vo, Model model) {
 	  // 이미지파일 업로드시에는 'resources/data'폴더에서 'resources/data/mainImage'폴더로 복사작업처리
		studyService.imgCheckInput(vo);
		msgFlag = "mainImageInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
 // 메인 이미지 보기...
	@RequestMapping(value="/mainImageList", method = RequestMethod.GET)
	public String mainImageListGet(Model model,	@RequestParam(name="idx", defaultValue="0", required=false) int idx) {
		List<MainImageVO> mainImageVos = studyService.getMainImageList(idx);
		model.addAttribute("mainImageVos", mainImageVos);
		model.addAttribute("idx", mainImageVos.get(0).getIdx());
		model.addAttribute("part", mainImageVos.get(0).getPart());
		
		List<MainImageVO> partVos = studyService.getMainImagePart();
		model.addAttribute("partVos", partVos);
		return "study/mainImage/mainImageList";
	}
	
	// 메인이미지 보기에 보이는 현재 이미지들 삭제하기
	@ResponseBody
	@RequestMapping(value="/mainImageDelete")
	public String mainImageDelete(int idx) {
		studyService.mainImageDelete(idx);
		return "";
	}
}
