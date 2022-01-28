package com.spring.cjs2108;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.cjs2108.pagination.PageProcess;
import com.spring.cjs2108.pagination.PageVO;
import com.spring.cjs2108.service.VoteService;
import com.spring.cjs2108.vo.MemberVO;
import com.spring.cjs2108.vo.VotePersonInforVO;
import com.spring.cjs2108.vo.VotePopularVO;
import com.spring.cjs2108.vo.VoteVO;

@Controller
@RequestMapping("/vote")
public class VoteController {
	String msgFlag = "";
	
	@Autowired
	VoteService voteService;
	
	@Autowired
	PageProcess pageProcess;
	
	// 설문주제 등록하기 폼 호출
	@RequestMapping(value="/aVoteInput", method=RequestMethod.GET)
	public String aVoteInputGet() {
		return "admin/vote/voteInput";
	}
	
	// 설문주제 등록하기
	@RequestMapping(value="/aVoteInput", method=RequestMethod.POST)  // 멀티파일을 위한 객체 : MultipartHttpServletRequest객체를 사용한다.
  public String aVoteInputPost(MultipartHttpServletRequest file, VotePopularVO vo) {
  	voteService.aVotePopularInputOk(file, vo);
  	msgFlag = "aVoteInputOk";
  	
  	return "redirect:/msg/" + msgFlag;
  }
	
	// 투표 주제 리스트 보여주기 폼..
	@RequestMapping(value="/aVoteList", method=RequestMethod.GET)
	public String aVoteListGet(HttpServletRequest request, Model model,
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		PageVO pageVO = pageProcess.pagination(pag, pageSize, "vote", part, "");
		List<VotePopularVO> vos = voteService.aVotePopularList(pageVO.getStartNo(), pageSize, part);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		return "admin/vote/voteList";
	}
	
	// 투표 주제 내역 삭제하기
	@ResponseBody
	@RequestMapping(value="/votePopularDelete", method=RequestMethod.GET)
	public int votePopularDeleteGet(int idx, String realImg, String thumbImg) {
		int res = voteService.votePopularDelete(idx, realImg, thumbImg);
		return res;
	}
  
