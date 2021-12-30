package com.spring.cjs2108;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//return "main/home";
		return "main/main";
	}
	
	// 게시판의 글 작성시, ckeditor에서 글올릴때 이미지와 함께 올린다면 이곳에서 서버 파일시스템에 저장시켜준다.
	@ResponseBody
	@RequestMapping("/imageUpload")
	public void imageUploadGet(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String originalFilename = upload.getOriginalFilename();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		originalFilename = sdf.format(date) + "_" + originalFilename;
		
		byte[] bytes = upload.getBytes();
		
		// ckeditor에서 올린 파일을 서버 파일시스템에 저장시켜준다.
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		OutputStream outStr = new FileOutputStream(new File(uploadPath + originalFilename));
		outStr.write(bytes);		// 서버에 업로드시킨 그림파일이 저장된다.
		
		// 서버 파일시스템에 저장된 파일을 화면(textarea)에 출력하기
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/ckeditor/" + originalFilename;
		out.println("{\"originalFilename\":\""+originalFilename+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");       /* "atom":"12.jpg","uploaded":1,"": */
		
		out.flush();
		outStr.close();
	}
}
