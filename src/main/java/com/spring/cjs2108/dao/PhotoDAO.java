package com.spring.cjs2108.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.PhotoVO;

public interface PhotoDAO {

	public List<PhotoVO> getPhoto();

	public void photoInputOk(@Param("vo") PhotoVO vo);

	public PhotoVO getPhotoContent(@Param("idx") int idx);

	public void setReadNumCount(@Param("idx") int idx);

	public void photoDelete(@Param("idx") int idx);

	public int totRecCnt(@Param("part") String part);

	public List<PhotoVO> photoPartList(@Param("startNo") int startNo, @Param("pageSize") int pageSize, @Param("part") String part);

	public int totSearchRecCnt(@Param("search") String search, @Param("searchString") String searchString);

	public List<PhotoVO> photoSearchList(@Param("startNo") int startNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchString") String searchString);

}
