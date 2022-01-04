package com.spring.cjs2108.vo;

import lombok.Data;

@Data
public class PdsVO {
	private int idx;
	private String mid;
	private String nickName;
	private String fName;
	private String fSName;
	private int fSize;
	private String title;
	private String part;
	private String pwd;
	private String fDate;
	private int downNum;
	private String openSw;
	private String content;
	
	// 멀티파일에 대한 정보를 저장하기 위한 필드 2개
	private String fNames;
	private String rfNames;
}
