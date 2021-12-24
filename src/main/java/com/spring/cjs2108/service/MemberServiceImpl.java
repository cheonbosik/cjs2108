package com.spring.cjs2108.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.dao.MemberDAO;
import com.spring.cjs2108.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberVO(String mid) {
		return memberDAO.getMemberVO(mid);
	}
	
	@Override
	public MemberVO getIdCheck(String mid) {
		return memberDAO.getIdCheck(mid);
	}

	@Override
	public MemberVO getNickNameCheck(String nickName) {
		return memberDAO.getNickNameCheck(nickName);
	}

	// 회원 가입처리
	@Override
	public int setMemInput(MultipartFile fName, MemberVO vo) {
		int res = 0;
		try {
			String oFileName = fName.getOriginalFilename();
			if(oFileName != "" && oFileName != null) {
				// 회원사진을 업로드처리하기위해 중복파일명처리와 업로드처리
				UUID uid = UUID.randomUUID();
				String saveFileName = uid + "_" + oFileName;
				writeFile(fName, saveFileName);
				vo.setPhoto(saveFileName);
			}
			else {
				vo.setPhoto("noimage.jpg");
			}
			// 회원사진을 정상적으로 업로드마치고나면 회원정보를 DB에 넣어준다.
			memberDAO.setMemInput(vo);
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	// 회원사진 업로드처리
	private void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		byte[] data = fName.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/member/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		fos.close();
	}

	@Override
	public int setMemUpdate(MultipartFile fName, MemberVO vo) {
		int res = 0;
		try {
			// 정보수정중 사진을 새로 업로드 하였을 경우 아래 if문장을 처리한다.
			String oFileName = fName.getOriginalFilename();
			if(oFileName != "" && oFileName != null) {
				// 기존에 존재하는 회원사진을 삭제처리한다.
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				String uploadPath = request.getSession().getServletContext().getRealPath("/resources/member/");
				if(!vo.getPhoto().equals("noimage.jpg")) {
					new File(uploadPath + vo.getPhoto()).delete();
				}
				UUID uid = UUID.randomUUID();
				String saveFileName = uid + "_" + oFileName;
				writeFile(fName, saveFileName);
				vo.setPhoto(saveFileName);
			}
			
			// 회원사진을 정상적으로 업로드마치고나면 회원정보를 DB에서 수정처리해준다.
			memberDAO.setMemUpdate(vo);
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void setMemDelete(String mid) {
		memberDAO.setMemDelete(mid);
	}

	@Override
	public MemberVO getPwdConfirm(String mid, String toMail) {
		return memberDAO.getPwdConfirm(mid, toMail);
	}

	@Override
	public void setPwdChange(String mid, String pwd) {
		memberDAO.setPwdChange(mid, pwd);
	}

	@Override
	public ArrayList<MemberVO> getIdConfirm(String toMail) {
		return memberDAO.getIdConfirm(toMail);
	}

	@Override
	public void setVisitUpdate(String mid) {
		memberDAO.setVisitUpdate(mid);
	}

}
