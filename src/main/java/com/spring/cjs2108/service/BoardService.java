package com.spring.cjs2108.service;

import java.util.List;

import com.spring.cjs2108.vo.BoardReplyVO;
import com.spring.cjs2108.vo.BoardVO;

public interface BoardService {

	public int totRecCnt();

	public List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	public void setBoardInput(BoardVO vo);

	public BoardVO getBoardContent(int idx);

	public void addReadNum(int idx);

	public void addGood(int idx, int flag);

	public List<BoardVO> getPreNext(int idx);

	public void imgCheck(String content);

	public void setBoardUpdate(BoardVO vo);

	public void imgCheckUpdate(String content);

	public void imgDelete(String content);

	public void setBoardDelete(int idx);

	public String maxLevelOrder(int boardIdx);

	public void setReplyInsert(BoardReplyVO rVo);

	public List<BoardReplyVO> getBoardReply(int idx);

	public void levelOrderPlusUpdate(BoardReplyVO rVo);

	public void setReplyInsert2(BoardReplyVO rVo);

	public void setReplyDelete(int replyIdx);

}
