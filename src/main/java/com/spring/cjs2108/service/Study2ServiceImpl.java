package com.spring.cjs2108.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.dao.Study2DAO;
import com.spring.cjs2108.vo.AddressVO;
import com.spring.cjs2108.vo.AreaVO;
import com.spring.cjs2108.vo.ThumbnailVO;

@Service
public class Study2ServiceImpl implements Study2Service {
	@Autowired
	Study2DAO study2DAO;

	@Override
	public ThumbnailVO thumbnailCreate(MultipartFile file) {
		ThumbnailVO vo = new ThumbnailVO();
	  try {
	    String oFileName = file.getOriginalFilename();
	    String fileExt = oFileName.substring(oFileName.lastIndexOf(".")+1);

	    // 업로드된 파일의 이름 중복방지를 위한 처리
	    UUID uid = UUID.randomUUID();
	    String saveFileName = uid + "_" + oFileName;
	    oImageFileWrite(file, saveFileName);  // 원본 이미지 저장하기 메소드 생성호출

	    // 아래로, 썸네임 이미지 만들어서 저장하기
	    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	    String imsiUploadPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
	    String uploadPath = imsiUploadPath + saveFileName;

	    BufferedImage srcImg = ImageIO.read(new File(uploadPath));

	    int ow = srcImg.getWidth();   // 서버에 저장된 원본이미지의 폭을 가져온다.
	    int oh = srcImg.getHeight();  // 서버에 저장된 원본이미지의 높이를 가져온다.

	    // 썸네일 이미지로 저장할 그림파일의 폭과 높이를 지정한다.(4:3 비율 -> 200px(폭) / 4 * 3 = 150px(높이))
	    int tw = 200, th = 150;

	    // 원본그림을 재지정된(w:200, h:150) 그림으로 변경처리
	    int rw = ow;                              // rw =2000,
	    int rh = (ow * th) / tw;

	    // 재계산된 높이가 원본보다 높다면 잘랐을때 위쪽이 공백으로 채워진다. 따라서 폭과 높이를 서로 바꿔서 다시 계산해 준다.
	    if(rh > oh) {
	      rh = oh;
	      rw = (oh * tw) / th;
	    }

	    // 재계산이 완료되면 계산된 크기만큼 원본 이미지를 잘라서 저장한다.
	    Scalr.crop(srcImg, (ow-rw)/2, (oh-rh)/2, rw, rh);

	    // 변경된 사이즈로 정보를 다시 읽어온다.
	    BufferedImage resizeImg = Scalr.resize(srcImg, tw, th);

	    // 새롭게 재 계산되어 처리(잘라진)된 썸네일을 화일명을 붙여서 저장한다.
	    String imsiFileName = imsiUploadPath + "s_" + saveFileName;
	    String thumbnailFileName = "s_" + saveFileName + "." + fileExt;

	    File tFileName = new File(imsiFileName);
	    ImageIO.write(resizeImg, fileExt, tFileName);  // 썸네일 이미지 저장처리

	    // 이미지가 정상처리되어 저장된후 DB작업을 위해 VO에 담는다.
	    vo.setRes(1);
	    vo.setOFileName(saveFileName);
	    vo.setTFileName(thumbnailFileName);
	  } catch (IOException e) {
	    vo.setRes(0);
	    e.printStackTrace();
	  }
	  // DB작업처리 할 것 있으면 이곳에서 처리한다.

	  return vo;
	}

  // 원본이미지 저장메소드
	private void oImageFileWrite(MultipartFile mFile, String saveFileName) throws IOException {
	  byte[] date = mFile.getBytes();

	  HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	  String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");

	  FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
	  fos.write(date);

	  fos.close();
	}
	
	@Override
	public String[] getAddress1() {
		return study2DAO.getAddress1();
	}

	@Override
	public List<AreaVO> getAddress2(String address1) {
		return study2DAO.getAddress2(address1);
	}

	@Override
	public AreaVO getAddressSearch(String address1, String address2) {
		return study2DAO.getAddressSearch(address1, address2);
	}

	@Override
	public AddressVO getAddressName(String address) {
		return study2DAO.getAddressName(address);
	}

	@Override
	public void setAddressName(AddressVO vo) {
		study2DAO.setAddressName(vo);
	}

	@Override
	public List<AddressVO> getAddressNameList() {
		return study2DAO.getAddressNameList();
	}

	@Override
	public void getAddressNameDelete(String address) {
		study2DAO.getAddressNameDelete(address);
	}
	
}
