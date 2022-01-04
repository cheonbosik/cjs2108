package com.spring.cjs2108;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.encryption.ARIAUtil;
import com.spring.cjs2108.service.StudyService;
import com.spring.cjs2108.vo.Goods1VO;
import com.spring.cjs2108.vo.Goods2VO;
import com.spring.cjs2108.vo.Goods3VO;
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
		List<Goods1VO> vos = studyService.getProduct1();
		model.addAttribute("vos", vos);
		return "study/ajax/goods";
	}
	
	// 대분류선택시
	@ResponseBody
	@RequestMapping(value="/goods1", method = RequestMethod.POST)
	public ArrayList<Goods2VO> goods1Post(String product1) {
//		ArrayList<Goods2VO> vos = studyService.getProduct2(product1);
//		return vos;
		return studyService.getProduct2(product1);
	}
	
	// 중분류선택시
	@ResponseBody
	@RequestMapping(value="/goods2", method = RequestMethod.POST)
	public ArrayList<Goods3VO> goods2Post(Goods2VO vo) {
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
}
