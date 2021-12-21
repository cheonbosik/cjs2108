<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>guestList.jsp(방명록리스트_블록페이징처리)</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    function delCheck(idx) {
    	var ans = confirm("현재 방문소감 글을 삭제하시겠습니까?");
    	if(ans) location.href="<%=request.getContextPath()%>/GDelete?idx="+idx;
    }
  </script>
  <style>
    th {
    	background-color: #ccc;
    	text-align: center;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p></p>
<div class="container">
  <table class="table table-borderless">
    <tr>
      <td colspan="2" style="text-align:center;"><h2>방 명 록 리 스 트</h2></td>
    </tr>
    <tr>
      <td>
        <a href="<%=request.getContextPath()%>/guest/guestInput" class="btn btn-secondary">글쓰기</a>
      </td>
      <td style="text-align:right;">
        <!-- 페이징처리 시작 -->
				<!-- 페이징처리 끝 -->
      </td>
    </tr>
  </table>
  <c:forEach var="vo" items="${vos}" varStatus="st">
	  <table class="table table-borderless">
	    <tr>
	      <td>
	        방문번호 : ${curScrStrarNo} &nbsp;
	      </td>
	      <td style="text-align:right;">
	        방문IP : ${vo.hostIp}
	      </td>
	    </tr>
	  </table>
	  <table class="table table-bordered">
	    <tr>
	      <th style="width:20%;">성명</th>
	      <td style="width:25%;">${vo.name}</td>
	      <th style="width:20%;">방문일자</th>
	      <td style="width:35%;">${vo.VDate}</td>
	    </tr>
	    <tr>
	      <th>전자우편</th>
	      <td colspan="3">${vo.email}</td>
	    </tr>
	    <tr>
	      <th>홈페이지</th>
	      <td colspan="3">
	        <c:set var="hpLen" value="${fn:length(vo.homePage)}"/>
	      	<c:if test="${empty vo.homePage || hpLen < 7}">없음</c:if>
	      	<c:if test="${!empty vo.homePage && hpLen >= 7}"><a href="${vo.homePage}">${vo.homePage}</a></c:if>
	      </td>
	    </tr>
	    <tr>
	      <th>글내용</th>
	      <td colspan="3">${fn:replace(vo.content,newLine,'<br/>')}</td>
	    </tr>
	  </table>
	  <br/>
	  <c:set var="curScrStrarNo" value="${curScrStrarNo - 1}"/>
	</c:forEach>
</div>
<!-- 블록 페이징처리 시작(BS4 스타일적용) -->
<div class="container">
	<ul class="pagination justify-content-center">
		<c:if test="${totPage == 0}"><p style="text-align:center"><b>자료가 없습니다.</b></p></c:if>
		<c:if test="${totPage != 0}">
		  <c:if test="${pag != 1}">
		    <li class="page-item"><a href="${ctp}/guest/guestList?pag=1" title="첫페이지" class="page-link text-secondary">◁◁</a></li>
		  </c:if>
		  <c:if test="${curBlock > 0}">
		    <li class="page-item"><a href="${ctp}/guest/guestList?pag=${(curBlock-1)*blockSize + 1}" title="이전블록" class="page-link text-secondary">◀</a></li>
		  </c:if>
		  <c:forEach var="i" begin="${(curBlock*blockSize)+1}" end="${(curBlock*blockSize)+blockSize}">
		    <c:if test="${i == pag && i <= totPage}">
		      <li class="page-item active"><a href='${ctp}/guest/guestList?pag=${i}' class="page-link text-light bg-secondary border-secondary">${i}</a></li>
		    </c:if>
		    <c:if test="${i != pag && i <= totPage}">
		      <li class="page-item"><a href='${ctp}/guest/guestList?pag=${i}' class="page-link text-secondary">${i}</a></li>
		    </c:if>
		  </c:forEach>
		  <c:if test="${curBlock < lastBlock}">
		    <li class="page-item"><a href="${ctp}/guest/guestList?pag=${(curBlock+1)*blockSize + 1}" title="다음블록" class="page-link text-secondary">▶</a>
		  </c:if>
		  <c:if test="${pag != totPage}">
		    <li class="page-item"><a href="${ctp}/guest/guestList?pag=${totPage}" title="마지막페이지" class="page-link" style="color:#555">▷▷</a>
		  </c:if>
		</c:if>
	</ul>
</div>
<!-- 블록 페이징처리 끝 -->
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>