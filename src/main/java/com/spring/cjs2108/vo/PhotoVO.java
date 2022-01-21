package com.spring.cjs2108.vo;

import lombok.Data;

@Data
public class PhotoVO {
	private int idx;
	private String name;
	private String part;
	private String title;
	private String content;
	private String thumbnail;
	private String firstFile;
	private String wDate;
	private String hostIp;
	private int readNum;
	
	private String diffTime;  //  24시간에 따라서 '날짜/시간'으로 보여주기위한 필드
}