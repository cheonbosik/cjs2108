<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<%
  String realUrl = application.getRealPath("/");
  request.setAttribute("realUrl", realUrl);
%>
<%-- -${realUrl} --%>
<html>
<head>
  <meta charset="UTF-8">
  <title>voteList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function partCheck() {
    	var part = partForm.part.value;
    	location.href = "${ctp}/vote/voteList?pag=${pag}&part="+part;
    }
    
    // 처음 로딩시체 셋팅값 설정하기
    $(document).ready(function() {
	  //  $("#votePopularHide").hide();
    	$(".votePopularShowBtn2").hide();
    	$(".votePopularHide").hide();
    	$(".resultHide").hide();
    	//$("#voteTotalHiddenBtn").hide();
    	$("#voteTotalShowBtn").hide();
    	// $("#voteTotal").hide();
    });
    
    // 투표 문항 항목 보여주기(투표하기 버튼 클릭시 수행)
    function votePopularShow(idx) {
    	//$("#voteQuestion").show();
    	$("#voteQuestion").slideDown(600);
    	$("#votePopularHide").show();
    	var query = {idx : idx};
    	$.ajax({
    		url  : "${ctp}/vote/votePopularView",
    		type : "get",
    		data : query,
    		success : function(data) {
   			  //var imsiVotePopularHide = "#voteQuestion"+data.idx;
    			var insRow = "";
    			if(data != null) {
    				var subTitles = data.subTitle.split("/");
    			  var realImgs = data.realImg.split("/");
    			  var thumbImgs = data.thumbImg.split("/");
    			  //console.log(thumbImgs[0]);
    			  insRow += "<form name='voteForm' method='post'>";
    			  insRow += "<table class='w-100 text-center m-0 p-0'>";
    			  insRow += "<tr class='text-center m-0 p-0'>";
    			  insRow += "<c:forEach var='i' begin='0' end='4'>";
    			  insRow += "<td class='text-center m-0 p-0'><a href='javascript:imgView(\""+realImgs[${i}]+"\")' title=\""+subTitles[${i}]+"\"><img src='${ctp}/vote/"+thumbImgs[${i}]+"' width='150px' height='100px'/></a></td>";
    			  insRow += "</c:forEach>";
    			  insRow += "</tr><tr class='text-center m-0 p-0'>";
    			  insRow += "<c:forEach var='i' begin='0' end='4'>";
    			  //insRow += "<td class='text-center m-0 p-0'>${i+1}.<input type='radio' name='popular"+data.idx+"' value='${i}' onclick='voteCheck("+data.idx+",${i})'/></td>";
    			  insRow += "<td class='m-0 p-1'><input type='radio' onclick='voteCheck(\""+data.idx+"\",\"${i}\",\""+data.title+"\",\""+subTitles[${i}]+"\")'/></td>";
    			  insRow += "</c:forEach>";
    			  insRow += "</tr>";
    				insRow += "</table>";
    				insRow += "</form>";
	   			  var imsiVoteQuestion = "#voteQuestion"+data.idx;
						$(imsiVoteQuestion).html(insRow);
    			  var imsiVotePopularShowBtn = "#votePopularShowBtn"+data.idx; 
						$(imsiVotePopularShowBtn).hide();
    			  var imsiVotePopularShowBtn2 = "#votePopularShowBtn2"+data.idx; 
						$(imsiVotePopularShowBtn2).hide();
						var imsiVotePopularHide = "#votePopularHide"+data.idx;
						$(imsiVotePopularHide).show();
    			}
    		},
    		error : function(data) {
    			alert("검색실패!");
    		}
    	});
    }
    
    // 투표항목에서 제목 클릭시 문항목록보기/닫기(토글처리)
    var titleView = false;
    function voteQuestionTitle(idx) {
    	if(!titleView) {
	    	titleView = true;
	    	var query = {idx : idx};
	    	$.ajax({
	    		url  : "${ctp}/vote/votePopularView",
	    		type : "get",
	    		data : query,
	    		success : function(data) {
	    			var insRow = "";
	    			if(data != null) {
	    				var subTitles = data.subTitle.split("/");
	    			  var realImgs = data.realImg.split("/");
	    			  var thumbImgs = data.thumbImg.split("/");
	    			  insRow += "<form name='voteForm' method='post'>";
	    			  insRow += "<table class='w-100 text-center m-0 p-0'>";
	    			  insRow += "<tr>";
	    			  insRow += "<td colspan='5'><table>";
	    			  insRow += "<c:forEach var='i' begin='0' end='4'>";
	    			  insRow += "<tr><td><input type='radio' name='choice' onclick='voteCheck(\""+data.idx+"\",\"${i}\",\""+data.title+"\",\""+subTitles[${i}]+"\")'/><a href='javascript:imgView(\""+realImgs[${i}]+"\")' title=\""+subTitles[${i}]+"\">&nbsp; <img src='${ctp}/vote/"+thumbImgs[${i}]+"' width='150px'/> &nbsp;&nbsp;"+subTitles[${i}]+"</a></td></tr>";
	    			  insRow += "</c:forEach>";
	    			  insRow += "</table></td>";
	    			  insRow += "</tr>";
	    				insRow += "</table>";
	    				insRow += "</form>";
		   			  var voteQuestionTitle = "#voteQuestionTitle"+data.idx;
							$(voteQuestionTitle).html(insRow);
	    			}
	    		},
	    		error : function(data) {
	    			alert("검색실패!");
	    		}
	    	});
    	}
    	else {
    		titleView = false;
    		 var voteQuestionTitle = "#voteQuestionTitle"+idx;
    		 $(voteQuestionTitle).hide();
    		 //location.reload();
    		 //$("#container").load("${ctp}/vote/voteList #voteQuestionTitleArea");
    		 $("#container").load(window.location.href + "#container");
    	}
    }
    
    // 그림 클릭시 큰 그림으로 보기
    function imgView(tImg) {
    	var url = tImg;
      var imgObj = new Image();
      imgObj.src = "${ctp}/vote/"+url;
      imageWin = window.open("", "profile_popup", "width=" + imgObj.width + "px, height=" + imgObj.height + "px"); 
      imageWin.document.write("<html><body style='margin:0'>"); 
      imageWin.document.write("<a href=javascript:window.close()><img src='" + imgObj.src + "' border=0></a>"); 
      imageWin.document.write("</body><html>"); 
      imageWin.document.title = imgObj.src;
    }
    
    // 투표 문항접기(투표하기접기)
    function votePopularHide(idx) {
    	var voteQuestionIdx = "#voteQuestion"+idx;
    	var votePopularShowBtn2 = "#votePopularShowBtn2"+idx;
    	var imsiVotePopularHide = "#votePopularHide"+idx;
    	$(voteQuestionIdx).hide();
    	$(votePopularShowBtn2).show();
    	$(imsiVotePopularHide).hide();
    }
    
    // 문항보기('투표하기'버튼 클릭시 투표항목 보기)
    function votePopularShow2(idx) {
    	var voteQuestionIdx = "#voteQuestion"+idx;
    	$(voteQuestionIdx).show();
    	var imsiVotePopularShowBtn2 = "#votePopularShowBtn2"+idx;
			$(imsiVotePopularShowBtn2).hide();
    	var imsiVotePopularHide = "#votePopularHide"+idx;
		  $(imsiVotePopularHide).show();
    }
    
    // 투표할경우 수행(투표하기 버튼 클릭시~)
    function voteCheck(idx, sel, title, subtitle) {
    	/* 현재상황에서 투표를 처음한다면 사용자의 인적정보를 담기위해 정보입력창으로 보낸다. */
    	if(!'${sVotePersonInforIdx}') {
    		location.href = "${ctp}/vote/voteInforInsert?mid=${sMid}";
    	}
    	/* 이미 방문한 상태에서 한번이라도 투표를 했다면 sVotePersonInforIdx가 발급되어 있기에 바로 투표에 참여할 수 있게 한다. */
    	var voteNum = parseInt(sel)+1;		// 선택한 번호는 0번부터이기에 +1 시켜주었다.
    	var ans = confirm(idx+"번 설문지 항목("+title+")중"+"\n"+(voteNum)+" 번째 사진("+subtitle+")을 선택하셨습니다.\n투표 하시겠습니까?");
    	if(ans) {
    		var query = {
    				personInforIdx : "${sVotePersonInforIdx}",		// 투표하는 사람의 고유 sVotePersonInforIdx
    				popularIdx : idx,
    				voteNum : voteNum,
    		}
    		$.ajax({
    			url  : "${ctp}/vote/voteSelectInsert",
    			type : "get",
    			data : query,
    			success : function(data) {
    				if(data == 1) {
    					alert("투표에 반영되었습니다.\n감사합니다.");
    					location.reload();
    				}
    			},
    			error : function() {
    				alert("투표에 참여되지 않았습니다.");
    			}
    		});
    	}
    }
    
    // 투표 결과보기1(상단에 있는 투표결과보기)
    function votePopularShowResult(idx,voteCnt) {
    	var voteResultHideBtn = "#voteResultHide"+idx;
    	var voteResultShowBtn = "#voteResultShow"+idx;
    	//var votePiechart = "#piechart"+idx;
    	$(voteResultHideBtn).show();
    	$(voteResultShowBtn).hide();
    	//$(votePiechart).show();
    	
    	var query = {idx : idx};
    	$.ajax({
    		url  : "${ctp}/vote/voteResultView",
    		type : "get",
    		data : query,
    		success : function(data) {
    			var subTitles = data.vos[0].subTitle.split("/");
    			var pie = "''pie''";
    			var insResult = "<input type='button' class='btn btn-secondary' value='차트분석보기' onclick='chartAnalysis("+idx+",\"pie\")' style='float:right'/>";
    			var voteNum = 0;
    			var cnt = 0;
    			for(var i=0; i<data.vos.length; i++) {
   			    while(true) {  // subTitles안의 타이틀을 출력하기위해 cnt(위치값변수)로 투표한 항목과 비교하면서 횟수를 증가시키고 있다.
	   			    cnt++;
	   			    if(data.vos[i].voteNum == (cnt)) {
		   			    var voteSum = data.vos[i].voteSum / voteCnt * 100;
		   			    insResult += "<div style='text-align:left;font-size:0.8em;'>투표자 : "+data.vos[i].voteSum+"명("+voteSum.toFixed(1)+"%) - "+subTitles[cnt-1]+"</div>";
		   			    insResult += "<div style='height:25px;'>"
		   			    insResult += "<div class='progress'>";
		   			    insResult += "<span class='progress-bar bg-info' style='width:"+(voteSum*5)+"'>"+subTitles[cnt-1]+"("+data.vos[i].voteSum+"명)"+"</span>";
		   			    insResult += "</div></div>";
		   			    break;
	   			    }
   			    }
    			}
				  var imsiVoteResult = "#voteResult"+idx;
					$(imsiVoteResult).html(insResult);
    		}
    	});
			
    }
    // 투표 결과가리기1(상단결과)
    function votePopularHideResult(idx) {
    	var voteResultHideBtn = "#voteResultHide"+idx;
    	var voteResultShowBtn = "#voteResultShow"+idx;
    	$(voteResultHideBtn).hide();
    	$(voteResultShowBtn).show();
    	var imsiVoteResult = "#voteResult"+idx;
    	// $(imsiVoteResult).hide();
    	$(imsiVoteResult).html('');
    }
    
    // 화면 하단에서, 전체 설문 리스트 보여주기(문항목록보기2)
    var titleView = false;
    function voteQuestionTitle2(idx) {
    	if(!titleView) {
	    	titleView = true;
	    	var query = {idx : idx};
	    	$.ajax({
	    		url  : "${ctp}/vote/votePopularView",
	    		type : "get",
	    		data : query,
	    		success : function(data) {
	    			var insRow = "";
	    			if(data != null) {
	    				//alert("data.subTitle:" + data.subTitle);
	    				var subTitles = data.subTitle.split("/");
	    			  var realImgs = data.realImg.split("/");
	    			  var thumbImgs = data.thumbImg.split("/");
	    			  insRow += "<form name='voteForm' method='post'>";
	    			  insRow += "<table class='w-100 text-left m-5 p-0'>";
	    			  insRow += "<tr>";
	    			  insRow += "<td colspan='5'><table width='100%'>";
	    			  insRow += "<c:forEach var='i' begin='0' end='4'>";
	    			  insRow += "<tr><td><a href='javascript:imgView(\""+realImgs[${i}]+"\")' title=\""+subTitles[${i}]+"\">${i+1}. &nbsp; <img src='${ctp}/vote/"+thumbImgs[${i}]+"' width='150px'/> &nbsp;"+subTitles[${i}]+"</a></td></tr>";
	    			  insRow += "</c:forEach>";
	    			  insRow += "</table></td>";
	    			  insRow += "</tr>";
	    				insRow += "</table>";
	    				insRow += "</form>";
		   			  var voteQuestionTitle = "#voteQuestionTitle2"+data.idx;
							$(voteQuestionTitle).html(insRow);
	    			}
	    		},
	    		error : function(data) {
	    			alert("검색실패!");
	    		}
	    	});
    	}
    	else {
    		titleView = false;
    		 var voteQuestionTitle = "#voteQuestionTitle"+idx;
    		 $(voteQuestionTitle).hide();
    		 location.reload();
    		 //$("#container").load("${ctp}/vote/voteList #voteQuestionTitleArea");
    		 //$("#container").load(window.location.href + "#container");
    	}
    }
    
    // 결과보기2
    function votePopularShowResult2(idx,voteCnt) {
    	var voteResultHideBtn2 = "#voteResultHide2"+idx;
    	var voteResultShowBtn2 = "#voteResultShow2"+idx;
    	var votePiechart = "#piechart"+idx;
    	$(voteResultHideBtn2).show();
    	$(voteResultShowBtn2).hide();
    	$(votePiechart).show();
    	
    	var query = {idx : idx};
    	$.ajax({
    		url  : "${ctp}/vote/voteResultView",
    		type : "get",
    		data : query,
    		success : function(data) {
    			var subTitles = data.vos[0].subTitle.split("/");
    			var pie = "''pie''";
    			var insResult = "<input type='button' class='btn btn-secondary' value='차트분석보기' onclick='chartAnalysis("+idx+",\"pie\")' style='float:right'/>";
    			var voteNum = 0;
    			var cnt = 0;
    			for(var i=0; i<data.vos.length; i++) {
   			    while(true) {  // subTitles안의 타이틀을 출력하기위해 cnt(위치값변수)로 투표한 항목과 비교하면서 횟수를 증가시키고 있다.
	   			    cnt++;
	   			    if(data.vos[i].voteNum == (cnt)) {
		   			    var voteSum = data.vos[i].voteSum / voteCnt * 100;
		   			    insResult += "<div style='text-align:left;font-size:0.8em;'>투표자 : "+data.vos[i].voteSum+"명("+voteSum.toFixed(1)+"%) - "+subTitles[cnt-1]+"</div>";
		   			    insResult += "<div style='height:25px;'>"
		   			    insResult += "<div class='progress'>";
		   			    insResult += "<span class='progress-bar bg-info' style='width:"+(voteSum*5)+"'>"+subTitles[cnt-1]+"("+data.vos[i].voteSum+"명)"+"</span>";
		   			    insResult += "</div></div>";
		   			    break;
	   			    }
   			    }
    			}
				  var imsiVoteResult2 = "#voteResult2"+idx;
					$(imsiVoteResult2).html(insResult);
    		}
    	});
			
    }
    // 결과가리기2
    function votePopularHideResult2(idx) {
    	var voteResultHideBtn2 = "#voteResultHide2"+idx;
    	var voteResultShowBtn2 = "#voteResultShow2"+idx;
    	$(voteResultHideBtn2).hide();
    	$(voteResultShowBtn2).show();
    	var imsiVoteResult2 = "#voteResult2"+idx;
    	location.reload();
    }
    
    // 분석용 차트 새창으로 띄우기
    function chartAnalysis(idx,chartFlag) {
    	var url = "${ctp}/vote/chartAnalysis?idx="+idx+"&chartFlag="+chartFlag;
    	var winX = 770;
    	var winY = 500;
    	var x = (window.screen.width)/2 - winX/2;
    	var y = (window.screen.height)/2 - winY/2;
    	window.open(url, "chartWindow","width="+winX+",height="+winY+",left="+x+",top="+y);
    }
    
    // 전체 설문리스트 보여주기
    function voteTotalShow() {
    	$("#voteTotalHiddenBtn").show();
    	$("#voteTotalShowBtn").hide();
    	//$("#voteTotal").show();
    	$("#voteTotal").fadeIn();
    }
    // 전체 설문리스트 가리기
    function voteTotalHidden() {
    	$("#voteTotalHiddenBtn").hide();
    	$("#voteTotalShowBtn").show();
    	//$("#voteTotal").hide();
    	$("#voteTotal").fadeOut();
    }
  </script>
