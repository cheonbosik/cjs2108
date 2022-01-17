package com.spring.cjs2108;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.service.Study2Service;
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
}
