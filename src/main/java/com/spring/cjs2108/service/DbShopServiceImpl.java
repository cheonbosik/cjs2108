package com.spring.cjs2108.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.dao.DbShopDAO;
import com.spring.cjs2108.vo.CategorySubVO;
import com.spring.cjs2108.vo.DbBaesongVO;
import com.spring.cjs2108.vo.DbCartListVO;
import com.spring.cjs2108.vo.DbOptionVO;
import com.spring.cjs2108.vo.DbOrderVO;
import com.spring.cjs2108.vo.DbProductVO;

@Service
public class DbShopServiceImpl implements DbShopService {
	@Autowired
	DbShopDAO dbShopDAO;

	@Override
	public List<CategorySubVO> getCategoryMain() {
		return dbShopDAO.getCategoryMain();
	}

	@Override
	public List<CategorySubVO> getCategoryMiddle() {
		return dbShopDAO.getCategoryMiddle();
	}

	@Override
	public List<CategorySubVO> getCategorySub() {
		return dbShopDAO.getCategorySub();
	}

	@Override
	public CategorySubVO getCategoryMainOne(String categoryMainCode, String categoryMainName) {
		return dbShopDAO.getCategoryMainOne(categoryMainCode, categoryMainName);
	}

	@Override
	public List<CategorySubVO> getCategoryMiddleOne(CategorySubVO vo) {
		return dbShopDAO.getCategoryMiddleOne(vo);
	}
	
	@Override
	public List<CategorySubVO> getCategorySubOne(CategorySubVO vo) {
		return dbShopDAO.getCategorySubOne(vo);
	}
	
	@Override
	public List<DbProductVO> getDbProductOne(String categorySubCode) {
		return dbShopDAO.getDbProductOne(categorySubCode);
	}
	
	@Override
	public void categoryMainInput(CategorySubVO vo) {
		dbShopDAO.categoryMainInput(vo);
	}
	
	@Override
	public void categoryMiddleInput(CategorySubVO vo) {
		dbShopDAO.categoryMiddleInput(vo);
	}

	@Override
	public void categorySubInput(CategorySubVO vo) {
		dbShopDAO.categorySubInput(vo);
	}
	
	@Override
	public void delCategoryMain(String categoryMainCode) {
		dbShopDAO.delCategoryMain(categoryMainCode);
	}
	
	@Override
	public void delCategoryMiddle(String categoryMiddleCode) {
		dbShopDAO.delCategoryMiddle(categoryMiddleCode);
	}

	@Override
	public void delCategorySub(String categorySubCode) {
		dbShopDAO.delCategorySub(categorySubCode);
	}

	@Override
	public List<CategorySubVO> getCategoryMiddleName(String categoryMainCode) {
		return dbShopDAO.getCategoryMiddleName(categoryMainCode);
	}