</head>
<body id="container">
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p></p>
<div class="container">
  <table class="table table-borderless" style="margin:0px;padding:0px;">
    <tr>
      <td colspan="2" style="text-align:center"><h2>진행중인 설문자료 리스트</h2></td>
    </tr>
  </table>
  <table class="table table-hover m-0 p-0">
    <thead class="thead-light">
      <tr style="text-align:center">
        <th>설문번호</th>
        <th>설문제목</th>
        <th>분류</th>
        <th>참여자수</th>
        <th>문항보기</th>
      </tr>
    </thead>
    <tbody id="voteQuestionTitleArea">
      <c:set var="curScrNo" value="1"/>
      <c:forEach var="vo" items="${voteVos}">
        <c:if test="${vo.progress eq 'ON'}">
	        <tr class="text-center">
	          <%-- <td>${curScrNo}</td> --%>
	          <td>${vo.idx}</td>
	          <td><b><a href="javascript:voteQuestionTitle(${vo.idx})" title="문항보기">${vo.title}</a></b></td>
	          <td>${vo.part}</td>
	          <td>${vo.voteCnt}</td>
	          <td>
	            <input type="button" value="투표하기" id="votePopularShowBtn${vo.idx}" class="btn btn-outline-secondary btn-sm showBtn" onclick="votePopularShow('${vo.idx}')"/>
	            <input type="button" value="투표하기" id="votePopularShowBtn2${vo.idx}" class="btn btn-outline-secondary btn-sm showBtn votePopularShowBtn2" onclick="votePopularShow2('${vo.idx}')"/>
	            <input type='button' value='문항접기' id='votePopularHide${vo.idx}' class='btn btn-outline-secondary btn-sm votePopularHide' onclick='votePopularHide(${vo.idx})'/>
	            <input type="button" value="결과보이기" id='voteResultShow${vo.idx}' class="btn btn-outline-secondary btn-sm resultShow" onclick="votePopularShowResult('${vo.idx}','${vo.voteCnt}')"/>
	            <input type="button" value="결과가리기" id='voteResultHide${vo.idx}' class="btn btn-secondary btn-sm resultHide" onclick="votePopularHideResult('${vo.idx}')"/>
	          </td>
	        </tr>
			    <tr>
			      <td colspan="5" class="m-0 p-0 text-center"><div id="voteQuestionTitle${vo.idx}"></div></td>
			    </tr>
			    <tr>
			      <td colspan="5" class="m-0 p-0 text-center"><div id="voteQuestion${vo.idx}"></div></td>
			    </tr>
			    <tr>
			      <td colspan="5" class="m-0 p-0 text-center"><div id="voteResult${vo.idx}"></div></td>
			    </tr>
			    <%-- 
			    <tr>
			      <td colspan="5" class="m-0 p-0 text-center"><div id="piechart${vo.idx}"></div></td>
			    </tr>
			     --%>
	        <c:set var="curScrNo" value="${curScrNo + 1}"/>
        </c:if>
      </c:forEach>
    </tbody>
  </table>
	<br/><hr/><br/>
	<input type="button" value="전체 설문리스트 보기"  id="voteTotalShowBtn"   class="btn btn-secondary" onclick="voteTotalShow()"/>
	<input type="button" value="전체 설문리스트 가리기" id="voteTotalHiddenBtn" class="btn btn-secondary" onclick="voteTotalHidden()"/>
	<div id="voteTotal">
	  <h2 class="text-center">전체 설문 리스트</h2>
	  <table class="table table-borderless">
	    <tr>
	      <td style="text-align:left; width:15%;">
	        <form name="partForm">
	          <select name="part" size="1" onchange="partCheck();" class="form-control">
	            <option value="전체" ${pageVO.part=='전체' ? 'selected' : ''}>전체</option>
	            <option value="여행" ${pageVO.part=='여행' ? 'selected' : ''}>여행</option>
	            <option value="학습" ${pageVO.part=='학습' ? 'selected' : ''}>학습</option>
	            <option value="건강" ${pageVO.part=='건강' ? 'selected' : ''}>건강</option>
	            <option value="기타" ${pageVO.part=='기타' ? 'selected' : ''}>기타</option>
	          </select>
	        </form>
	      </td>
	      <td style="text-align:right">
	        <button type="button" onclick="location.href='${ctp}/vote/voteInforInsert';" class="btn btn-secondary">돌아가기</button>
	      </td>
	    </tr>
	  </table>
	  <table class="table table-hover m-0 p-0">
	    <thead class="thead-light">
	      <tr style="text-align:center">
	        <th>설문번호</th>
	        <th>설문제목</th>
	        <th>분류</th>
	        <th>참여자수</th>
	        <th>진행여부</th>
	        <th>결과보기</th>
	      </tr>
	    </thead>
	    <tbody class='m-0 p-0'>
	      <%-- <c:set var="curScrStrarNo" value="${fn:length(vos)}"/> --%>
	      <c:set var="curScrStrarNo" value="${pageVO.curScrStrarNo}"/>
	      <c:forEach var="vo" items="${vos}">
	        <tr class="text-center">
	          <td>${curScrStrarNo}</td>
	          <td class="text-left"><b><a href="javascript:voteQuestionTitle2(${vo.idx})" title="문항보기">${vo.title}</a></b></td>
	          <td>${vo.part}</td>
	          <td>${vo.voteCnt}</td>
	          <td>
	            <c:if test="${vo.progress eq 'ON'}">진행중</c:if>
	            <c:if test="${vo.progress ne 'ON'}">진행종료</c:if>
	          </td>
	          <td>
	            <%-- <input type='button' value='문항접기' id='votePopularHide${vo.idx}' class='btn btn-outline-secondary btn-sm votePopularHide' onclick='votePopularHide(${vo.idx})'/> --%>
	            <%-- <input type="button" value="결과보이기" class="btn btn-secondary btn-sm" onclick="votePopularShowResult('${vo.idx}','${vo.thumbImg}')"/> --%>
	            <input type="button" value="결과보이기" id='voteResultShow2${vo.idx}' class="btn btn-outline-secondary btn-sm resultShow" onclick="votePopularShowResult2('${vo.idx}','${vo.voteCnt}')"/>
	            <input type="button" value="결과가리기" id='voteResultHide2${vo.idx}' class="btn btn-secondary btn-sm resultHide" onclick="votePopularHideResult2('${vo.idx}')"/>
	          </td>
	        </tr>
	        <tr>
			      <td colspan="6" class="m-0 p-0 text-center"><div id="voteQuestionTitle2${vo.idx}"></div></td>
			    </tr>
			    <tr>
			      <td colspan="6" class="m-0 p-0 text-center"><div id="voteQuestion${vo.idx}"></div></td>
			    </tr>
			    <tr>
			      <td colspan="6" class="m-0 p-0 text-center"><div id="voteResult2${vo.idx}"></div></td>
			    </tr>
			    <%-- 
			    <tr>
			      <td colspan="6" class="m-0 p-0 text-center"><div id="piechart2${vo.idx}"></div></td>
			    </tr>
			     --%>
	        <c:set var="curScrStrarNo" value="${curScrStrarNo - 1}"/>
	      </c:forEach>
	    </tbody>
	  </table>
	  <br/>
		<!-- 아래로 블록페이징 처리 -->
		<div class="container">
		  <c:set var="startPageNum" value="${pageVO.pag - (pageVO.pag-1)%pageVO.blockSize}"/>
		  <ul class="pagination justify-content-center pagination-sm">
		    <c:if test="${pageVO.pag != 1}">
		      <li class="page-item"><a href="voteList?pag=1" class="page-link" style="color:#777">첫페이지</a></li>
		      <li class="page-item"><a href="voteList?pag=${pageVO.pag-1}" class="page-link" style="color:#777">이전</a></li>
		    </c:if>
		    <c:forEach var="i" begin="0" end="2">  <!-- 하나의 블록페이지의 갯수를 3개로 지정함. -->
		      <c:if test="${(startPageNum+i) <= pageVO.totPage}">
		        <c:if test="${pageVO.pag == (startPageNum+i)}">
		          <li class="page-item"><a href="voteList?pag=${startPageNum+i}" class="page-link btn btn-secondary active" style="color:#fff">${startPageNum+i}</a></li>
		        </c:if>
		        <c:if test="${pageVO.pag != (startPageNum+i)}">
		          <li class="page-item"><a href="voteList?pag=${startPageNum+i}" class="page-link" style="color:#777">${startPageNum+i}</a></li>
		        </c:if>
		      </c:if>
		    </c:forEach>
		    <c:if test="${pageVO.pag != pageVO.totPage}">
		      <li class="page-item"><a href="voteList?pag=${pag+1}" class="page-link" style="color:#777">다음</a></li>
		      <li class="page-item"><a href="voteList?pag=${totPage}" class="page-link" style="color:#777">마지막페이지</a></li>
		    </c:if>
		  </ul>
		</div>
		<!-- 여기까지 블록페이징 처리 -->
  </div>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>