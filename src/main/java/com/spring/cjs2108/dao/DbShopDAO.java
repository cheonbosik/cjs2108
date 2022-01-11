package com.spring.cjs2108.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.CategorySubVO;
import com.spring.cjs2108.vo.DbBaesongVO;
import com.spring.cjs2108.vo.DbCartListVO;
import com.spring.cjs2108.vo.DbOptionVO;
import com.spring.cjs2108.vo.DbOrderVO;
import com.spring.cjs2108.vo.DbProductVO;

public interface DbShopDAO {

	public List<CategorySubVO> getCategoryMain();

	public List<CategorySubVO> getCategoryMiddle();

	public List<CategorySubVO> getCategorySub();

	public CategorySubVO getCategoryMainOne(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMainName") String categoryMainName);

	public List<CategorySubVO> getCategoryMiddleOne(@Param("vo") CategorySubVO vo);

	public List<CategorySubVO> getCategorySubOne(@Param("vo") CategorySubVO vo);
	
	public List<DbProductVO> getDbProductOne(@Param("categorySubCode") String categorySubCode);
	
	public void categoryMainInput(@Param("vo") CategorySubVO vo);
	
	public void categoryMiddleInput(@Param("vo") CategorySubVO vo);

	public void categorySubInput(@Param("vo") CategorySubVO vo);
	
	public void delCategoryMain(@Param("categoryMainCode") String categoryMainCode);
	
	public void delCategoryMiddle(@Param("categoryMiddleCode") String categoryMiddleCode);

	public void delCategorySub(@Param("categorySubCode") String categorySubCode);

	public List<CategorySubVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	public List<CategorySubVO> getCategorySubName(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode);

	public void setDbProductInput(@Param("vo") DbProductVO vo);

	public DbProductVO getProductMaxIdx();

	public String[] getProductName();

	public void setDbOptionInput(@Param("vo") DbOptionVO vo);

	public List<DbProductVO> getProductInfor(@Param("productName") String productName);

	public List<DbProductVO> getSubTitle();

	public List<DbProductVO> getDbShopList(@Param("part") String part);

	public DbProductVO getDbShopProduct(@Param("idx") int idx);

	public List<DbOptionVO> getDbShopOption(@Param("idx") int idx);

	public DbCartListVO dbCartListProductOptionSearch(@Param("productName") String productName, @Param("optionName") String optionName);

	public void dbShopCartUpdate(@Param("vo") DbCartListVO vo);

	public void dbShopCartInput(@Param("vo") DbCartListVO vo);

	public List<DbCartListVO> getDbCartList(@Param("mid") String mid);

	public void dbCartDel(@Param("idx") int idx);

	public DbCartListVO getCartIdx(@Param("idx") String idx);

	public DbOrderVO getOrderMaxIdx();

	public void setDbOrder(@Param("vo") DbOrderVO vo);

	public void delDbCartList(@Param("cartIdx") int cartIdx);

	public void setDbBaesong(@Param("bVo") DbBaesongVO bVo);

	public List<DbBaesongVO> getBaesong(@Param("mid") String mid);

	public int getOrderOIdx(@Param("orderIdx") String orderIdx);

	public List<DbBaesongVO> getOrderBaesong(@Param("orderIdx") String orderIdx);

	public int totRecCnt(@Param("mid") String mid);

	public List<DbOrderVO> getDbMyOrder(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("mid") String mid);

	public List<DbBaesongVO> getOrderStatus(@Param("mid") String mid, @Param("orderStatus") String orderStatus);

	public int totRecCntStatus(@Param("mid") String mid, @Param("orderStatus") String orderStatus);

	public void setMemberPointPlus(@Param("point") int point, @Param("mid") String mid);

	public int totRecCntOrderCondition(@Param("mid") String mid, @Param("conditionDate") int conditionDate);

	public List<DbBaesongVO> orderCondition(@Param("mid") String mid, @Param("conditionDate") int conditionDate);

	public List<DbBaesongVO> adminOrderStatus(@Param("startJumun") String startJumun, @Param("endJumun") String endJumun, @Param("orderStatus") String orderStatus);

	public void setOrderStatusUpdate(@Param("orderStatus") String orderStatus, @Param("orderIdx") String orderIdx);

	public int totRecCntAdminStatus(@Param("startJumun") String startJumun, @Param("endJumun") String endJumun, @Param("orderStatus") String orderStatus);

	public List<DbBaesongVO> getOrderAdminStatus(@Param("startJumun") String startJumun, @Param("endJumun") String endJumun, @Param("orderStatus") String orderStatus);

}
