<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsList.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
	  function partCheck() {
	  	var part = partForm.part.value;
	  	location.href="${ctp}/pds/pdsList?pag=${pag}&part="+part;
	  }
	  
		$(function() {
			$(".downCheck").on("click",function(){
		  	var idx = $(this).attr("value");
		    	var query = {idx : idx}
		    	$.ajax({
		    		url   : "${ctp}/pds/downCheck",
		    		type  : "get",
		    		data  : query,
		    		success : function(data) {
	    				location.reload();
		    		}
		    	});
			});
		});
		
		function newWindow(idx) {
			var url = "${ctp}/pds/pdsContent?idx="+idx;
			window.open(url,"winOpen","width=450px,height=600px");
		}
		
		function deleteCheck(idx) {
			var ans = confirm("현재 파일을 삭제처리하시겠습니까?");
			if(ans) {
				$(".deletePwd").hide();
				var str = "";
				str += "<div class='text-center deletePwd' id='deletePwd"+idx+"'>"
				str += "현자료의 비밀번호 : "
				str += "<input type='password' name='pwd' id='pwd"+idx+"' autofocus />"
				str += "<input type='button' value='확인' onclick='pwdCheck("+idx+")'/>"
				str += "</div>"
				$("#deleteForm"+idx).html(str);
			}
		}
		
		function pwdCheck(idx) {
			var pwd = $("#pwd"+idx).val();
			var query = {
					idx : idx,
					pwd : pwd
			}
			$.ajax({
				type : "post",
				url  : "${ctp}/pds/pdsPwdCheck",
				data : query,
				success:function(data) {
					if(data != '1')	alert('비밀번호가 틀립니다.');
					else {
						alert('삭제 되었습니다.');
						location.reload();
					}
				},
				error : function() {
					alert("전송오류!");
				}
			});
		}
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container" id="container">
  <table class="table">
    <tr>
      <td colspan="2" style="text-align:center;border:none;"><h2>멀 티 자 료 실 리 스 트</h2></td>
    </tr>
    <tr>
  		<td style="text-align:left;border:none;width:15%;">
  		  <form name="partForm">
  		    <select name="part" onchange="partCheck()" class="form-control">
  		      <option value="전체" ${part=='전체' ? 'selected' : ''}>전체</option>
  		      <option value="학습" ${part=='학습' ? 'selected' : ''}>학습</option>
  		      <option value="여행" ${part=='여행' ? 'selected' : ''}>여행</option>
  		      <option value="음식" ${part=='음식' ? 'selected' : ''}>음식</option>
  		      <option value="기타" ${part=='기타' ? 'selected' : ''}>기타</option>
  		    </select>
  		  </form>
  		</td>
  		<td style="text-align:right;border:none;">
  		  <button type="button" onclick="location.href='${ctp}/pds/pdsInput';" class="btn btn-secondary">자료올리기</button>
  		</td>
    </tr>
  </table>
  <table class="table table-hover">
    <tr style="text-align:center;background-color:#ccc;">
      <th>번호</th>
      <th>자료제목</th>
      <th>올린이</th>
      <th>올린날자</th>
      <th>파일명(용량)</th>
      <th>분류</th>
      <th>다운횟수</th>
      <th>다운로드</th>
    </tr>
    <c:set var="curScrStartNo" value="${curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}">
	    <tr style="text-align:center;">
	      <td>${curScrStartNo}</td>
	      <td><a href="javascript:newWindow(${vo.idx});">${vo.title}</a></td>
	      <td>${vo.nickName}</td>
	      <td>${fn:substring(vo.FDate,0,10)}</td>
	      <td>
	        <c:if test="${vo.openSw == '공개' || smid == vo.mid || slevel == 0}">
		        <c:set var="FNames" value="${fn:split(vo.FName,'/')}"/>
		        <c:set var="FSNames" value="${fn:split(vo.FSName,'/')}"/>
		        <c:forEach var="FName" items="${FNames}" varStatus="st">
		          <a href="${ctp}/pds/${FSNames[st.index]}" class="downCheck" value="${vo.idx}" download>${FName}</a> /
		        </c:forEach>
		        <br/>(<fmt:formatNumber value="${vo.FSize / 1024}" pattern="#,###.#"/>KB)
		      </c:if>
		      <c:if test="${vo.openSw != '공개' && smid != vo.mid && slevel != 0}">
		        :비공개:
		      </c:if>
	      </td>
	      <td>${vo.part}</td>
	      <td>${vo.downNum}</td>
	      <td>
	        <c:if test="${vo.openSw == '공개' || smid == vo.mid || slevel == 0}">
		        <input type="button" value="다운" onclick="location.href='${ctp}/pds/pdsDown?idx=${vo.idx}&fName=${vo.FName}&fSName=${vo.FSName}&title=${vo.title}';"/>
		        <input type="button" value="삭제" onclick="deleteCheck(${vo.idx})"/>
		      </c:if>
		      <c:if test="${vo.openSw != '공개' && sMid != vo.mid && sLevel != 0}">
		        :비공개:
		      </c:if>
	      </td>
	    </tr>
	    <c:set var="curScrStartNo" value="${curScrStartNo-1}"/>
	    <tr><td colspan="8"><div id="deleteForm${vo.idx}"></div></td></tr>
    </c:forEach>
  </table>
</div>
<!-- 블록 페이징처리 시작 -->
<div class="container" style="text-align:center">
  <ul class="pagination justify-content-center">
	  <c:set var="startPageNum" value="${pag - (pag-1)%blockSize}"/>
	  <c:if test="${pag != 1}">
	    <li class="page-item"><a href="${ctp}/pds/pdsList?pag=1&pageSize=${pageSize}&part=${part}" class="page-link" style="color:#666">◁◁</a></li>
	  </c:if>
	  <c:if test="${curBlock > 0}">
	    <li class="page-item"><a href="${ctp}/pds/pdsList?pag=${(curBlock-1)*blockSize + 1}&pageSize=${pageSize}&part=${part}" class="page-link" style="color:#666">◀</a></li>
	  </c:if>
	  <c:forEach var="i" begin="0" end="2">
	    <c:if test="${(startPageNum+i) <= totPage}">
	      <c:if test="${pag == (startPageNum+i)}">
	        <li class="page-item active"><a href="${ctp}/pds/pdsList?pag=${startPageNum+i}&pageSize=${pageSize}&part=${part}" class="page-link btn btn-secondary active" style="color:#666"><font color="#fff"><b>${startPageNum+i}</b></font></a></li>
	      </c:if>
	      <c:if test="${pag != (startPageNum+i)}">
	        <li class="page-item"><a href="${ctp}/pds/pdsList?pag=${startPageNum+i}&pageSize=${pageSize}&part=${part}" class="page-link" style="color:#666">${startPageNum+i}</a></li>
	      </c:if>
	    </c:if>
	  </c:forEach>
	  <c:if test="${curBlock < lastBlock}">
	    <li class="page-item"><a href="${ctp}/pds/pdsList?pag=${(curBlock+1)*blockSize + 1}&pageSize=${pageSize}&part=${part}" class="page-link" style="color:#666">▶</a></li>
	  </c:if>
	  <c:if test="${pag != totPage}">
	    <li class="page-item"><a href="${ctp}/pds/pdsList?pag=${totPage}&pageSize=${pageSize}&part=${part}" class="page-link" style="color:#666">▷▷</a></li>
	  </c:if>
	</ul>
</div>
<!-- 블록 페이징처리 끝 -->
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>