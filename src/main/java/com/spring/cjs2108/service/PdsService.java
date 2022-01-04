package com.spring.cjs2108.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.cjs2108.vo.PdsVO;

public interface PdsService {

	public int totRecCnt(String part);
	
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part);

	public void pdsInput(MultipartHttpServletRequest file, PdsVO vo);

	public void setDownCheck(int idx);

	public PdsVO getPdsContent(int idx);

	public void pdsDelete(PdsVO vo);

}
