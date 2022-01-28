package com.spring.cjs2108;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import com.spring.cjs2108.pagination.PageProcess;
import com.spring.cjs2108.pagination.PageVO;
import com.spring.cjs2108.service.PhotoService;
import com.spring.cjs2108.vo.PhotoVO;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	String msgFlag = "";
	
  @Autowired
  PhotoService photoService;
  
  @Autowired
  PageProcess pageProcess;
  
  @RequestMapping(value="/photo", method=RequestMethod.GET)
  public String photoGet(Model model,
  		@RequestParam(name="photo", defaultValue="", required=false) String photo) {
  	List<PhotoVO> vos = photoService.getPhoto();
  	int[] idxs = new int[20];
  	String[] titles = new String[20];
  	String[] thumbnails = new String[20];
  	int cnt = 0;
  	for(PhotoVO vo : vos) {
  		idxs[cnt] = vo.getIdx();
  		titles[cnt] = vo.getTitle();
  		thumbnails[cnt] = vo.getThumbnail();
  		cnt++;
  	}
  	model.addAttribute("vos", vos);
  	model.addAttribute("idxs", idxs);
  	model.addAttribute("titles", titles);
  	model.addAttribute("thumbnails", thumbnails);
  	model.addAttribute("photo", photo);
  	model.addAttribute("imgCnt", vos.size());
  	
  	return "photo/photo";
  }
  
  @RequestMapping(value="/photoInput", method=RequestMethod.GET)
  public String photoInputGet() {
  	return "photo/photoInput";
  }
  
  @RequestMapping(value="/photoInput", method=RequestMethod.POST)
  public String photoInputPost(PhotoVO vo, HttpServletRequest request) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		photoService.imgCheck(vo, uploadPath);
  	msgFlag = "photoInputOk";
  	
  	return "redirect:/msg/" + msgFlag;
  }
  
  // 포토갤러리 사진 업로드부분
	@RequestMapping("/imageUpload")
	@ResponseBody
	public void imagePhoto(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
	  response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
	   
    UUID uid = UUID.randomUUID();
    String fileName = uid + "_" + upload.getOriginalFilename();
    
    String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/");
    OutputStream outStr = new FileOutputStream(new File(uploadPath + fileName));
    
    byte[] bytes = upload.getBytes();
    outStr.write(bytes);
    
    PrintWriter out=response.getWriter();
    String fileUrl=request.getContextPath()+"/data/"+fileName;
    // JSON방식을 이용해서 브라우저 textarea 화면창에 출력한다.
    out.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
    
    out.flush();
    outStr.close();
	}

  @RequestMapping(value="/photoContent", method=RequestMethod.GET)
  public String photoContentGet(Model model, int idx) {
  	photoService.setReadNumCount(idx);
  	PhotoVO vo = photoService.getPhotoContent(idx);
  	model.addAttribute("vo", vo);
  	
  	return "photo/photoContent";
  }
  
  @RequestMapping(value="/photoDelete", method=RequestMethod.GET)
  public String photoDeleteGet(int idx, HttpServletRequest request) {
  	String deletePath = request.getSession().getServletContext().getRealPath("/resources/data/photo/");
  	photoService.photoDelete(idx, deletePath);
  	msgFlag = "photoDeleteOk";
  	
  	return "redirect:/msg/" + msgFlag;
  }
  
  @RequestMapping(value="/photoPart", method=RequestMethod.GET)
  public String photoPartGet(String part,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
			Model model) {
    PageVO pageVO = pageProcess.pagination(pag, pageSize, "photo", part, "");
    pageVO.setPart(part);
    //System.out.println("pageVO : " + pageVO);
		
		List<PhotoVO> vos = photoService.photoPartList(pageVO.getStartNo(), pageSize, part);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("vos", vos);
		
		return "photo/photoPart";
  }
  
  @RequestMapping(value="/photoSearch", method=RequestMethod.GET)
  public String photoSearchGet(String search, String searchString,
  		@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
			Model model) {
  	
  	PageVO pageVO = pageProcess.pagination(pag, pageSize, "photo", search, searchString);
  	
  	List<PhotoVO> vos = photoService.photoSearchList(pageVO.getStartNo(), pageSize, search, searchString);
  	
  	String searchTitle = "";
  	if(search.equals("title")) searchTitle = "글제목"; 
  	else if(search.equals("name")) searchTitle = "글쓴이"; 
  	else searchTitle = "글내용"; 
  	
  	model.addAttribute("vos", vos);
  	model.addAttribute("pageVO", pageVO);
  	model.addAttribute("search", search);
  	model.addAttribute("searchTitle", searchTitle);
  	model.addAttribute("searchString", searchString);
  	model.addAttribute("searchCount", pageVO.getTotRecCnt());
  	
  	return "photo/photoSearch";
  }
}