	// 처음 시작....(투표메뉴버튼 누를때) 
	// 투표하는 사람의 기본 정보 등록하기 폼 보여주기. 투표할 경우(회원/비회원 모두 투표할 수 있다.) 기본정보 입력폼 보여주기... 투표회원 정보가져오기(기존회원이면 기존회원의 '성별/직업'등이 필요하다)
	@RequestMapping(value="/voteInforInsert", method=RequestMethod.GET)
	public String voteInforInsertGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		if(mid != null && !mid.equals("")) {  // 기존회원이면 member2테이블에서 gender를 담고있는 vo를 가져온다.
			MemberVO memberVO = voteService.memberSearch(mid);
			LocalDate now = LocalDate.now();
			int year = Integer.parseInt(now.toString().substring(0,4));
			int ageGroup =  Integer.parseInt(memberVO.getBirthday().substring(0,4));
			memberVO.setAgeGroup((year - ageGroup) / 10 * 10);
			//System.out.println("Vote의 memberVO : " + memberVO);
			model.addAttribute("memberVO", memberVO);
		}
		return "vote/voteInforInsert";
	}
	
	// 투표 정보 등록하기
	@RequestMapping(value="/voteInforInsert", method=RequestMethod.POST)
	public String voteInforInsertPost(VotePersonInforVO personVO, HttpSession session) {
		int votePersonInforIdx = voteService.getCountVotePersonInfor()==0 ? 1 : voteService.getMaxVotePersonInfor() + 1;
		session.setAttribute("sVotePersonInforIdx", votePersonInforIdx);  // 투표를 하기위해 처음에는 사용자의 정보를 DB에 저장하려한다. 한번 세션에 저장된 후에는 계속해서 투표할 수 있기에, 생성된 votePersonInforIdx 를 세션에 저장시켜둔다. 
		personVO.setIdx(votePersonInforIdx);
		voteService.aVoteInforInsert(personVO);
		return "redirect:/vote/voteList";
	}
	
	// 설문자료 리스트 보여주기
	@RequestMapping(value="/voteList", method=RequestMethod.GET)
	public String voteListGet(HttpServletRequest request, Model model,
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		// 아래 두줄은 전체 설문자료 보여주기위함
		//PageVO pageVoteVO = pageProcess.pagination(0, 100, "vote", "전체", "");
		List<VotePopularVO> voteVos = voteService.aVotePopularList(0, 100, "전체");
		//List<VotePopularVO> voteVos = voteService.aVotePopularList(pageVoteVO.getStartNo(), 100, part);
		model.addAttribute("voteVos", voteVos);
		
		// 아래 두줄은 전체 설문자료 보여주기위함
		PageVO pageVO = pageProcess.pagination(pag, pageSize, "vote", part, "");
		List<VotePopularVO> vos = voteService.aVotePopularList(pageVO.getStartNo(), pageSize, part);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		return "vote/voteList";
	}
	
	// 투표하기전에 투표문항을 보여주기
	@ResponseBody
	@RequestMapping(value="/votePopularView", method=RequestMethod.GET)
	public VotePopularVO votePopularViewGet(int idx) {
		VotePopularVO vo = voteService.getVotePopularView(idx);
		return vo;
	}
	
	// 투표한 항목을 DB에 저장하기
	@ResponseBody
	@RequestMapping(value="/voteSelectInsert", method=RequestMethod.GET)
	public int voteSelectInsertGet( VoteVO vo) {  // votePersonInforIdx:설문한사람의 고유번호(personInforIdx로변환), idx:설문지번호(popularIdx로변환), sel:선택한 설문지항목번호(voteNum로변환)
		int voteIdx = voteService.getCountVoteIdx()==0 ? 1 : voteService.getMaxVoteIdx() + 1;
		vo.setIdx(voteIdx);
		voteService.voteSelectInsert(vo);
		return 1;
	}
	
	// 투표한 결과 보기
	@ResponseBody
	@RequestMapping(value="/voteResultView", method=RequestMethod.GET)
	public HashMap<String, Object> voteResultViewGet(int idx) {  // votePersonInforIdx:설문한사람의 고유번호(personInforIdx로변환), idx:설문지번호(popularIdx로변환), sel:선택한 설문지항목번호(voteNum로변환)
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<VoteVO> vos = voteService.voteResultView(idx);  // vos로 값을 넘길때 여기서는 HashMap을 사용했다. vo는 그냥 자체로 넘길수 있다.
		result.put("vos", vos);
		return result;
	}
	
	// 현재 투표를 진행할것인지 아닌지를 표시~~ '진행중/진행중지' 표시유무(관리자에서 호출하고 있다.)
	@ResponseBody
	@RequestMapping(value="/voteProgress", method=RequestMethod.GET)
	public String voteProgressGet(int idx, String progress) {
		voteService.setProgress(idx, progress);
		return "";
	}
	
	// 투표결과를 차트로 보기
	@RequestMapping(value="/chartAnalysis", method=RequestMethod.GET)
	public String chartAnalysisGet(HttpServletRequest request, Model model, String chartFlag, int idx, String subPart) {
		int voteTotCnt = voteService.getVoteTotCnt(idx);  // 해당 주제에 투표한 총 투표 인원수
		//String subTitles = voteService.getVoteSubTitle(idx);	// 해당 설문주제 가져오기
		//String[] arrSubTitle = subTitles.split("/");	// 해당 설문주제에 따른 항목별 제목을 담는배열
		//model.addAttribute("arrSubTitle", arrSubTitle);
		
		// 아래는 pie차트와 bar차트에 필요한 자료들(해당 주제에 따른 각 항목별 투표자수)
    List<VoteVO> vos = voteService.voteResultView(idx);  // 투표정보(설문 주제에따른 각 문항별 투표한 총 개수/주제명/각 항목명 - 조인해서 가져옴.)
    int[] arrVoteSum = new int[5];
    for(int i=0; i<arrVoteSum.length; i++) {
    	int sw = 0;
  		for(int j=0; j<vos.size(); j++) {
	    	if((i+1) == vos.get(j).getVoteNum()) {
	    		arrVoteSum[i] = vos.get(j).getVoteSum();
	    		sw = 1;
	    		break;
	    	}
    	}
    	if(sw == 0)	arrVoteSum[i] = 0;
    }
    model.addAttribute("arrVoteSum", arrVoteSum);
    model.addAttribute("subTitles", vos.get(0).getSubTitle());

    
    // 아래는 Donut차트(해당 주제를 투표한 남자 여자의 인원수?)
    List<VoteVO> genderVos = voteService.voteGenderResultView(idx);
    
    // 아래는 Line차트(해당 주제를 투표한 연령별 인원수?)를 위한 데이터 준비
    List<VoteVO> ageVos = voteService.voteAgeResultView(idx);
    int[] arrAge = new int[6]; 
    int[] arrAgeSum = new int[6];
    int cnt = 0, ageSum = 0;
    for(int i=0; i<arrAge.length; i++) {
    	cnt += 10;
    	arrAge[i] = cnt;
    	for(VoteVO vo : ageVos) {
    		if(Integer.parseInt(vo.getAge().substring(0,2)) == cnt) {
    			arrAgeSum[i] = vo.getVoteSum();
    			ageSum += vo.getVoteSum();
    		}
    	}
    }
    
    // 아래는 Area차트(해당 주제를 투표한 지역별 인원수?)를 위한 데이터 준비
    //List<VoteVO> areaVos = voteService.voteAreaResultView(idx);
    int[][] areaVoteNum = new int[5][7];	// [5]는 0~4:1번문항~5번문항 / [7]은 0:서울,1:경기도,2:강원도,3:충청도,4:전라도,5:경상도,6:제주도
    String[] areaName = {"서울","경기도","강원도","충청도","전라도","경상도","제주도"};		// 지역명저장배열
    for(int voteNum=0; voteNum<areaVoteNum.length; voteNum++) {
//    	for(int j=0; j<areaVoteNum[voteNum].length; j++) {
  		for(int j=0; j<1; j++) {
    		List<VoteVO> areaVos = voteService.voteAreaResultView(idx, voteNum+1);
//    		System.out.println((voteNum+1)+"번문항.areaVos : " + areaVos);
    		for(int k=0; k<areaVos.size(); k++) {
    			for(int l=0; l<5; l++) {
    				if((l+1)==areaVos.get(k).getVoteNum()) {
    					switch(areaVos.get(k).getArea()) {
    						case "서울" : areaVoteNum[l][0] = areaVos.get(k).getVoteSum(); break;
    						case "경기도": areaVoteNum[l][1] = areaVos.get(k).getVoteSum(); break;
    						case "강원도": areaVoteNum[l][2] = areaVos.get(k).getVoteSum(); break;
    						case "충청도": areaVoteNum[l][3] = areaVos.get(k).getVoteSum(); break;
    						case "전라도": areaVoteNum[l][4] = areaVos.get(k).getVoteSum(); break;
    						case "경상도": areaVoteNum[l][5] = areaVos.get(k).getVoteSum(); break;
    						case "제주도": areaVoteNum[l][6] = areaVos.get(k).getVoteSum(); break;
    					}
    				}
    				//areaSum += areaVoteNum[][];
    			}
    		}
    	}
    }
    model.addAttribute("areaName", areaName);
    model.addAttribute("areaVoteNum", areaVoteNum);
    
    // 검산작업
    int areaSum = 0; 	// 지역자 총 투표인 수
    for(int i=0; i<5; i++) {
    	for(int j=0; j<7; j++) {
    		areaSum += areaVoteNum[i][j];
    		//System.out.print(areaVoteNum[i][j] + ",");
    	}
    	//System.out.println();
    }
    
    // 아래는 Job차트(해당 주제를 투표한 직업별 인원수?)를 위한 데이터 준비
    int[][] jobVoteNum = new int[5][9];	// [5]는 0~4:1번문항~5번문항 / [9]은 0:학생,1:회사원,2:공무원,3:군인,4:의사,5:법조인,6:세무인,7:자영업,8:기타
    String[] jobName = {"학생","회사원","공무원","군인","의사","법조인","세무인","자영업","기타"};		// 직업명저장배열
    for(int voteNum=0; voteNum<areaVoteNum.length; voteNum++) {			// voteNum은 선택항목번호
    	for(int j=0; j<1; j++) {
    		List<VoteVO> jobVos = voteService.voteJobResultView(idx, voteNum+1);
//    		System.out.println((voteNum+1)+"번문항.areaVos : " + areaVos);
    		for(int k=0; k<jobVos.size(); k++) {
    			for(int l=0; l<5; l++) {
    				if((l+1)==jobVos.get(k).getVoteNum()) {
    					switch(jobVos.get(k).getJob()) {
    						case "학생" : jobVoteNum[l][0] = jobVos.get(k).getVoteSum(); break;
    						case "회사원": jobVoteNum[l][1] = jobVos.get(k).getVoteSum(); break;
    						case "공무원": jobVoteNum[l][2] = jobVos.get(k).getVoteSum(); break;
    						case "군인": jobVoteNum[l][3] = jobVos.get(k).getVoteSum(); break;
    						case "의사": jobVoteNum[l][4] = jobVos.get(k).getVoteSum(); break;
    						case "법조인": jobVoteNum[l][5] = jobVos.get(k).getVoteSum(); break;
    						case "세무인": jobVoteNum[l][6] = jobVos.get(k).getVoteSum(); break;
    						case "자영업": jobVoteNum[l][7] = jobVos.get(k).getVoteSum(); break;
    						case "기타": jobVoteNum[l][8] = jobVos.get(k).getVoteSum(); break;
    					}
    				}
    				//jobSum += jobVoteNum[][];
    			}
    		}
    	}
    }
    model.addAttribute("jobName", jobName);
    model.addAttribute("jobVoteNum", jobVoteNum);
    
    // 검산작업
    int jobSum = 0; 	// 지역자 총 투표인 수
    for(int i=0; i<5; i++) {
    	for(int j=0; j<9; j++) {
    		jobSum += jobVoteNum[i][j];
    		//System.out.print(jobVoteNum[i][j] + ",");
    	}
    	//System.out.println();
    }
    
    model.addAttribute("vos", vos);
    model.addAttribute("voteTotCnt", voteTotCnt);
    model.addAttribute("chartFlag", chartFlag);
    model.addAttribute("subPart", subPart);
    model.addAttribute("genderVos", genderVos);
    model.addAttribute("arrAge", arrAge);
    model.addAttribute("arrAgeSum", arrAgeSum);
    model.addAttribute("ageSum", ageSum);
    model.addAttribute("areaSum", areaSum);
    model.addAttribute("jobSum", jobSum);
    
    if(chartFlag.equals("pie")) {
		  return "vote/analysisPieChart";
    }
    else if(chartFlag.equals("bar")) {
    	return "vote/analysisBarChart";
    }
    else if(chartFlag.equals("donut")) {
    	return "vote/analysisDonutChart";
    }
    else if(chartFlag.equals("line")) {
    	return "vote/analysisLineChart";
    }
    else if(chartFlag.equals("area")) {
    	return "vote/analysisAreaChart";
    }
    else if(chartFlag.equals("job")) {
    	return "vote/analysisJobChart";
    }
    return "";
	}
}
