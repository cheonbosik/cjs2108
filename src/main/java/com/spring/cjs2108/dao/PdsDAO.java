package com.spring.cjs2108.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.PdsVO;

public interface PdsDAO {

	public int totRecCnt(@Param("part") String part);

	public List<PdsVO> getPdsList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part);

	public void pdsInput(@Param("vo") PdsVO vo);

	public void setDownCheck(@Param("idx") int idx);

	public PdsVO getPdsContent(@Param("idx") int idx);

	public void pdsDelete(@Param("idx") int idx);

}
