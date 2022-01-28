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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.cjs2108.dao.VoteDAO;
import com.spring.cjs2108.vo.MemberVO;
import com.spring.cjs2108.vo.VotePersonInforVO;
import com.spring.cjs2108.vo.VotePopularVO;
import com.spring.cjs2108.vo.VoteVO;


@Service
@RequestMapping("/vote")
public class VoteServiceImpl implements VoteService {

	@Autowired
	VoteDAO voteDAO;
	
	@Override
	public void aVotePopularInputOk(MultipartHttpServletRequest mfile, VotePopularVO vo) {
		try {
	  	// 여러개의 파일 정보가 넘어오기에 List객체를 이용해서 담아준다.
	  	List<MultipartFile> fileList = mfile.getFiles("file");
	  	String saveFileNames = ""; // 저장파일 누적
	  	String ThumbFileNames = "";  // 썸네일파일 누적
	  	
	  	for(MultipartFile file : fileList) {
		  	String oFileName = file.getOriginalFilename(); // 윈도우에서 사용자 업로드 시킨 파일명
		  	
		  	// 앞전에 사용했던 날짜형식의 고유번호방식을 사용하지 않고, 아래는 uuid(네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약)을 사용해 본다. - 128bit(32개의 16진수)를 랜덤하게 발생시켜서 행성해준다.
		  	UUID uid = UUID.randomUUID();  // UUID객체를 통한 고유번호글 구함.
		  	String saveFileName = uid + "_" + oFileName;  // 생성된 uid에 원본 파일명을 결합한다.
		  	
				writeFile(file, saveFileName);  // uid와 결합한 파일을, 실제 서버에 저장시켜주는 메소드 호출
				saveFileNames += saveFileName + "/";  // 서버에 저장된 파일을 누적처리하고 있다.
				
				// 아래는 썸네일 이미지 처리
				String thumbName = "s_" + saveFileName;// 썸네일 이미지는, 서버에 저장된 실제파일명앞에 's_'을 붙였다.
				makeThumbnail(saveFileName, thumbName);  // '썸네일이미지'도 용량을 줄여서 저장하기위해, uid와 결합한 '서버에 저장된 실제파일명'과 '썸네일파일명'을 넘겨준다.  
				ThumbFileNames += thumbName + "/";  // 썸네일 이름을 누적한다.
	  	}
	  	
	  	vo.setSubTitle(vo.getSubTitle().replace(",", "/")); //subTitle의 구분자를 '/'로 변경처리
	  	
			//서버에 저장된 파일의 정보를 vo에 담아서 DB에 저장시킨다.
			vo.setRealImg(saveFileNames);
			vo.setThumbImg(ThumbFileNames);
			
			int votePopularIdx = voteDAO.getMaxVotePopularIdx()==0 ? 1 : voteDAO.getMaxVotePopularIdx() + 1;
			vo.setIdx(votePopularIdx);
			//System.out.println("vo : " + vo);
			
			voteDAO.aVotePopularInputOk(vo);  // 정상적일때 서버에 파일이 업로드 된후 DB에 저장하기위한 메소드
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** * 썸네일을 생성한다. * 250 x 150 크기의 썸네일을 만든다. */
	private void makeThumbnail(String saveFileName, String thumbName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

		String uploadPath = request.getSession().getServletContext().getRealPath("/")+"resources\\data\\vote\\";  // 저장경로 구하기
		String fileExt = saveFileName.substring(saveFileName.lastIndexOf(".")+1);   // 확장자 구하기
		
		// 저장된 원본파일로부터 BufferedImage 객체를 생성한다. 
		String filePath = uploadPath + saveFileName;
		BufferedImage srcImg = ImageIO.read(new File(filePath));

		// 서버에 저장되어 있는 원본 이미지의 너비와 높이를 구한다.
		int ow = srcImg.getWidth();
		int oh = srcImg.getHeight();
		
		// 썸네일의 너비와 높이를 지정한다.(사용자가 원하는 크기로 지정)
		int dw = 150, dh = 100;
		
		// 원본 너비를 기준으로 하여 썸네일의 비율로 새롭게 저장될, 썸네일의 높이를 계산한다.
		int rw = ow;              // 사용자가 지정한 썸네일 폭을 결정
		int rh = (ow * dh) / dw;  // 사용자가 지정한 썸네일 폭을 기준으로 높이를 계산
		
		// 계산된 높이가 원본보다 높다면 crop이 안되므로, 원본 높이를 기준으로 썸네일의 비율로 너비를 계산한다.
		if(rh > oh) {
			rh = oh;             // 사용자가 지정한 썸네일 높이로 결정
			rw = (oh * dw) / dh; // 사용자가 지정한 썸네일 높이를 기준으로 다시 폭을 계산한다.
		}
		
		// 계산된 크기로 원본이미지를 가운데에서 crop 한다.(Scalr라이브러리 이용..)
		BufferedImage cropImg = Scalr.crop(srcImg, (ow-rw)/2, (oh-rh)/2, rw, rh);
		
		// crop된 이미지로 썸네일을 생성한다.
		BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
		
		// 썸네일을 저장한다. 앞에서 넘어온 thumbName와 결합하여 저장한다.
		//thumbName = uploadPath + thumbName + saveFileName;
		thumbName = uploadPath + thumbName;
		File thumbFile = new File(thumbName);
		
		//ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
		ImageIO.write(destImg, fileExt, thumbFile); // 이미지파일 저장하는 클래스(ImageIO) : (변형된크기의이미지파일,확장자,파일생성객체)
	}
	
  //실제 파일이 서버에 저장되는 메소드
	private void writeFile(MultipartFile file, String saveFileName) throws IOException {
		byte[] data = file.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/")+"resources\\data\\vote\\";
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);  // 서버에 파일이 저장된다.
		
		fos.close();
	}
	
	@Override
	public int totVotePopularRecCnt() {
		return voteDAO.totVotePopularRecCnt();
	}

	@Override
	public List<VotePopularVO> aVotePopularList(int startNo, int pageSize, String part) {
		return voteDAO.aVotePopularList(startNo, pageSize, part);
	}

	@Override
	public VotePopularVO getVotePopularView(int idx) {
		return voteDAO.getVotePopularView(idx);
	}

	@Override
	public int votePopularDelete(int idx, String realImg, String thumbImg) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest(); // AOP에서 request객체를 사용(선언)하기위한 방법
		
		String directory = request.getSession().getServletContext().getRealPath("/resources/data/vote/");
		
  	//System.out.println("directory+realImg : " + directory+realImg);
  	//System.out.println("directory+thumbImg : " + directory+thumbImg);
  	
  	String[] realImgs = realImg.split("/");
  	String[] thumbImgs = thumbImg.split("/");
  	
  	for(String delRealImg : realImgs) {
    	new File(directory + delRealImg).delete();  // 원본파일삭제
  	}
  	for(String delThumbImg : thumbImgs) {
  	  new File(directory + delThumbImg).delete();  // 썸네일파일삭제
  	}
  	
		return voteDAO.votePopularDelete(idx);
	}
	
