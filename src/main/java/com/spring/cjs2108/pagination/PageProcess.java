package com.spring.cjs2108.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2108.dao.PhotoDAO;
import com.spring.cjs2108.dao.VoteDAO;

@Service
public class PageProcess {
	@Autowired
	PhotoDAO photoDAO;
	
	@Autowired
	VoteDAO voteDAO;
	
  //인자 : 1.page번호, 2.page크기, 3.소속(예:게시판-board, 멀티자료실:pds, 포토갤러리:photo), 4.part(분류)-예:'전체'/'여행', 5.검색어로검색시
	public PageVO pagination(int pag, int pageSize, String partName, String partValue, String searchString) { // 매개변수 partValue는 분류에 따른 처리를 위함이다.
		int blockSize = 3;
		int totRecCnt = 0;
		
		PageVO pageVO = new PageVO();
		
		if(partName.equals("photo")) {
			if(searchString.equals("")) {
				String part = partValue;
				totRecCnt = photoDAO.totRecCnt(part);
			}
			else {
				String search = partValue;
				totRecCnt = photoDAO.totSearchRecCnt(search, searchString);
			}
		}
		else if(partName.equals("vote")) {
			String part = partValue;
			totRecCnt = voteDAO.totRecCnt(part);
		}
		
		int totPage = (totRecCnt % pageSize) == 0 ? totRecCnt/pageSize : (int)(totRecCnt/pageSize) + 1;  //총 페이지수 구하기
		int startNo = (pag - 1) * pageSize;  // 한페이지에 보여줄 시작 인덱스번호를 구한다.
		int curScrStrarNo = totRecCnt - startNo;  // 해당페이지의 시작번호 구하기.
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setBlockSize(blockSize);
		pageVO.setTotRecCnt(totRecCnt);
		pageVO.setTotPage(totPage);
		pageVO.setStartNo(startNo);
		pageVO.setCurScrStrarNo(curScrStrarNo);
		pageVO.setPart(partValue);
		
		return pageVO;
	}
}
