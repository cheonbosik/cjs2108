package com.spring.cjs2108;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {
	
	@RequestMapping(value="/msg/{msgFlag}", method = RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, Model model) {
		
		if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		/*
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("msg", "유저 정보가 변경되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		*/
		
		
		
		
		/*
		else if(msgFlag.substring(0,12).equals("userUpdateOk")) {
			model.addAttribute("msg", "유저 정보가 변경되었습니다.");
			model.addAttribute("url", "/user/userUpdate?"+msgFlag.substring(13));
		}
		*/
		
		
		return "include/message";
	}
	
}
