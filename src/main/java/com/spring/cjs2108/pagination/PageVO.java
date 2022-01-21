package com.spring.cjs2108.pagination;

import lombok.Data;

@Data
public class PageVO {
	private int pag;
	private int pageSize;
	private int blockSize;
	private int totRecCnt;
	private int totPage;
	private int startNo;
	private int curScrStrarNo;
	
	private String part;
}
