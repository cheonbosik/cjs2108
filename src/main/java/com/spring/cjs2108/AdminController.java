package com.spring.cjs2108;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.cjs2108.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	String msgFlag = "";
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping("/adMenu")
	public String adMenuGet() {
		return "admin/adMenu";
	}
}
