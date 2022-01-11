package com.spring.cjs2108;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sessionShop")
public class SessionShopController {
	String msgFlag = "";
	
	// 세션을 활용한 쇼핑몰
	
	// 품목리스트 보여주기
	@RequestMapping(value="/shopList", method = RequestMethod.GET)
	public String shopListGet() {
		return "study/sessionShop/shopList";
	}
	// 선택한 품목을 장바구니에 담기
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/shopList", method = RequestMethod.POST)
	public String shopListPost(HttpSession session, String product) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		
		if(session.getAttribute("sProductList") == null) {
			productList = new ArrayList<String>();
		}
		productList.add(product);
		session.setAttribute("sProductList", productList);
		
		return "study/sessionShop/shopList";
	}
	
	// 장바구니 보여주기
	@RequestMapping(value="/cart", method = RequestMethod.GET)
	public String cartGet(HttpSession session, Model model) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		if(productList == null || productList.size() == 0) {
			msgFlag = "sessionProductNo";
			return "redirect:/msg/" + msgFlag;
		}
		else {
			Collections.sort(productList);
			model.addAttribute("productList", productList);
			return "study/sessionShop/cart";
		}
	}
	
	// 장바구니 모두 비우기(구매취소)
	@RequestMapping(value="/cartReset", method = RequestMethod.GET)
	public String cartResetGet(HttpSession session) {
		session.removeAttribute("sProductList");
		return "redirect:/sessionShop/cart";
	}
	
	// 장바구니 수량 증가하기
	@RequestMapping(value="/cartAdd", method = RequestMethod.GET)
	public String cartAddGet(HttpSession session, String product) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		productList.add(product);
		session.setAttribute("sProductList", productList);
		
		return "redirect:/sessionShop/cart";
	}
	
	// 장바구니 수량 감소하기
	@RequestMapping(value="/cartSub", method = RequestMethod.GET)
	public String cartSubGet(HttpSession session, String product) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		productList.remove(product);
		session.setAttribute("sProductList", productList);
		
		return "redirect:/sessionShop/cart";
	}
	
	// 장바구니 한개 품목 취소하기
	@RequestMapping(value="/cartDel", method = RequestMethod.GET)
	public String cartDelGet(HttpSession session, String product) {
		List<String> productImsi = new ArrayList<String>();
		productImsi.add(product);
		
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		productList.removeAll(productImsi);
		
		return "redirect:/sessionShop/cart";
	}
	
}

