package com.spring.cjs2108.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2108.dao.MemberDAO;
import com.spring.cjs2108.dao.StudyDAO;
import com.spring.cjs2108.vo.Goods3VO;
import com.spring.cjs2108.vo.MainImageVO;
import com.spring.cjs2108.vo.MemberVO;

@Service
public class StudyServiceImpl implements StudyService {
	@Autowired
	StudyDAO studyDAO;

	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public ArrayList<String> getCity(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("강북구");
			vos.add("강동구");
			vos.add("강서구");
			vos.add("종로구");
			vos.add("서대문구");
			vos.add("영등포구");
			vos.add("관악구");
			vos.add("성북구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("부천시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("하남시");
			vos.add("성남시");
			vos.add("안양시");
			vos.add("광명시");
			vos.add("남양주시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("영동군");
			vos.add("옥천군");
			vos.add("음성군");
			vos.add("진천군");
			vos.add("증평군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("공주시");
			vos.add("태안군");
			vos.add("보령시");
			vos.add("당진시");
			vos.add("홍성군");
			vos.add("부여시");
			vos.add("논산시");
		}
		return vos;
	}

	@Override
	public String[] getCityString(String dodo) {
		String[] strArr = new String[100];
		if(dodo.equals("서울")) {
			strArr[0] = "강남구";
			strArr[1] = "강북구";
			strArr[2] = "강동구";
			strArr[3] = "강서구";
			strArr[4] = "종로구";
			strArr[5] = "서대문구";
			strArr[6] = "영등포구";
			strArr[7] = "관악구";
			strArr[8] = "성북구";
		}
		else if(dodo.equals("경기")) {
			strArr[0] = "수원시";
			strArr[1] = "부천시";
			strArr[2] = "안성시";
			strArr[3] = "평택시";
			strArr[4] = "하남시";
			strArr[5] = "성남시";
			strArr[6] = "안양시";
			strArr[7] = "광명시";
			strArr[8] = "남양주시";
		}
		else if(dodo.equals("충북")) {
			strArr[0] = "청주시";
			strArr[1] = "충주시";
			strArr[2] = "제천시";
			strArr[3] = "단양군";
			strArr[4] = "영동군";
			strArr[5] = "옥천군";
			strArr[6] = "음성군";
			strArr[7] = "진천군";
			strArr[8] = "증평군";
		}
		else if(dodo.equals("충남")) {
			strArr[0] = "천안시";
			strArr[1] = "아산시";
			strArr[2] = "공주시";
			strArr[3] = "태안군";
			strArr[4] = "보령시";
			strArr[5] = "당진시";
			strArr[6] = "홍성군";
			strArr[7] = "부여시";
			strArr[8] = "논산시";
		}
		return strArr;
	}

	@Override
	public MemberVO getMemberVO(String mid) {
		//return studyDAO.getMemberVO(mid);
		return memberDAO.getMemberVO(mid);
	}

	@Override
	public ArrayList<MemberVO> getMemberVos(String mid) {
		return memberDAO.getMemberVos(mid);
	}

	@Override
	public int fileUpload(MultipartFile fName) {
		int res = 0;
		try {
			UUID uid = UUID.randomUUID();
			String oFileName = fName.getOriginalFilename();
			String saveFileName = uid + "_" + oFileName;
			writeFile(fName, saveFileName);
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	private void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		byte[] data = fName.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/test/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		fos.close();
	}

	@Override
	public List<Goods3VO> getProduct1() {
		return studyDAO.getProduct1();
	}

	@Override
	public ArrayList<Goods3VO> getProduct2(String product1) {
		return studyDAO.getProduct2(product1);
	}

	@Override
	public ArrayList<Goods3VO> getProduct3(String product1, String product2) {
		return studyDAO.getProduct3(product1, product2);
	}

	@Override
	public void getCalendar() {
		// model객체를 사용하게되면 불필요한 메소드가 많이 따라오기에 여기서는 request객체를 사용했다.
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 오늘 날짜 저장시켜둔다.(calToday변수, 년(toYear), 월(toMonth), 일(toDay))
		Calendar calToday = Calendar.getInstance();
		int toYear = calToday.get(Calendar.YEAR);
		int toMonth = calToday.get(Calendar.MONTH);
		int toDay = calToday.get(Calendar.DATE);
				
		// 화면에 보여줄 해당 '년(yy)/월(mm)'을 셋팅하는 부분(처음에는 오늘 년도와 월을 가져오지만, '이전/다음'버튼 클릭하면 해당 년과 월을 가져오도록 한다.
		Calendar calView = Calendar.getInstance();
		int yy = request.getParameter("yy")==null ? calView.get(Calendar.YEAR) : Integer.parseInt(request.getParameter("yy"));
	  int mm = request.getParameter("mm")==null ? calView.get(Calendar.MONTH) : Integer.parseInt(request.getParameter("mm"));
	  
	  if(mm < 0) { // 1월에서 전월 버튼을 클릭시에 실행
	  	yy--;
	  	mm = 11;
	  }
	  if(mm > 11) { // 12월에서 다음월 버튼을 클릭시에 실행
	  	yy++;
	  	mm = 0;
	  }
	  calView.set(yy, mm, 1);		// 현재 '년/월'의 1일을 달력의 날짜로 셋팅한다.
	  
	  int startWeek = calView.get(Calendar.DAY_OF_WEEK);  						// 해당 '년/월'의 1일에 해당하는 요일값을 숫자로 가져온다.
	  int lastDay = calView.getActualMaximum(Calendar.DAY_OF_MONTH);  // 해당월의 마지막일자(getActualMaxximum메소드사용)를 구한다.
	  
	  // 화면에 보여줄 년월기준 전년도/다음년도를 구하기 위한 부분
	  int prevYear = yy;  			// 전년도
	  int prevMonth = (mm) - 1; // 이전월
	  int nextYear = yy;  			// 다음년도
	  int nextMonth = (mm) + 1; // 다음월
	  
	  if(prevMonth == -1) {  // 1월에서 전월 버튼을 클릭시에 실행..
	  	prevYear--;
	  	prevMonth = 11;
	  }
	  
	  if(nextMonth == 12) {  // 12월에서 다음월 버튼을 클릭시에 실행..
	  	nextYear++;
	  	nextMonth = 0;
	  }
	  
	  // 현재달력에서 앞쪽의 빈공간은 '이전달력'을 보여주고, 뒷쪽의 남은공간은 '다음달력'을 보여주기위한 처리부분(아래 6줄)
	  Calendar calPre = Calendar.getInstance(); // 이전달력
	  calPre.set(prevYear, prevMonth, 1);  			// 이전 달력 셋팅
	  int preLastDay = calPre.getActualMaximum(Calendar.DAY_OF_MONTH);  // 해당월의 마지막일자를 구한다.
	  
	  Calendar calNext = Calendar.getInstance();// 다음달력
	  calNext.set(nextYear, nextMonth, 1);  		// 다음 달력 셋팅
	  int nextStartWeek = calNext.get(Calendar.DAY_OF_WEEK);  // 다음달의 1일에 해당하는 요일값을 가져온다.
	  
	  /* ---------  아래는  앞에서 처리된 값들을 모두 request객체에 담는다.  -----------------  */
	  
	  // 오늘기준 달력...
	  request.setAttribute("toYear", toYear);
	  request.setAttribute("toMonth", toMonth);
	  request.setAttribute("toDay", toDay);
	  
	  // 화면에 보여줄 해당 달력...
	  request.setAttribute("yy", yy);
	  request.setAttribute("mm", mm);
	  request.setAttribute("startWeek", startWeek);
	  request.setAttribute("lastDay", lastDay);
	  
	  // 화면에 보여줄 해당 달력 기준의 전년도, 전월, 다음년도, 다음월 ...
	  request.setAttribute("preYear", prevYear);
		request.setAttribute("preMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		
		// 현재 달력의 '앞/뒤' 빈공간을 채울, 이전달의 뒷부분과 다음달의 앞부분을 보여주기위해 넘겨주는 변수
		request.setAttribute("preLastDay", preLastDay);				// 이전달의 마지막일자를 기억하고 있는 변수
		request.setAttribute("nextStartWeek", nextStartWeek);	// 다음달의 1일에 해당하는 요일을 기억하고있는 변수
	}
	
	// 메인 이미지(상품/이벤트/포토) 등록하기(data폴더에서 data/mainImage폴더로 사진 복사시키기
	@SuppressWarnings("deprecation")
	@Override
	public void imgCheckInput(MainImageVO vo) {
		//             0         1         2         3         4         5
		//             012345678901234567890123456789012345678901234567890
		// <img alt="" src="/cjs2108/data/211229124318_4.jpg"
		if(vo.getContent().indexOf("src=\"/") == -1) return;
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getRealPath("/resources/data/");
		
		int position = 19;
		String nextImg = vo.getContent().substring(vo.getContent().indexOf("src=\"/") + position);
		boolean sw = true, imageSw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			String copyFilePath = "";
			String oriFilePath = uploadPath + imgFile;	// 원본 그림이 들어있는 '경로명+파일명'
			
			copyFilePath = uploadPath + "mainImage/" + imgFile;	// 복사가 될 '경로명+파일명'
			
			fileCopyCheck(oriFilePath, copyFilePath);	// 원본그림이 복사될 위치로 복사작업처리하는 메소드
			
			if(nextImg.indexOf("src=\"/") == -1) {
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
  		// 이미지 복사작업이 종료되면 실제로 저장된 mainImage 파일명을 vo에 set시켜준후, 고유번호(idx)를 만들고, DB에 저장시켜줘야 한다.
  		// 실제 파일명을 vo에 set시키기
			vo.setMainFName(imgFile);
			if(imageSw) {
				// 고유번호 만들기(아래 3줄)
				MainImageVO maxIdx = studyDAO.getMainImageIdx();
				int idx = 1;
				if(maxIdx != null) idx = maxIdx.getMaxIdx() + 1;
				vo.setIdx(idx);
				imageSw = false;
			}
			
			studyDAO.imgCheckInput(vo);
		}
	}

	// 실제 파일(data폴더)을 mainImage폴더로 복사처리하는곳 
	private void fileCopyCheck(String oriFilePath, String copyFilePath) {
		File oriFile = new File(oriFilePath);
		File copyFile = new File(copyFilePath);
		
		try {
			FileInputStream  fis = new FileInputStream(oriFile);
			FileOutputStream fos = new FileOutputStream(copyFile);
			
			byte[] buffer = new byte[2048];
			int count = 0;
			while((count = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, count);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MainImageVO> getMainImageList(int idx) {
		return studyDAO.getMainImageList(idx);
	}

	@Override
	public List<MainImageVO> getMainImagePart() {
		return studyDAO.getMainImagePart();
	}

	// 메인이미지들 삭제처리
	@SuppressWarnings("deprecation")
	@Override
	public void mainImageDelete(int idx) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getRealPath("/resources/data/mainImage/");
		
		List<MainImageVO> vos = studyDAO.getMainImageList(idx);
		for(MainImageVO mainFName : vos) {
			String oriFilePath = realPath + mainFName.getMainFName();	// 원본 그림이 들어있는 '경로명+파일명'
			
			// 원본그림을 삭제처리
			File delFile = new File(oriFilePath);
			if(delFile.exists()) delFile.delete();
		}
		studyDAO.mainImageDelete(idx);
	}
}
