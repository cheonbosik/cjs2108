
package com.spring.cjs2108.service;

import java.util.List;

import com.spring.cjs2108.vo.ScheduleVO;

public interface ScheduleService {

	public void getSchedule();

	public List<ScheduleVO> getScMenu(String mid, String ymd);

	public void scheduleInputOk(ScheduleVO vo);

	public void scheduleDeleteOk(int idx);

	public ScheduleVO getScheduleSearch(int idx);

}
