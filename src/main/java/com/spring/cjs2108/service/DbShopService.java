package com.spring.cjs2108.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.vo.CategorySubVO;
import com.spring.cjs2108.vo.DbBaesongVO;
import com.spring.cjs2108.vo.DbCartListVO;
import com.spring.cjs2108.vo.DbOptionVO;
import com.spring.cjs2108.vo.DbOrderVO;
import com.spring.cjs2108.vo.DbProductVO;

public interface DbShopService {

	public List<CategorySubVO> getCategoryMain();

	public List<CategorySubVO> getCategoryMiddle();

	public List<CategorySubVO> getCategorySub();
	
	public CategorySubVO getCategoryMainOne(String categoryMainCode, String categoryMainName);

	public List<CategorySubVO> getCategoryMiddleOne(CategorySubVO vo);

	public List<CategorySubVO> getCategorySubOne(CategorySubVO vo);
	
	public List<DbProductVO> getDbProductOne(String categorySubCode);
	
	public void categoryMainInput(CategorySubVO vo);
	
	public void categoryMiddleInput(CategorySubVO vo);

	public void categorySubInput(CategorySubVO vo);
	
	public void delCategoryMain(String categoryMainCode);
	
	public void delCategoryMiddle(String categoryMiddleCode);

	public void delCategorySub(String categorySubCode);

	public List<CategorySubVO> getCategoryMiddleName(String categoryMainCode);

	public List<CategorySubVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode);

	public void imgCheckProductInput(MultipartFile file, DbProductVO vo);

	public String[] getProductName();

	public void setDbOptionInput(DbOptionVO vo);

	public List<DbProductVO> getProductInfor(String productName);

	public List<DbProductVO> getSubTitle();

	public List<DbProductVO> getDbShopList(String part);

	public DbProductVO getDbShopProduct(int idx);

	public List<DbOptionVO> getDbShopOption(int idx);

	public DbCartListVO dbCartListProductOptionSearch(String productName, String optionName, String mid);

	public void dbShopCartUpdate(DbCartListVO vo);

	public void dbShopCartInput(DbCartListVO vo);

	public List<DbCartListVO> getDbCartList(String mid);

	public void dbCartDel(int idx);

	public DbCartListVO getCartIdx(String idx);

	public DbOrderVO getOrderMaxIdx();

	public void setDbOrder(DbOrderVO vo);

	public void delDbCartList(int cartIdx);

	public void setDbBaesong(DbBaesongVO bVo);

	public List<DbBaesongVO> getBaesong(String mid);

	public int getOrderOIdx(String orderIdx);

	public List<DbBaesongVO> getOrderBaesong(String orderIdx);

	public int totRecCnt(String mid);

	public List<DbOrderVO> getDbMyOrder(int startIndexNo, int pageSize, String mid);

	public List<DbBaesongVO> getOrderStatus(String mid, String orderStatus);

	public int totRecCntStatus(String mid, String orderStatus);

	public void setMemberPointPlus(int point, String mid);

	public int totRecCntOrderCondition(String mid, int conditionDate);

	public List<DbBaesongVO> orderCondition(String mid, int conditionDate);

	public List<DbBaesongVO> adminOrderStatus(String startJumun, String endJumun, String orderStatus);

	public void setOrderStatusUpdate(String orderStatus, String orderIdx);

	public int totRecCntAdminStatus(String startJumun, String endJumun, String orderStatus);

	public List<DbBaesongVO> getOrderAdminStatus(String startJumun, String endJumun, String orderStatus);

}
