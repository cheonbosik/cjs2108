package com.spring.cjs2108.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.dao.GuestDAO;
import com.spring.cjs2108.dao.MemberDAO;
import com.spring.cjs2108.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	GuestDAO guestDAO;

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

	// 회원 로그인후 처리해야할 내용들을 처리후 DB에 저장한다.
	@Override
	public void getMemberTodayProcess(int todayCnt) {		  // todayCnt(DB에 저장된 오늘날짜까지 방문누적변수)
		// 앞에서 mid와 lastDate를 매개변수로 받아오면 되지만, session객체 생성을 복습하기위해 request객체를 새로 생성해봤다.
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("sMid");
		String lastDate = (String) session.getAttribute("sLastDate");		// 세션에 저장된 마지막 방문일자를 가져온다.
		
	  // 오늘 방문횟수 5회까지만 포인트 10점을 누적처리한다.
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(today);
		
		int newPoint = 0;		
		if(lastDate.substring(0, 10).equals(strToday)) {	// 최종접속일과 오늘 현재접속일을 비교한다.
			if(todayCnt >= 5) newPoint = 0;  // 오늘날짜 기존방문 누적일수가 5번째 방문이었다면, 이번에 다시 방문시는 6번째 방문이 된다.
			else	newPoint = 10;
			todayCnt += 1;	// 오늘날짜 방문일수에 1을 누적
		}
		else {	// 오늘 첫 방문이면 오늘날짜 방문일수 누적변수는 1이고, 방문포인트도 10을 준다.
			todayCnt = 1;
			newPoint = 10;
		}
		
		// 앞에서 구한 오늘날짜까지의 방문누적수(todayCnt)를 mid와 함께 넘겨서 갱신처리한다.(이때 총 방문횟수와 최종방문일자도 업데이트 시킨다)
		memberDAO.setLastDateUpdate(mid, newPoint, todayCnt);  // 신규 로그인시 수정항목(포인트, 방문수,등등..) 처리
	}
	
	// 사용자가 방명록에 올린 글의 개수 담아오기
	@Override
	public int getGuestWriteCnt(String mid, String nickName, String name) {
		return memberDAO.getGuestWriteCnt(mid, nickName, name);
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
	public int getBoardWriteCnt(String mid) {
		return memberDAO.getBoardWriteCnt(mid);
	}
	
}
