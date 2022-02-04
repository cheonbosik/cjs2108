package com.spring.cjs2108.service;

import java.util.List;

import com.spring.cjs2108.vo.NotifyVO;

public interface NotifyService {

	public void nInput(NotifyVO vo);

	public List<NotifyVO> getNotifyList();

	public void setDelete(int idx);

	public NotifyVO getNUpdate(int idx);

	public void setNUpdateOk(NotifyVO vo);

	public int setpopupCheckUpdate(int idx, String popupSw);

	public List<NotifyVO> getNotifyPopup();

}
