package com.spring.cjs2108;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.service.DbShopService;
import com.spring.cjs2108.service.MemberService;
import com.spring.cjs2108.vo.CategorySubVO;
import com.spring.cjs2108.vo.DbBaesongVO;
import com.spring.cjs2108.vo.DbCartListVO;
import com.spring.cjs2108.vo.DbOptionVO;
import com.spring.cjs2108.vo.DbOrderVO;
import com.spring.cjs2108.vo.DbProductVO;
import com.spring.cjs2108.vo.MemberVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {
	String msgFlag = "";
	
	@Autowired
	DbShopService dbShopService;
	
	@Autowired
	MemberService memberService;
	
	// 모든 분류목록 출력하기
	@RequestMapping(value="/dbCategory", method = RequestMethod.GET)
	public String dbCategoryGet(Model model) {
		List<CategorySubVO> mainVos = dbShopService.getCategoryMain();
		List<CategorySubVO> middleVos = dbShopService.getCategoryMiddle();
		List<CategorySubVO> subVos = dbShopService.getCategorySub();
		
		model.addAttribute("mainVos", mainVos);
		model.addAttribute("middleVos", middleVos);
		model.addAttribute("subVos", subVos);
		
		return "admin/dbShop/dbCategory";
	}
	
	// 대분류 선택시 중분류명 가져오기
	@ResponseBody
	@RequestMapping(value="/categoryMiddleName", method = RequestMethod.POST)
	public List<CategorySubVO> categoryMiddleNamePost(String categoryMainCode) {
		return dbShopService.getCategoryMiddleName(categoryMainCode);
	}
	
	
	// 중분류 선택시 소분류명 가져오기
	@ResponseBody
	@RequestMapping(value="/categorySubName", method = RequestMethod.POST)
	public List<CategorySubVO> categorySubNamePost(String categoryMainCode, String categoryMiddleCode) {
		return dbShopService.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}
	
	// 대분류 등록하기
	@ResponseBody
	@RequestMapping(value="/categoryMainInput", method = RequestMethod.POST)
	public String categoryMainInputPost(CategorySubVO vo) {
		CategorySubVO imsiVo  = dbShopService.getCategoryMainOne(vo.getCategoryMainCode(), vo.getCategoryMainName());
		if(imsiVo != null) return "0";
		dbShopService.categoryMainInput(vo);
		return "1";
	}
	
	// 중분류 등록하기
	@ResponseBody
	@RequestMapping(value="/categoryMiddleInput", method = RequestMethod.POST)
	public String categoryMiddleInputPost(CategorySubVO vo) {
		List<CategorySubVO> vos = dbShopService.getCategoryMiddleOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.categoryMiddleInput(vo);
		return "1";
	}
	
	// 소분류 등록하기
	@ResponseBody
	@RequestMapping(value="/categorySubInput", method = RequestMethod.POST)
	public String categorySubInputPost(CategorySubVO vo) {
		List<CategorySubVO> vos = dbShopService.getCategorySubOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.categorySubInput(vo);
		return "1";
	}
	
	// 대분류 삭제하기
	@ResponseBody
	@RequestMapping(value="/delCategoryMain", method = RequestMethod.POST)
	public String delCategoryMainPost(CategorySubVO vo) {
		List<CategorySubVO> vos = dbShopService.getCategoryMiddleOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.delCategoryMain(vo.getCategoryMainCode());
		return "1";
	}
	
	// 중분류 삭제하기
	@ResponseBody
	@RequestMapping(value="/delCategoryMiddle", method = RequestMethod.POST)
	public String delCategoryMiddlePost(CategorySubVO vo) {
		List<CategorySubVO> vos = dbShopService.getCategorySubOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.delCategoryMiddle(vo.getCategoryMiddleCode());
		return "1";
	}
	
	// 소분류 삭제하기
	@ResponseBody
	@RequestMapping(value="/delCategorySub", method = RequestMethod.POST)
	public String delCategorySubPost(CategorySubVO vo) {
		List<DbProductVO> vos = dbShopService.getDbProductOne(vo.getCategorySubCode());
		if(vos.size() != 0) return "0";
		dbShopService.delCategorySub(vo.getCategorySubCode());
		return "1";
	}
	
  //관리자 상품등록에서 상품 작성시, ckeditor에서 글올릴때 이미지와 함께 올린다면 이곳에서 서버 파일시스템에 저장시켜준다.
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
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/");
		OutputStream outStr = new FileOutputStream(new File(uploadPath + originalFilename));
		outStr.write(bytes);		// 서버에 업로드시킨 그림파일이 저장된다.
		
		// 서버 파일시스템에 저장된 파일을 화면(textarea)에 출력하기
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/dbShop/" + originalFilename;
		out.println("{\"originalFilename\":\""+originalFilename+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");       /* "atom":"12.jpg","uploaded":1,"": */
		
		out.flush();
		outStr.close();
	}
	
	// 상품 등록을 위한 목록창 보여주기
	@RequestMapping(value="/dbProduct", method = RequestMethod.GET)
	public String dbProductGet(Model model) {
		List<CategorySubVO> mainVos = dbShopService.getCategoryMain();
		model.addAttribute("mainVos", mainVos);
		return "admin/dbShop/dbProduct";
	}
	
	// 상품 등록 시키기
	@RequestMapping(value="/dbProduct", method = RequestMethod.POST)
	public String dbProductPost(MultipartFile file, DbProductVO vo) {
	  // 이미지파일 업로드시에는 ckeditor폴더에서 board폴더로 복사작업처리
		dbShopService.imgCheckProductInput(file, vo);
		
		msgFlag = "dbProductInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	// 옵션 등록창 보기
	@RequestMapping(value="/dbOption", method=RequestMethod.GET)
	public String dbOptionGet(Model model) {
		String[] productNames = dbShopService.getProductName();
		model.addAttribute("productNames", productNames);
		return "admin/dbShop/dbOption";
	}
	
	// 옵션 기록사항 등록하기
	@RequestMapping(value="/dbOption", method=RequestMethod.POST)
	public String dbOptionPost(DbOptionVO vo, String[] optionName, int[] optionPrice) {
		for(int i=0; i<optionName.length; i++) {
			vo.setProductIdx(vo.getProductIdx());
			vo.setOptionName(optionName[i]);
			vo.setOptionPrice(optionPrice[i]);
			dbShopService.setDbOptionInput(vo);
		}
		
		msgFlag = "dbOptionInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	// 옵션등록시 상품을 선택하면 상품의 상세설명 가져와서 뿌리기
	@ResponseBody
	@RequestMapping(value="/getProductInfor", method = RequestMethod.POST)
	public List<DbProductVO> getProductInfor(String productName) {
		return dbShopService.getProductInfor(productName);
	}
	
  // 판매 상품 진열하기
	@RequestMapping(value="/dbShopList", method=RequestMethod.GET)
	public String dbShopListGet(@RequestParam(name="part", defaultValue="전체", required=false) String part, Model model) {
		//String[] subTitle = studyService.getSubTitle();
		List<DbProductVO> subTitleVos = dbShopService.getSubTitle();
		model.addAttribute("subTitleVos", subTitleVos);
		model.addAttribute("part", part);
		//List<DbProductVO> vos = studyService.getDbShopList();
		List<DbProductVO> vos = dbShopService.getDbShopList(part);
		model.addAttribute("vos", vos);
		
		return "study/dbShop/dbShopList";
	}
	
  // 진열된 상품 클릭시 상품내역 상세보기
	@RequestMapping(value="/dbShopContent", method=RequestMethod.GET)
	public String dbShopContentGet(int idx, Model model) {
		DbProductVO productVo = dbShopService.getDbShopProduct(idx); 			// 상품 상세 정보 불러오기
		List<DbOptionVO> optionVos = dbShopService.getDbShopOption(idx); 	// 옵션 정보 모두 가져오기(두개이상이 올수 있기에 배열(리스트)처리한다.)
		
		model.addAttribute("productVo", productVo);
		model.addAttribute("optionVos", optionVos);
		
		return "study/dbShop/dbShopContent";
	}
	
  // 진열상품중에서 선택한상품을 장바구니로 보내기
	@RequestMapping(value="/dbShopContent", method=RequestMethod.POST)
	public String dbShopContentPost(DbCartListVO vo, HttpSession session) {
		// 구매한 상품과 상품의 옵션 정보를 읽어온다. 이때, 기존에 구매했었던 제품이 장바구니에 담겨있었다면 지금 상품을 기존 장바구니에 'update'시키고, 처음 구매한 장바구니이면 새로담긴 품목을 장바구니 테이블에 'insert'시켜준다.
		// 장바구니테이블에서 지금 구매한 상품이 예전 장바구니에도 담겨있는지 확인하기위해 상품명과 옵션명을 넘겨서 기존 장바구니 내용을 검색해 온다.
		String mid = (String) session.getAttribute("sMid");
		DbCartListVO resVo = dbShopService.dbCartListProductOptionSearch(vo.getProductName(), vo.getOptionName(), mid);
		if(resVo != null) {		// 기존에 구매한적이 있다면....update 시킨다.
			String[] voOptionNums = vo.getOptionNum().split(",");     // 앞에서 넘어온 vo안의 옵션리스트(배열로 넘어온다. 따라서 자료(옵션)가 여러개라면 ','로 들어있기에 ','로 분리시켜주었다.)
			String[] resOptionNums = resVo.getOptionNum().split(","); // 기존 DB에 저장되어 있던 장바구니 : resVo
			int[] nums = new int[99];		// 기존옵션과 지금것을 함께 다시 저장시키기 위한 작업
			String strNums = "";
			for(int i=0; i<voOptionNums.length; i++) {
				nums[i] += (Integer.parseInt(voOptionNums[i]) + Integer.parseInt(resOptionNums[i]));
				strNums += nums[i];
				if(i < nums.length - 1) strNums += ",";
			}
			vo.setOptionNum(strNums);
			dbShopService.dbShopCartUpdate(vo);
		}
		else {			// 기존에 구매한적이 없다면....insert시킨다.
			dbShopService.dbShopCartInput(vo);
		}
		return "redirect:/dbShop/dbCartList";
	}
	
  // 장바구니에 담겨있는 모든 품목들 보여주기
	@RequestMapping(value="/dbCartList", method=RequestMethod.GET)
	public String dbCartListGet(HttpSession session, DbCartListVO vo, Model model) {
		String mid = (String) session.getAttribute("sMid");
		List<DbCartListVO> vos = dbShopService.getDbCartList(mid);
		
		model.addAttribute("cartListVos", vos);
		return "study/dbShop/dbCartList";
	}
	
	// 카트에서 주문한 품목들을 주문테이블에 저장시켜준다.
	@RequestMapping(value="/dbCartList", method=RequestMethod.POST)
	//public String dbCartListPost(String mid, int[] idx, String[] checkItem, Model model) {
	public String dbCartListPost(HttpServletRequest request, Model model, HttpSession session) {
		String[] idxChecked = request.getParameterValues("idxChecked");
		
		DbCartListVO cartVo = new DbCartListVO();
		List<DbOrderVO> orderVos = new ArrayList<DbOrderVO>();
		
		for(String idx : idxChecked) {
			cartVo = dbShopService.getCartIdx(idx);
			DbOrderVO orderVo = new DbOrderVO();
			orderVo.setProductIdx(cartVo.getProductIdx());
			orderVo.setProductName(cartVo.getProductName());
			orderVo.setMainPrice(cartVo.getMainPrice());
			orderVo.setThumbImg(cartVo.getThumbImg());
			orderVo.setOptionName(cartVo.getOptionName());
			orderVo.setOptionPrice(cartVo.getOptionPrice());
			orderVo.setOptionNum(cartVo.getOptionNum());
			orderVo.setTotalPrice(cartVo.getTotalPrice());
			orderVo.setCartIdx(cartVo.getIdx());
			orderVos.add(orderVo);
		}
		
		//model.addAttribute("orderVos", orderVos);
		session.setAttribute("orderVos", orderVos); // 주문에서 보여준후 다시 그대로를 담아서 결제창으로 보내기에 session처리했다.
		
		// 현재 로그인된 고객의 정보를 member2테이블에서 가져온다.
		MemberVO memberVo = memberService.getIdCheck(session.getAttribute("sMid").toString());
		model.addAttribute("memberVo", memberVo);
		
		// 아래는 주문작업이 들어오면 그때 만들어주면된다.
    // 주문고유번호(idx) 만들기(기존 DB의 고유번호(idx) 최대값 보다 +1 시켜서 만든다) 
		DbOrderVO maxIdx = dbShopService.getOrderMaxIdx();
		int idx = 1;
		if(maxIdx != null) idx = maxIdx.getMaxIdx() + 1;
		
		//주문번호(orderIdx) 만들기(->날짜_idx)
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String orderIdx = sdf.format(today) + idx;
		model.addAttribute("orderIdx", orderIdx);
		
		return "study/dbShop/dbOrder";  // 결재 및 주문서 작성 jsp호출
	}
	
	// 장바구니안의 선택된 품목 삭제하기
	@ResponseBody
	@RequestMapping(value="/dbCartDel", method=RequestMethod.POST)
	public String dbCartDelGet(int idx) {
		dbShopService.dbCartDel(idx);
		return "";
	}
	
	// 주문내역 dbOrder/dbBaesong 테이블에 저장하기
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/orderInput", method=RequestMethod.POST)
	public String orderInputPost(HttpSession session, DbOrderVO orderVo, DbBaesongVO bVo) {
		List<DbOrderVO> orderVos = (List<DbOrderVO>) session.getAttribute("orderVos");
		for(DbOrderVO vo : orderVos) {
			vo.setIdx(Integer.parseInt(bVo.getOrderIdx().substring(8))); // 주문테이블에 고유번호를 셋팅한다.	
			vo.setOrderIdx(bVo.getOrderIdx());           // 주문번호를 주문테이블의 주문번호필드에 지정처리한다.
			vo.setMid(bVo.getMid());							
			
			dbShopService.setDbOrder(vo);                 // 주문내용을 주문테이블(dbOrder)에 저장.
			dbShopService.delDbCartList(vo.getCartIdx()); // 주문이 완료되었기에 장바구니(dbCartList)에서 주문한 내역을 삭체처리한다.
		}
		bVo.setOIdx(dbShopService.getOrderOIdx(bVo.getOrderIdx()));		// 주문테이블의 고유번호를 가져온다.
		dbShopService.setDbBaesong(bVo);  // 배송내용을 배송테이블(dbBaesong)에 저장
		dbShopService.setMemberPointPlus((int)(bVo.getOrderTotalPrice() * 0.01), bVo.getMid());	// 회원테이블에 포인트 적립하기(1%)
		
		msgFlag = "orderInputOk";
		
		return "redirect:/msg/" + msgFlag;
	}
	
	// 주문 완료후 주문(배송정보)된 내역 보여주는 폼
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/dbOrderConfirm", method=RequestMethod.GET)
	public String dbOrderConfirmGet(HttpSession session, Model model) {
		List<DbOrderVO> vos = (List<DbOrderVO>) session.getAttribute("orderVos");
		List<DbBaesongVO> bVos = dbShopService.getBaesong(vos.get(0).getMid());
		
		model.addAttribute("vos", vos);
		model.addAttribute("bVo", bVos.get(0));
		//session.removeAttribute("orderVos");
		
		return "study/dbShop/dbOrderConfirm";
	}
	
	// 배송지 정보 보여주기
	@RequestMapping(value="/dbOrderBaesong", method=RequestMethod.GET)
	public String dbOrderBaesongGet(String orderIdx, Model model) {
		List<DbBaesongVO> vos = dbShopService.getOrderBaesong(orderIdx);  // 같은 주문번호가 2개 이상 있을수 있기에 List객체로 받아온다.
		model.addAttribute("vo", vos.get(0));  // 같은 배송지면 0번째것 하나만 vo에 담아서 넘겨주면 된다.
		
		return "study/dbShop/dbOrderBaesong";
	}
	
	// 로그인 사용자가 주문내역 조회하기
	@RequestMapping(value="/dbMyOrder", method=RequestMethod.GET)
	public String dbMyOrderGet(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		String mid = (String) session.getAttribute("sMid");
		int level = (int) session.getAttribute("sLevel");
		if(level == 0) mid = "전체";
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int totRecCnt = dbShopService.totRecCnt(mid);		// 전체자료 갯수 검색
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
	  int startIndexNo = (pag - 1) * pageSize;
	  int curScrStrarNo = totRecCnt - startIndexNo;
	  int blockSize = 3;		// 한블록의 크기를 3개의 Page로 본다.(사용자가 지정한다.)
	  int curBlock = (pag - 1) / blockSize;		// 현재페이지의 블록위치
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
	  /* 블록페이징처리 끝 */
		
		List<DbOrderVO> myOrderVos = dbShopService.getDbMyOrder(startIndexNo, pageSize, mid);
		
		model.addAttribute("myOrderVos", myOrderVos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "study/dbShop/dbMyOrder";
	}

	// 주문 상태별 조회하기
	@RequestMapping(value="/orderStatus", method=RequestMethod.GET)
	public String orderStatusGet(HttpSession session, String orderStatus, Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		String mid = (String) session.getAttribute("sMid");
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int totRecCnt = dbShopService.totRecCntStatus(mid, orderStatus);
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
	  int startIndexNo = (pag - 1) * pageSize;
	  int curScrStrarNo = totRecCnt - startIndexNo;
	  int blockSize = 3;
	  int curBlock = (pag - 1) / blockSize;
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
	  /* 블록페이징처리 끝 */
		
	  List<DbBaesongVO> myOrderVos = dbShopService.getOrderStatus(mid, orderStatus);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("myOrderVos", myOrderVos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "study/dbShop/dbMyOrder";
	}
	
	// 주문 조건 조회하기(날짜별(오늘/일주일/보름/한달/3개월/전체)
	@RequestMapping(value="/orderCondition", method=RequestMethod.GET)
	public String orderConditionGet(HttpSession session, int conditionDate, Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		String mid = (String) session.getAttribute("sMid");
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
	  int totRecCnt = dbShopService.totRecCntOrderCondition(mid, conditionDate);
	  int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
	  int startIndexNo = (pag - 1) * pageSize;
	  int curScrStrarNo = totRecCnt - startIndexNo;
	  int blockSize = 3;
	  int curBlock = (pag - 1) / blockSize;
	  int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
	  /* 블록페이징처리 끝 */
		
	  List<DbBaesongVO> myOrderVos = dbShopService.orderCondition(mid, conditionDate);
	  
		model.addAttribute("conditionDate", conditionDate);
		model.addAttribute("myOrderVos", myOrderVos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		// 아래는 1일/일주일/보름/한달/3달/전체 조회시에 startJumun과 endJumun을 넘겨주는 부분(view에서 시작날짜와 끝날짜를 지정해서 출력시켜주기위해 startJumun과 endJumun값을 구해서 넘겨준다.)
		Calendar startDateJumun = Calendar.getInstance();
		Calendar endDateJumun = Calendar.getInstance();
		startDateJumun.setTime(new Date());  // 오늘날짜로 셋팅
		endDateJumun.setTime(new Date());    // 오늘날짜로 셋팅
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startJumun = "";
		String endJumun = "";
		switch (conditionDate) {
			case 1:
				startJumun = sdf.format(startDateJumun.getTime());
				endJumun = sdf.format(endDateJumun.getTime());
				break;
			case 7:
				startDateJumun.add(Calendar.DATE, -7);
				break;
			case 15:
				startDateJumun.add(Calendar.DATE, -15);
				break;
			case 30:
				startDateJumun.add(Calendar.MONTH, -1);
				break;
			case 90:
				startDateJumun.add(Calendar.MONTH, -3);
				break;
			case 99999:
				startDateJumun.set(2021, 00, 01);
				break;
			default:
				startJumun = null;
				endJumun = null;
		}
		if(conditionDate != 1 && endJumun != null) {
			startJumun = sdf.format(startDateJumun.getTime());
			endJumun = sdf.format(endDateJumun.getTime());
		}
		
		model.addAttribute("startJumun", startJumun);
		model.addAttribute("endJumun", endJumun);
		
		return "study/dbShop/dbMyOrder";
	}
	
	@RequestMapping(value="/dbOrderProcess")
	public String dbOrderProcessGet(Model model,
			@RequestParam(name="startJumun", defaultValue="", required=false) String startJumun,
			@RequestParam(name="endJumun", defaultValue="", required=false) String endJumun,
			@RequestParam(name="orderStatus", defaultValue="전체", required=false) String orderStatus,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
		//int totRecCnt = dbShopService.totRecCntAdminStatus(startJumun, endJumun, orderStatus);
		String strNow = "";
		if(startJumun.equals("")) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strNow = sdf.format(now);
		}
		
		//int totRecCnt = dbShopService.totRecCntAdminStatus(startJumun, endJumun, orderStatus);
		int totRecCnt = dbShopService.totRecCntAdminStatus(strNow, strNow, orderStatus);
		int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStrarNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
		/* 블록페이징처리 끝 */
		
		List<DbBaesongVO> orderVos = dbShopService.adminOrderStatus(startJumun, endJumun, orderStatus);
		
		model.addAttribute("startJumun", startJumun);
		model.addAttribute("endJumun", endJumun);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("orderVos", orderVos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
			
		return "admin/dbShop/dbOrderProcess";
	}
	
  // 관리자가 주문상태를 변경처리하는것
	@ResponseBody
	@RequestMapping(value="/goodsStatus", method=RequestMethod.POST)
	public String goodsStatusGet(String orderStatus, String orderIdx) {
		dbShopService.setOrderStatusUpdate(orderStatus, orderIdx);
		return "";
	}
	
	// 관리자 주문 상태별 조회하기(개인 주문상태별 조회 복사해서 처리함)
	@RequestMapping(value="/adminOrderStatus", method=RequestMethod.GET)
	public String orderStatusGet(Model model, String startJumun, String endJumun,
			@RequestParam(name="orderStatus", defaultValue="전체", required=false) String orderStatus,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
		int totRecCnt = dbShopService.totRecCntAdminStatus(startJumun, endJumun, orderStatus);
		int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStrarNo = totRecCnt - startIndexNo;
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage % blockSize)==0 ? ((totPage / blockSize) - 1) : (totPage / blockSize);
		/* 블록페이징처리 끝 */
		
		List<DbBaesongVO> orderVos = dbShopService.getOrderAdminStatus(startJumun, endJumun, orderStatus);
		
		model.addAttribute("startJumun", startJumun);
		model.addAttribute("endJumun", endJumun);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("orderVos", orderVos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStrarNo", curScrStrarNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "admin/dbShop/dbOrderProcess";
	}
}
