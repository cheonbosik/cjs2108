package com.spring.cjs2108.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.cjs2108.dao.PdsDAO;
import com.spring.cjs2108.vo.PdsVO;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;

	@Override
	public int totRecCnt(String part) {
		return pdsDAO.totRecCnt(part);
	}
	
	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public void pdsInput(MultipartHttpServletRequest mfile, PdsVO vo) {
		try {
			List<MultipartFile> fileList = mfile.getFiles("file");
			String oFileNames = "";     
			String saveFileNames = "";  
			int fileSizes = 0;          
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				
				// 서버에 저장될 파일명 작업(파일명을 날짜를 사용하여 중복을 방지처리했다)
				String saveFileName = saveFileName(oFileName);
				
				// 실제로 서버 파일시스템에 업로드한 파일을 저장한다.
				writeFile(file, saveFileName);
				
				// DB에는 업로드 파일명과 실제 저장된 파일명을 저장시켜야 하기에, 여러개의 파일을 '/'와 같이 누적처리했다. 
				oFileNames += oFileName + "/";
				saveFileNames += saveFileName + "/";
				fileSizes += file.getSize();
			}
			
			vo.setFName(oFileNames);
			vo.setFSName(saveFileNames);
			vo.setFSize(fileSizes);
			
			pdsDAO.pdsInput(vo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void writeFile(MultipartFile file, String saveFileName) throws IOException {
		byte[] data = file.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);  
		
		fos.close();
	}
	
	private String saveFileName(String oFileName) {
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;
		
		return fileName;
	}

	@Override
	public void setDownCheck(int idx) {
		pdsDAO.setDownCheck(idx);
	}

	@Override
	public PdsVO getPdsContent(int idx) {
		return pdsDAO.getPdsContent(idx);
	}

	@Override
	public void pdsDelete(PdsVO vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		String[] fSNames = vo.getFSName().split("/");
		
		String realPathFile = "";
		for(int i=0; i<fSNames.length; i++) {
			realPathFile = realPath + fSNames[i];
			new File(realPathFile).delete();
		}
		pdsDAO.pdsDelete(vo.getIdx());
	}
	
}
