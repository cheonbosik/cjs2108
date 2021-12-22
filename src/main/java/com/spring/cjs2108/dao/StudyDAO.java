package com.spring.cjs2108.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.Goods1VO;
import com.spring.cjs2108.vo.Goods2VO;
import com.spring.cjs2108.vo.Goods3VO;

public interface StudyDAO {

	public List<Goods1VO> getProduct1();

	public ArrayList<Goods2VO> getProduct2(@Param("product1") String product1);

	public ArrayList<Goods3VO> getProduct3(@Param("product1") String product1, @Param("product2") String product2);

}
