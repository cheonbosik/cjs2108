package com.spring.cjs2108;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.service.Study2Service;
import com.spring.cjs2108.vo.AddressVO;
import com.spring.cjs2108.vo.AreaVO;
import com.spring.cjs2108.vo.ThumbnailVO;

@Controller
@RequestMapping("/study2")
public class Study2Controller {
	String msgFlag = "";
	
	@Autowired
	Study2Service study2Service;
	
	// 썸네일 이미지 처리 폼 호출하기
	@RequestMapping(value="/thumbnail", method = RequestMethod.GET)
	public String thumbnailGet() {
		return "study/thumbnail/thumbnail";
	}
	
	//썸네일 이미지 파일 올리기 처리
	@RequestMapping(value="/thumbnail", method=RequestMethod.POST)
	public String thumbnailPost(MultipartFile file) throws Exception {
	 ThumbnailVO vo = study2Service.thumbnailCreate(file);
	
	 if(vo.getRes() == 1) {
	   msgFlag = "thumbnailCreateOk$oFileName="+vo.getOFileName()+"&tFileName="+vo.getTFileName();
	   //msgFlag = "thumbnailCreateOk$oFileName="+URLEncoder.encode(vo.getOFileName(),"UTF-8")+"&tFileName="+URLEncoder.encode(vo.getTFileName(),"UTF-8");
	 }
	 else {
	   msgFlag = "thumbnailCreateNo";
	 }
	
	 return "redirect:/msg/" + msgFlag;
	}
	
	//썸네일 이미지 보여주기
	@RequestMapping(value="/thumbnailView", method=RequestMethod.GET)
	public String thumbnailViewGet(String oFileName, String tFileName, Model model) {
	 model.addAttribute("oFileName", oFileName);
	 model.addAttribute("tFileName", tFileName);
	
	 return "study/thumbnail/thumbnailView";
	}
	
	// 카카오맵 기본 설정
	@RequestMapping(value="/kakaomap")
	public String kakaomapGet() {
		return "study/kakaomap/kakao";
	}
	
	// 카카오맵 마커표시 / DB저장
	@RequestMapping(value="/kakaoEx1")
	public String kakaoEx1Get() {
		return "study/kakaomap/kakaoEx1";
	}
	
	@ResponseBody
	@RequestMapping(value="/kakaoEx1", method=RequestMethod.POST)
	public int kakaoEx1Post(AddressVO vo) {
		AddressVO placeVo = study2Service.getAddressName(vo.getAddress());
		if(placeVo != null) return 0;
		study2Service.setAddressName(vo);
		return 1;
	}
	
	@RequestMapping("/kakaoEx2")
	public String kakaoEx2Get(Model model, String address) {
		if(address == null) address = "청주 사창사거리";
		model.addAttribute("address", address);
		return "study/kakaomap/kakaoEx2";
	}
	
	@RequestMapping("/kakaoEx3")
	public String kakaoEx3Get(Model model,
			@RequestParam(name="address", defaultValue="청주 사창사거리", required=false) String address) {
		List<AddressVO> vos = study2Service.getAddressNameList();
		AddressVO vo = study2Service.getAddressName(address);
		
		model.addAttribute("vos", vos);
		model.addAttribute("vo", vo);
		model.addAttribute("address", address);
		return "study/kakaomap/kakaoEx3";
	}
	
	@ResponseBody
	@RequestMapping(value="/kakaoEx3Delete", method = RequestMethod.POST)
	public String kakaoEx5DeletePost(String address) {
		study2Service.getAddressNameDelete(address);
		return "";
	}
	
	@RequestMapping(value="/kakaoEx4", method=RequestMethod.GET)
	public String kakaoEx4Get(Model model) {
		String[] address1s = study2Service.getAddress1();
		
		double latitude = 36.63508797975421;
		double longitude = 127.45959376343134;
  	
		model.addAttribute("address1s", address1s);
		
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);
		
		return "study/kakaomap/kakaoEx4";
	}
	
	@ResponseBody
	@RequestMapping(value="/kakaoEx4", method=RequestMethod.POST)
	public List<AreaVO> kakaoEx4Post(@RequestBody String address1) {
		List<AreaVO> vos = study2Service.getAddress2(address1);
		
		return vos;
	}
	
	@RequestMapping(value="/kakaoEx4Search", method=RequestMethod.POST)
	public String kakaoEx4SearchPost(Model model, 
			@RequestParam(name="address1", defaultValue="충청북도", required=false) String address1,
			@RequestParam(name="address2", defaultValue="청주시", required=false) String address2) {
		
		AreaVO vo = study2Service.getAddressSearch(address1, address2);
		
		model.addAttribute("address1", vo.getAddress1());
		model.addAttribute("address2", vo.getAddress2());
		model.addAttribute("latitude", vo.getLatitude());
		model.addAttribute("longitude", vo.getLongitude());
		
		return "study/kakaomap/kakaoEx4Search";
	}
	
	// 구글 차트 고르기
	@RequestMapping(value="/chart")
	public String chartGet(Model model,
			@RequestParam(name="part", defaultValue="bar", required=false) String part) {
		model.addAttribute("part", part);
		return "study/chart/chart";
	}
	
	// QR코드 생성하기 폼(URL 등록폼)
	@RequestMapping(value="/qrCode", method=RequestMethod.GET)
	public String qrCodeGet() {
		return "study/qrcode/qrcode";
	}
	
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value="/qrCreate", method=RequestMethod.POST)
	public String barCreatePost(HttpServletRequest request, HttpSession session, String moveUrl) {
		String mid = (String) session.getAttribute("sMid");
		String uploadPath = request.getRealPath("/resources/data/qrcode/");
		String barCodeName = study2Service.qrCreate(mid, uploadPath, moveUrl);	// qr코드가 저장될 서버경로와 qr코드 찍었을때 이동할 url을 서비스객체로 넘겨서 qr코드를 생성하게 한다.

    return barCodeName;
	}
}
