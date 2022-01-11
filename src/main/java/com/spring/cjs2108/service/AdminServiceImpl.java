package com.spring.cjs2108.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2108.dao.AdminDAO;
import com.spring.cjs2108.vo.MemberVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminDAO adminDAO;

	@Override
	public int totRecCnt(int level) {
		return adminDAO.totRecCnt(level);
	}
	
	@Override
	public int totRecCntMid(String mid) {
		return adminDAO.totRecCntMid(mid);
	}
	
	@Override
	public ArrayList<MemberVO> getMemberList(int startIndexNo, int pageSize, int level) {
		return adminDAO.getMemberList(startIndexNo, pageSize, level);
	}

	@Override
	public ArrayList<MemberVO> getMemberListMid(int startIndexNo, int pageSize, String mid) {
		return adminDAO.getMemberListMid(startIndexNo, pageSize, mid);
	}
	
	@Override
	public void setLevelUpdate(int idx, int level) {
		adminDAO.setLevelUpdate(idx, level);
	}

	@Override
	public int getNewMember() {
		return adminDAO.getNewMember();
	}

	@Override
	public int imgDelete(String uploadPath) {
		File path = new File(uploadPath);
		// 파일객체를 통해서 uploadPath경로안의 모든 파일의 정보를 담아와서 배열로 저장한다.
		File[] fileList = path.listFiles();
		int fileCnt = fileList.length - 1;
		
		for(int i=0; i<fileCnt; i++) {
			fileList[i].delete();
		}
		return fileCnt;
	}

}
