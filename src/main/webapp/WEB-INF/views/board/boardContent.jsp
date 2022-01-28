<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>boardContent.jsp</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    // 전체 댓글 보이기/가리기
    $(document).ready(function(){
    	$("#reply").show();
    	$("#replyViewBtn").hide();
    	
    	$("#replyViewBtn").click(function(){
    		$("#reply").slideDown(500);
    		$("#replyViewBtn").hide();
    		$("#replyHiddenBtn").show();
    	});
    	$("#replyHiddenBtn").click(function(){
    		$("#reply").slideUp(500);
    		$("#replyViewBtn").show();
    		$("#replyHiddenBtn").hide();
    	});
    });
  
    // 게시글 삭제처리
    function delCheck() {
    	var ans = confirm("게시글을 삭제하시겠습니까?");
    	if(ans) location.href="${ctp}/board/boardDelete?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}&lately=${lately}";
    }
    
    // 좋아요 처리 - 1
    function goodCheck(flag) {
    	var query = {
    			idx : ${vo.idx},
    			flag: flag,
    			good: ${vo.good}
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/board/boardGood",
    		data : query,
    		success:function() {
    			location.reload();
    		}
    	});
    }
    
    // 좋아요 처리 - 2
    function goodCheck3() {
    	var query = {
    			idx : ${vo.idx}
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/boGood3",
    		data : query,
    		success:function(data) {
    			if(data == "1") {
    				alert("이미 좋아요를 클릭하셨습니다.");
    			}
    			else {
    				location.reload();
    			}
    		}
    	});
    }
    
    // 댓글 입력처리
    function replyCheck() {
    	var boardIdx = "${vo.idx}";
    	var mid = "${sMid}";
    	var nickName = "${sNickName}";
    	<%-- var hostIp = <%=request.getRemoteAddr()%>; --%>
    	var hostIp = "${pageContext.request.remoteAddr}";
    	var content = replyForm.content.value;
    	if(content == "") {
    		alert("댓글을 입력하세요?");
    		replyForm.content.focus();
    		return false;
    	}
    	var query = {
    			boardIdx : boardIdx,
    			mid      : mid,
    			nickName : nickName,
    			hostIp   : hostIp,
    			content  : content
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/board/boardReplyInsert",
    		data : query,
    		success:function() {
    			location.reload();
    		}
    	});
    }
    
    // 답변글(부모댓글의 댓글)
    function insertReply(idx,level,levelOrder,nickName) {
    	var insReply = "";
    	insReply += "<div class='container'><table style='width:90%' class='m-2 p-0'>";
    	insReply += "  <tr>";
    	insReply += "    <td class='p-0 text-left'>";
    	/* insReply += "    <div class='form-group'>"; */
    	/* insReply += "<label for='content'>답변 댓글 달기:</label> &nbsp;"; */
    	insReply += "답변 댓글 달기: &nbsp;";
    	insReply += "<input type='text' name='nickName' size='6' value='${sNickName}' readonly  class='p-0'/>";
    	/* insReply += "</div>"; */
    	insReply += "</td>";
    	insReply += "<td class='text-right p-0'>";
    	insReply += "<input type='button' value='답글달기' onclick='replyCheck2("+idx+","+level+","+levelOrder+")'/>";
    	insReply += "</td>";
    	insReply += "</tr>";
    	insReply += "<tr>";
    	insReply += "<td colspan='2' class='text-center p-0'>";
    	/* insReply += "<div>"; */
    	insReply += "<textarea rows='3' class='form-control' name='content' id='content"+idx+"' class='p-0'>@"+nickName+"\n</textarea>";
    	/* insReply += "</div>"; */
    	insReply += "</td>";
    	insReply += "</tr>";
    	insReply += "</table></div>";
    	/* insReply += "<hr style='margin:0px'/>"; */
    	
    	$("#replyBoxOpenBtn"+idx).hide();
    	$("#replyBoxCloseBtn"+idx).show();
    	$("#replyBox"+idx).slideDown(500);
    	$("#replyBox"+idx).html(insReply);
    }
    
    // 대댓글 입력폼 닫기 처리(대댓글에 해당하는 가상 테이블을 보이지 않게 처리한다.)
    function closeReply(idx) {
    	$("#replyBoxOpenBtn"+idx).show();
    	$("#replyBoxCloseBtn"+idx).hide();
    	$("#replyBox"+idx).slideUp(500);
    }
    
    // 대댓글 저장하기
    function replyCheck2(idx, level, levelOrder) {
    	var boardIdx = "${vo.idx}";
    	var mid = "${sMid}";
    	var nickName = "${sNickName}";
    	var hostIp = "${pageContext.request.remoteAddr}";
    	var content = "content"+idx;
    	//var contentVal = document.getElementById(content).value;
    	var contentVal = $("#"+content).val();
    	
    	if(content == "") {
    		alert("대댓글(답변글)을 입력하세요?");
    		$("#"+content).focus();
    		return false;
    	}
    	var query = {
    			boardIdx : boardIdx,
    			mid      : mid,
    			nickName : nickName,
    			hostIp   : hostIp,
    			content  : contentVal,
    			level    : level,
    			levelOrder:levelOrder
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/board/boardReplyInsert2",
    		data : query,
    		success:function() {
    			location.reload();
    		}
    	});
    }
    
    // 댓글 삭제처리
    function replyDelCheck(replyIdx) {
    	var ans = confirm("선택하신 댓글을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/board/boardReplyDelete",
    		data : {replyIdx : replyIdx},
    		success:function() {
    			location.reload();
    		},
    		error : function() {
    			alert("전송오류");
    		}
    	});
    }
  </script>
  <style>
    th {
      background-color:#ddd;
      text-align: center;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br></p>
<div class="container">
  <h2 style="text-align:center">글 내 용 보 기</h2>
  <br/>
  <table class="table table-bordered">
    <tr>
      <th>글쓴이</th>
      <td>${vo.nickName}</td>
      <th>글쓴날짜</th>
      <td>${fn:substring(vo.WDate,0,19)}</td>
    </tr>
    <tr>
      <th>이메일</th>
      <td>${vo.email}</td>
      <th>조회수</th>
      <td>${vo.readNum}</td>
    </tr>
    <tr>
      <th>홈페이지</th>
      <td>
        <c:if test="${fn:length(vo.homePage)>10}"><a href="${vo.homePage}" target="_blank">${vo.homePage}</a></c:if>
        <c:if test="${fn:length(vo.homePage)<=10}">없음</c:if>
      </td>
      <th>접속IP</th>
      <td>${vo.hostIp}</td>
    </tr>
    <tr>
      <th>좋아요</th>
      <td colspan="3">
        ❤(${vo.good}) : 
        <a href="javascript:goodCheck(1)">👍</a>&nbsp;<a href="javascript:goodCheck(-1)">👎</a>
      </td>
    </tr>
    <tr>
      <th>글제목</th>
      <td colspan="3">${vo.title} &nbsp;&nbsp;<a href="javascript:goodCheck3()">😍</a>(${vo.good})</td>
    </tr>
    <tr>
      <th>글내용</th>
      <td colspan="3" style="height:200px">${fn:replace(vo.content,newLine,'<br/>')}</td>
    </tr>
    <tr>
      <td colspan="4" class="text-center">
        <c:if test="${sw != 'search'}">
	        <input type="button" value="돌아가기" onclick="location.href='${ctp}/board/boardList?pag=${pag}&pageSize=${pageSize}&lately=${lately}';"/>
	        <c:if test="${sMid == vo.mid}">
	          <input type="button" value="수정하기" onclick="location.href='${ctp}/board/boardUpdate?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}&lately=${lately}';"/>
	          <input type="button" value="삭제하기" onclick="delCheck()"/>
	        </c:if>
        </c:if>
        <c:if test="${sw == 'search'}">
        	<input type="button" value="돌아가기" onclick="history.back()"/>
        </c:if>
      </td>
    </tr>
  </table>
  
  <c:if test="${sw != 'search'}">
  <!-- 이전글/다음글 처리 -->
	  <table class="table table-borderless">
	    <tr>
	      <td>
	        <c:if test="${!empty pnVos[1]}">
		        👆 <a href="${ctp}/board/boardContent?idx=${pnVos[1].idx}&pag=${pag}&pageSize=${pageSize}&lately=${lately}">다음글 : ${pnVos[1].title}</a><br/>
	        </c:if>
	        <c:if test="${!empty pnVos[0]}">
		        👇 <a href="${ctp}/board/boardContent?idx=${pnVos[0].idx}&pag=${pag}&pageSize=${pageSize}&lately=${lately}">이전글 : ${pnVos[0].title}</a><br/>
	        </c:if>
	      </td>
	    </tr>
	  </table>
  </c:if>
  
  <div class="container text-center mb-3">
    <input type="button" value="댓글보이기" id="replyViewBtn" class="btn btn-secondary"/>
    <input type="button" value="댓글가리기" id="replyHiddenBtn" class="btn btn-secondary"/>
  </div>
  <!-- 댓글 출력/입력 처리부분 -->
  <!-- 댓글 출력 -->
  <div id="reply">
	  <table class="table table-hover m-0 p-0 table-borderless">
	    <tr>
	      <th class="text-left pl-4">작성자</th>
	      <th>댓글내용</th>
	      <th>작성일자</th>
	      <th>접속IP</th>
	      <th>답글</th>
	    </tr>
	    <c:forEach var="rVo" items="${rVos}">
	      <tr>
	        <c:if test="${rVo.level <= 0 }">   <!-- 부모댓글은 들여쓰기 하지 않는다. -->
	          <td>
	            ${rVo.nickName}
	            <c:if test="${rVo.mid == sMid}">
	              <a href="javascript:replyDelCheck(${rVo.idx});"><span class="glyphicon glyphicon-remove"></span></a>
	            </c:if>
	          </td>
	        </c:if>
	        <c:if test="${rVo.level > 0 }">	<!-- 하위 댓글은 들여쓰기 한다. -->
		        <td>
		          <c:forEach var="i" begin="1" end="${rVo.level}">&nbsp;&nbsp; </c:forEach>
		            └ ${rVo.nickName}
		          <c:if test="${rVo.mid == sMid}">
	              <a href="javascript:replyDelCheck(${rVo.idx});"><span class="glyphicon glyphicon-remove"></span></a>
	            </c:if>
		        </td>
	        </c:if>
	        <td>
	          ${rVo.content}
	        </td>
	        <td>${rVo.WDate}</td>
	        <td>${rVo.hostIp}</td>
	        <td class="m-0 pt-2">
	          <input type="button" value="답글" onclick="insertReply('${rVo.idx}','${rVo.level}','${rVo.levelOrder}','${rVo.nickName}')" id="replyBoxOpenBtn${rVo.idx}" class="p-0"/>
	          <input type="button" value="닫기" onclick="closeReply(${rVo.idx})" id="replyBoxCloseBtn${rVo.idx}" class="replyBoxClose p-0" style="display:none"/>
	        </td>
	      </tr>
	      <tr>
	      	<td colspan="5" class="m-0 p-0" style="border-top:none;"><div id="replyBox${rVo.idx}"></div></td>
	      </tr>
	    </c:forEach>
	  </table>
	</div>
  <!-- 댓글 입력 -->
  <form name="replyForm">
	  <table class="table">
	  	<tr>
	  	  <td style="width:90%">
	  	    글내용 :
	  	    <textarea rows="4" name="content" id="content" class="form-control"></textarea>
	  	  </td>
	  	  <td style="width:10%">
	  	    <br/><p>작성자 :<br/>${sNickName}</p>
	  	    <p>
	  	      <input type="button" value="댓글달기" onclick="replyCheck()"/>
	  	      <%-- <input type="button" value="댓글수정" onclick="replyUpdateCheck(${replyIdx})"/> --%>
	  	    </p>
	  	  </td>
	  	</tr>
	  </table>
	  <input type="hidden" name="boardIdx" value="${vo.idx}"/>
	  <input type="hidden" name="mid" value="${sMid}"/>
	  <input type="hidden" name="nickName" value="${sNickName}"/>
	  <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}"/>
	  <input type="hidden" name="pag" value="${pag}"/>
	  <input type="hidden" name="pageSize" value="${pageSize}"/>
	  <input type="hidden" name="lately" value="${lately}"/>
  </form>
</div>
<br/>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>