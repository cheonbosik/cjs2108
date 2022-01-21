package com.spring.cjs2108.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.vo.AddressVO;
import com.spring.cjs2108.vo.AreaVO;
import com.spring.cjs2108.vo.ThumbnailVO;

public interface Study2Service {

	public ThumbnailVO thumbnailCreate(MultipartFile file);

	public String[] getAddress1();

	public List<AreaVO> getAddress2(String address1);

	public AreaVO getAddressSearch(String address1, String address2);

	public AddressVO getAddressName(String address);

	public void setAddressName(AddressVO vo);

	public List<AddressVO> getAddressNameList();

	public void getAddressNameDelete(String address);
}