	@Override
	public MemberVO memberSearch(String mid) {
		return voteDAO.memberSearch(mid);
	}
	
	@Override
	public void aVoteInforInsert(VotePersonInforVO personVO) {
		voteDAO.aVoteInforInsert(personVO);
	}

	@Override
	public int getCountVotePersonInfor() {
		return voteDAO.getCountVotePersonInfor();
	}
	
	@Override
	public int getMaxVotePersonInfor() {
		return voteDAO.getMaxVotePersonInfor();
	}
	
	@Override
	public int getCountVoteIdx() {
		return voteDAO.getCountVoteIdx();
	}
	
	@Override
	public int getMaxVoteIdx() {
		return voteDAO.getMaxVoteIdx();
	}

	@Override
	public void voteSelectInsert(VoteVO vo) {
		//System.out.println(personInforIdx + " , " + popularIdx + " , " + voteNum);
		voteDAO.voteSelectInsert(vo);
	}

	@Override
	public List<VoteVO> voteResultView(int idx) {
		return voteDAO.voteResultView(idx);
	}

	@Override
	public int getVoteTotCnt(int idx) {
		return voteDAO.getVoteTotCnt(idx);
	}

	@Override
	public void setProgress(int idx, String progress) {
		voteDAO.setProgress(idx, progress);
	}

	@Override
	public List<VoteVO> voteGenderResultView(int idx) {
		return voteDAO.voteGenderResultView(idx);
	}

	@Override
	public List<VoteVO> voteAgeResultView(int idx) {
		return voteDAO.voteAgeResultView(idx);
	}

	@Override
	public String getVoteSubTitle(int idx) {
		return voteDAO.getVoteSubTitle(idx);
	}

	@Override
	public List<VoteVO> voteAreaResultView(int idx, int voteNum) {
		return voteDAO.voteAreaResultView(idx, voteNum);
	}

	@Override
	public List<VoteVO> voteJobResultView(int idx, int voteNum) {
		return voteDAO.voteJobResultView(idx, voteNum);
	}
}
