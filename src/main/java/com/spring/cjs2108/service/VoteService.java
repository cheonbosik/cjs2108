package com.spring.cjs2108.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.cjs2108.vo.MemberVO;
import com.spring.cjs2108.vo.VotePersonInforVO;
import com.spring.cjs2108.vo.VotePopularVO;
import com.spring.cjs2108.vo.VoteVO;

public interface VoteService {
	public void aVotePopularInputOk(MultipartHttpServletRequest file, VotePopularVO vo);
	
	public int totVotePopularRecCnt();

	public List<VotePopularVO> aVotePopularList(int pageSize, int startNo, String part);

	public VotePopularVO getVotePopularView(int idx);

	public void voteSelectInsert(VoteVO vo);

	public int votePopularDelete(int idx, String realImg, String thumbImg);

	public MemberVO memberSearch(String mid); 

	public void aVoteInforInsert(VotePersonInforVO personVo);

	public int getCountVotePersonInfor();
	
	public int getMaxVotePersonInfor();
	
	public int getCountVoteIdx();
	
	public int getMaxVoteIdx();

	public List<VoteVO> voteResultView(int idx);

	public int getVoteTotCnt(int idx);

	public void setProgress(int idx, String progress);

	public List<VoteVO> voteGenderResultView(int idx);

	public List<VoteVO> voteAgeResultView(int idx);

	public String getVoteSubTitle(int idx);

	public List<VoteVO> voteAreaResultView(int idx, int voteNum);

	public List<VoteVO> voteJobResultView(int idx, int voteNum);

}
