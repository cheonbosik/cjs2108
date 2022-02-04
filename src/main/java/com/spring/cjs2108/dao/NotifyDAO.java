package com.spring.cjs2108.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.NotifyVO;

public interface NotifyDAO {

	public void nInput(@Param("vo") NotifyVO vo);

	public List<NotifyVO> getNotifyList();

	public void setDelete(@Param("idx") int idx);

	public NotifyVO getNUpdate(@Param("idx") int idx);

	public void setNUpdateOk(@Param("vo") NotifyVO vo);

	public void setpopupCheckUpdate(@Param("idx") int idx, @Param("popupSw") String popupSw);

	public List<NotifyVO> getNotifyPopup();

}
