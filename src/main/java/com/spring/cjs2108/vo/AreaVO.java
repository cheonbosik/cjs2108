package com.spring.cjs2108.vo;

public class AreaVO {
	private String address1;
	private String address2;
	private double latitude;
	private double longitude;
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "AreaVO [address1=" + address1 + ", address2=" + address2 + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
}
