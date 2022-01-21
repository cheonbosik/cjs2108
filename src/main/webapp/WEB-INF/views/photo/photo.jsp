<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>photo.jsp(포토갤러리 초기화면)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
	  function sChange() {
	  	searchForm.searchString.focus();
	  }
	  
	  function sCheck() {
	  	var searchString = searchForm.searchString.value;
	  	if(searchString == "") {
	  		alert("검색어를 입력하세요");
	  		searchForm.searchString.focus();
	  	}
	  	else {
	  		searchForm.submit();
	  	}
	  }
	  
	  function photoPart() {
		  var part = document.getElementById("part").value;
		  location.href = "${ctp}/photo/photoPart?part="+part;
	  }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
	<h2>포 토 갤 러 리</h2>
	<hr/>
	<div>
	  <c:if test="${fn:length(vos) == 0}">
	  	<a href="${ctp}/photo/photoInput" class="btn btn-secondary p-1">사진올리기</a>
	  </c:if>
	</div>
	<p>
	  <span style="float:left;margin:0 0 15px 0;">- 최근 등록된 사진들을 랜덤순서로 보여줍니다.</span>
	</p>
	<div style="clear:both; text-align:center;">
	  <c:forEach var="vo" items="${vos}" begin="1" end="5">
		  <c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 20) %></c:set>
		  <c:set var="idx" value="${idxs[rand]}"/>
		  <span style="margin:15px;"><a href="${ctp}/photo/photoContent?idx=${idxs[rand]}"><img src="${ctp}/data/photo/${thumbnails[rand]}" width="150px" title="${titles[rand]}" style="cursor:pointer;"/></a></span>
	  </c:forEach>
	</div>
	<hr/>
	<div style="float:left;">
	  사진 분류 :
	  <select name="part" id="part" onchange="photoPart()">
	    <option value="전체" <c:if test="${part == '전체'}">selected</c:if>>전체</option>
	    <option value="학습" <c:if test="${part == '학습'}">selected</c:if>>학습</option>
	    <option value="여행" <c:if test="${part == '여행'}">selected</c:if>>여행</option>
	    <option value="음식" <c:if test="${part == '음식'}">selected</c:if>>음식</option>
	    <option value="영화" <c:if test="${part == '영화'}">selected</c:if>>영화</option>
	    <option value="기타" <c:if test="${part == '기타'}">selected</c:if>>기타</option>
	  </select>
	</div>
	
  <!-- 검색기 처리 시작 -->
	<div style="float:right">
	  <form name="searchForm" method="get" action="${ctp}/photo/photoSearch">
	    <b>검색 : </b>
	    <select name="search" onchange="sChange()">
	    	<option value="title" selected>글제목</option>
	    	<option value="name">글쓴이</option>
	    	<option value="content">글내용</option>
	    </select>
	    <input type="text" name="searchString"/>
	    <input type="button" value="검 색" onclick="sCheck()" class="btn btn-secondary p-1"/> &nbsp;
	    <a href="${ctp}/photo/photoInput" class="btn btn-secondary p-1">사진올리기</a>
	  </form>
	</div>
	<!-- 검색기 처리 끝 -->
	<br/>
	<hr style="clear:both;"/>
	<div>
	  <p>
	    <span style="float:left;">- 최근 갤러리에 등록된 ${imgCnt}건의 사진들입니다.</span>
	    <span style="float:right; margin:0px 0px 10px">
	      <c:if test="${photo == 'LIST' || empty photo}">
		      <a href="${ctp}/photo/photo?photo=CARD" class="btn btn-secondary">카드보기</a>
		      <a href="${ctp}/photo/photo?photo=SLIDE" class="btn btn-secondary">슬라이드보기</a>
	      </c:if>
	      <c:if test="${photo == 'CARD'}">
	      	<a href="${ctp}/photo/photo?photo=LIST" class="btn btn-secondary">리스트보기</a>
		      <a href="${ctp}/photo/photo?photo=SLIDE" class="btn btn-secondary">슬라이드보기</a>
	      </c:if>
	      <c:if test="${photo == 'SLIDE'}">
	      	<a href="${ctp}/photo/photo?photo=LIST" class="btn btn-secondary">리스트보기</a>
		      <a href="${ctp}/photo/photo?photo=CARD" class="btn btn-secondary">카드보기</a>
	      </c:if>
	    </span>
	  </p>
	  <c:if test="${photo == 'LIST' || empty photo}"><jsp:include page="photoList.jsp"/></c:if>
	  <c:if test="${photo == 'CARD'}"><jsp:include page="photoCard.jsp"/></c:if>
	  <c:if test="${photo == 'SLIDE'}"><jsp:include page="photoSlide.jsp"/></c:if>
	</div>
	
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>