package com.spring.cjs2108.vo;

public class NotifyVO {
	private int idx;
	private String name;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private String popupSw;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPopupSw() {
		return popupSw;
	}
	public void setPopupSw(String popupSw) {
		this.popupSw = popupSw;
	}
	@Override
	public String toString() {
		return "NotifyVo [idx=" + idx + ", name=" + name + ", title=" + title + ", content=" + content + ", startDate="
				+ startDate + ", endDate=" + endDate + ", popupSw=" + popupSw + "]";
	}
}
