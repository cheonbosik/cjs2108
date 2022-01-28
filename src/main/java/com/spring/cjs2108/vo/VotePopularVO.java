package com.spring.cjs2108.vo;

import lombok.Data;

@Data
public class VotePopularVO {
	private int idx;
	private String part;
	private String title;
	private String subTitle;
	private String realImg;
	private String thumbImg;
	private String progress;
	
	private int voteCnt;  // 투표에 참여한 사람수
}
