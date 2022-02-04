package com.spring.cjs2108.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2108.dao.NotifyDAO;
import com.spring.cjs2108.vo.NotifyVO;

@Service
public class NotifyServiceImp implements NotifyService {
	@Autowired
	NotifyDAO notifyDAO;

	@Override
	public void nInput(NotifyVO vo) {
		notifyDAO.nInput(vo);
	}

	@Override
	public List<NotifyVO> getNotifyList() {
		return notifyDAO.getNotifyList();
	}

	@Override
	public void setDelete(int idx) {
		notifyDAO.setDelete(idx);
	}

	@Override
	public NotifyVO getNUpdate(int idx) {
		return notifyDAO.getNUpdate(idx);
	}

	@Override
	public void setNUpdateOk(NotifyVO vo) {
		notifyDAO.setNUpdateOk(vo);
	}

	@Override
	public int setpopupCheckUpdate(int idx, String popupSw) {
		notifyDAO.setpopupCheckUpdate(idx, popupSw);
		return 1;
	}

	@Override
	public List<NotifyVO> getNotifyPopup() {
		return notifyDAO.getNotifyPopup();
	}
	
}