	@Override
	public List<CategorySubVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode) {
		return dbShopDAO.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void imgCheckProductInput(MultipartFile file, DbProductVO vo) {
		// 먼저 기본그림파일은 'dbShop/product'폴더에 업로드 시켜준다.
		try {
			String originalFilename = file.getOriginalFilename();
			if(originalFilename != "" && originalFilename != null) {
				// 상품 메인사진을 업로드처리하기위해 중복파일명처리와 업로드처리
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			  String saveFileName = sdf.format(date) + "_" + originalFilename;
				writeFile(file, saveFileName);	  // 메일 이미지를 서버에 업로드 시켜주는 메소드 호출
				vo.setFName(originalFilename);		// 업로드시 파일명을 fName에 저장
				vo.setFSName(saveFileName);				// 서버에 저장된 파일명을 vo에 set시켜준다.
			}
			else {
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//             0         1         2         3         4         5
		//             012345678901234567890123456789012345678901234567890
		// <img alt="" src="/cjs2108/data/dbShop/211229124318_4.jpg"
		// <img alt="" src="/cjs2108/data/dbShop/product/211229124318_4.jpg"
		
		// ckeditor을 이용해서 담은 상품의 상세설명내역에 그림이 포함되어 있으면 그림을 dbShop/product폴더로 복사작업처리 시켜준다.
		String content = vo.getContent();
		if(content.indexOf("src=\"/") == -1) return;		// content박스의 내용중 그림이 없으면 돌아간다.
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getRealPath("/resources/data/dbShop/");
		
		int position = 26;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			String copyFilePath = "";
			String oriFilePath = uploadPath + imgFile;	// 원본 그림이 들어있는 '경로명+파일명'
			
			copyFilePath = uploadPath + "product/" + imgFile;	// 복사가 될 '경로명+파일명'
			
			fileCopyCheck(oriFilePath, copyFilePath);	// 원본그림이 복사될 위치로 복사작업처리하는 메소드
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
		// 이미지 복사작업이 종료되면 실제로 저장된 'dbShop/product'폴더명을 vo에 set시켜줘야 한다.
		vo.setContent(vo.getContent().replace("/data/dbShop/", "/data/dbShop/product/"));

		// 파일 복사작업이 모두 끝나면 vo에 담긴내용을 상품의 내역을 DB에 저장한다.
		// 먼저 productCode를 만들어주기 위해 지금까지 작업된 dbProduct테이블의 idx필드중 최대값을 읽어온다. 없으면 0으로 처리한다.
		int maxIdx = 0;
		DbProductVO maxVo = dbShopDAO.getProductMaxIdx();
		if(maxVo != null) maxIdx = maxVo.getIdx();
		vo.setProductCode(vo.getCategoryMainCode()+vo.getCategoryMiddleCode()+vo.getCategorySubCode()+maxIdx);
		dbShopDAO.setDbProductInput(vo);
	}
	
  // 실제 파일(dbShop폴더)을 'dbShop/product'폴더로 복사처리하는곳
	private void fileCopyCheck(String oriFilePath, String copyFilePath) {
		File oriFile = new File(oriFilePath);
		File copyFile = new File(copyFilePath);
		
		try {
			FileInputStream  fis = new FileInputStream(oriFile);
			FileOutputStream fos = new FileOutputStream(copyFile);
			
			byte[] buffer = new byte[2048];
			int count = 0;
			while((count = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, count);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 메인 상품 이미지 서버에 저장하기
	private void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		byte[] data = fName.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/product/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		fos.close();
	}

	@Override
	public String[] getProductName() {
		return dbShopDAO.getProductName();
	}

	@Override
	public void setDbOptionInput(DbOptionVO vo) {
		System.out.println("vo : " + vo);
		dbShopDAO.setDbOptionInput(vo);
	}

	@Override
	public List<DbProductVO> getProductInfor(String productName) {
		return dbShopDAO.getProductInfor(productName);
	}

	@Override
	public List<DbProductVO> getSubTitle() {
		return dbShopDAO.getSubTitle();
	}

	@Override
	public List<DbProductVO> getDbShopList(String part) {
		return dbShopDAO.getDbShopList(part);
	}

	@Override
	public DbProductVO getDbShopProduct(int idx) {
		return dbShopDAO.getDbShopProduct(idx);
	}

	@Override
	public List<DbOptionVO> getDbShopOption(int idx) {
		return dbShopDAO.getDbShopOption(idx);
	}

	@Override
	public DbCartListVO dbCartListProductOptionSearch(String productName, String optionName) {
		return dbShopDAO.dbCartListProductOptionSearch(productName, optionName);
	}

	@Override
	public void dbShopCartUpdate(DbCartListVO vo) {
		dbShopDAO.dbShopCartUpdate(vo);
	}

	@Override
	public void dbShopCartInput(DbCartListVO vo) {
		dbShopDAO.dbShopCartInput(vo);
	}

	@Override
	public List<DbCartListVO> getDbCartList(String mid) {
		return dbShopDAO.getDbCartList(mid);
	}

	@Override
	public void dbCartDel(int idx) {
		dbShopDAO.dbCartDel(idx);
	}

	@Override
	public DbCartListVO getCartIdx(String idx) {
		return dbShopDAO.getCartIdx(idx);
	}

	@Override
	public DbOrderVO getOrderMaxIdx() {
		return dbShopDAO.getOrderMaxIdx();
	}

	@Override
	public void setDbOrder(DbOrderVO vo) {
		dbShopDAO.setDbOrder(vo);
	}

	@Override
	public void delDbCartList(int cartIdx) {
		dbShopDAO.delDbCartList(cartIdx);
	}

	@Override
	public void setDbBaesong(DbBaesongVO bVo) {
		dbShopDAO.setDbBaesong(bVo);
	}

	@Override
	public List<DbBaesongVO> getBaesong(String mid) {
		return dbShopDAO.getBaesong(mid);
	}

	@Override
	public int getOrderOIdx(String orderIdx) {
		return dbShopDAO.getOrderOIdx(orderIdx);
	}

	@Override
	public List<DbBaesongVO> getOrderBaesong(String orderIdx) {
		return dbShopDAO.getOrderBaesong(orderIdx);
	}

	@Override
	public int totRecCnt(String mid) {
		return dbShopDAO.totRecCnt(mid);
	}

	@Override
	public List<DbOrderVO> getDbMyOrder(int startIndexNo, int pageSize, String mid) {
		return dbShopDAO.getDbMyOrder(startIndexNo, pageSize, mid);
	}

	@Override
	public List<DbBaesongVO> getOrderStatus(String mid, String orderStatus) {
		return dbShopDAO.getOrderStatus(mid, orderStatus);
	}

	@Override
	public int totRecCntStatus(String mid, String orderStatus) {
		return dbShopDAO.totRecCntStatus(mid, orderStatus);
	}

	@Override
	public void setMemberPointPlus(int point, String mid) {
		dbShopDAO.setMemberPointPlus(point, mid);
	}

	@Override
	public int totRecCntOrderCondition(String mid, int conditionDate) {
		return dbShopDAO.totRecCntOrderCondition(mid, conditionDate);
	}

	@Override
	public List<DbBaesongVO> orderCondition(String mid, int conditionDate) {
		return dbShopDAO.orderCondition(mid, conditionDate);
	}

	@Override
	public List<DbBaesongVO> adminOrderStatus(String startJumun, String endJumun, String orderStatus) {
		return dbShopDAO.adminOrderStatus(startJumun, endJumun, orderStatus);
	}

	@Override
	public void setOrderStatusUpdate(String orderStatus, String orderIdx) {
		switch(orderStatus) {
			case "결제완료": orderStatus = "배송중"; break;
			case "배송중": orderStatus = "배송완료"; break;
			case "배송완료": orderStatus = "구매완료"; break;
			default : orderStatus = "결제완료";
		}
		dbShopDAO.setOrderStatusUpdate(orderStatus, orderIdx);
	}

	@Override
	public int totRecCntAdminStatus(String startJumun, String endJumun, String orderStatus) {
		return dbShopDAO.totRecCntAdminStatus(startJumun, endJumun, orderStatus);
	}

	@Override
	public List<DbBaesongVO> getOrderAdminStatus(String startJumun, String endJumun, String orderStatus) {
		return dbShopDAO.getOrderAdminStatus(startJumun, endJumun, orderStatus);
	}

}