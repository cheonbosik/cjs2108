package com.spring.cjs2108.service;

import java.util.List;

import com.spring.cjs2108.vo.GuestVO;

public interface GuestService {

	public List<GuestVO> getGuestList(int startIndexNo, int pageSize);

	public void setGuestInput(GuestVO vo);

	public int totRecCnt();

	public void setGuestDelete(int idx);

}
