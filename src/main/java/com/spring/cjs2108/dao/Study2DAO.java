package com.spring.cjs2108.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2108.vo.AddressVO;
import com.spring.cjs2108.vo.AreaVO;

public interface Study2DAO {
	public String[] getAddress1();

	public List<AreaVO> getAddress2(@Param("address1") String address1);

	public AreaVO getAddressSearch(@Param("address1") String address1, @Param("address2") String address2);

	public AddressVO getAddressName(@Param("address") String address);

	public void setAddressName(@Param("vo") AddressVO vo);

	public List<AddressVO> getAddressNameList();

	public void getAddressNameDelete(@Param("address") String address);
}
