package com.spring.cjs2108.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.MemberVO;
import com.spring.cjs2108.vo.VotePersonInforVO;
import com.spring.cjs2108.vo.VotePopularVO;
import com.spring.cjs2108.vo.VoteVO;

public interface VoteDAO {
	public void aVotePopularInputOk(@Param("vo") VotePopularVO vo);
	
	public int totVotePopularRecCnt();
	
	public int totRecCnt(@Param("part") String part);

	public List<VotePopularVO> aVotePopularList(@Param("startNo") int startNo, @Param("pageSize") int pageSize, @Param("part") String part);

	public VotePopularVO getVotePopularView(@Param("idx") int idx);

	public int votePopularDelete(@Param("idx") int idx);

	public MemberVO memberSearch(@Param("mid") String mid); 

	public void aVoteInforInsert(@Param("personVO") VotePersonInforVO personVO);

	public int getCountVotePersonInfor();
	
	public int getMaxVotePersonInfor();
	
	public int getMaxVotePopularIdx();
	
	public int getCountVoteIdx();
	
	public int getMaxVoteIdx();

	public void voteSelectInsert(@Param("vo") VoteVO vo);

	public List<VoteVO> voteResultView(@Param("idx") int idx);

	public int getVoteTotCnt(@Param("idx") int idx);

	public void setProgress(@Param("idx") int idx, @Param("progress") String progress);

	public List<VoteVO> voteGenderResultView(@Param("idx") int idx);

	public List<VoteVO> voteAgeResultView(@Param("idx") int idx);

	public String getVoteSubTitle(@Param("idx") int idx);

	public List<VoteVO> voteAreaResultView(@Param("idx") int idx, @Param("voteNum") int voteNum);

	public List<VoteVO> voteJobResultView(@Param("idx") int idx, @Param("voteNum") int voteNum);

}
