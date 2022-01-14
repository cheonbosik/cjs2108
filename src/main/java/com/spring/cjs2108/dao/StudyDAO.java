package com.spring.cjs2108.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.Goods3VO;
import com.spring.cjs2108.vo.MainImageVO;

public interface StudyDAO {

	public List<Goods3VO> getProduct1();

	public ArrayList<Goods3VO> getProduct2(@Param("product1") String product1);

	public ArrayList<Goods3VO> getProduct3(@Param("product1") String product1, @Param("product2") String product2);

	public void imgCheckInput(@Param("vo") MainImageVO vo);

	public MainImageVO getMainImageIdx();

	public List<MainImageVO> getMainImageList(@Param("idx") int idx);

	public List<MainImageVO> getMainImagePart();

	public void mainImageDelete(@Param("idx") int idx);
}
