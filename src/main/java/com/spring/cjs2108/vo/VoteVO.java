package com.spring.cjs2108.vo;

import lombok.Data;

@Data
public class VoteVO {
  private int idx;
  private int personInforIdx;
  private int popularIdx;
  private int voteNum;
  
  private int voteSum;		// 각 문항별 투표결과 누적하기
  private String title;		// 각 문항별 주제
  private String subTitle;// 각 문항에 따른 각각 항목의 그림 설명
  private String gender;  // donut차트를 그리기위한 성별저장소.
  private String age;			// 연령대를 저장하기위한 변수
  private String area;		// 지역을 담는 변수
  private String job;			// 직업을 담는 변수
}
