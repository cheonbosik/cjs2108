package com.spring.cjs2108.service;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.vo.ThumbnailVO;

public interface Study2Service {

	public ThumbnailVO thumbnailCreate(MultipartFile file);

}
