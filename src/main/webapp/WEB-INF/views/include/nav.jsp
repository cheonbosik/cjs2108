<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<script>
  function memDeleteCheck() {
	  var ans = confirm("정말 탈퇴 하시겠습니까?");
	  if(ans) {
		  ans = confirm("탈퇴하시게되면 1개월간 같은 아이디로는 재가입하실수 없습니다.\n탈퇴 하시겠습니까?");
		  if(ans) location.href = "${ctp}/member/memDelete";
	  }
  }
</script>
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-black w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="${ctp}/" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Guest</a>
    <c:if test="${sLevel <= 4}">
	    <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Board</a>
	    <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Pds</a>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study/fileUpload" class="w3-bar-item w3-button">파일업로드</a>
	        <a href="${ctp}/study/ajax/ajaxMenu" class="w3-bar-item w3-button">Ajax연습</a>
	        <a href="${ctp}/study/goods" class="w3-bar-item w3-button">상품등록</a>
	        <a href="${ctp}/study/uuid" class="w3-bar-item w3-button">UUID연습</a>
	        <a href="${ctp}/study/aria" class="w3-bar-item w3-button">ARIA암호화</a>
	        <a href="${ctp}/study/calendar" class="w3-bar-item w3-button">인터넷달력</a>
	        <a href="${ctp}/mail/mailForm" class="w3-bar-item w3-button">메일연습</a>
	        <a href="${ctp}/sessionShop/shopList" class="w3-bar-item w3-button">세션장바구니</a>
	        <a href="${ctp}/dbShop/dbShopList" class="w3-bar-item w3-button">미니쇼핑몰</a>
	        <a href="${ctp}/study/mainImage" class="w3-bar-item w3-button">메인이미지관리</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study2 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study2/thumbnail" class="w3-bar-item w3-button">썸네일연습</a>
	        <a href="${ctp}/photo/photo" class="w3-bar-item w3-button">포토갤러리</a>
	        <a href="${ctp}/study2/kakaomap" class="w3-bar-item w3-button">카카오맵</a>
	        <a href="${ctp}/study2/chart" class="w3-bar-item w3-button">구글차트</a>
	        <a href="${ctp}/study2/qrCode" class="w3-bar-item w3-button">QR코드생성</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">${sNickName} <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/member/memMain" class="w3-bar-item w3-button">회원메인</a>
	        <a href="${ctp}/schedule/schedule" class="w3-bar-item w3-button">일정관리</a>
	        <a href="${ctp}/dbShop/dbMyOrder" class="w3-bar-item w3-button">주문조회</a>
	        <a href="${ctp}/dbShop/dbCartList" class="w3-bar-item w3-button">장바구니</a>
	        <a href="${ctp}/member/memList" class="w3-bar-item w3-button">회원리스트</a>
	        <a href="${ctp}/member/memPwdCheck" class="w3-bar-item w3-button">정보수정</a>
	        <a href="javascript:memDeleteCheck()" class="w3-bar-item w3-button">회원탈퇴</a>
	        <c:if test="${sLevel == 0}">
	          <a href="${ctp}/admin/adMenu" class="w3-bar-item w3-button">관리자메뉴</a>
	        </c:if>
	      </div>
	    </div>
	  </c:if>
	  <a href="${ctp}/vote/voteInforInsert/" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Vote</a>
    <c:if test="${empty  sLevel}"><a href="${ctp}/member/memLogin" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Login</a></c:if>
    <c:if test="${!empty sLevel}"><a href="${ctp}/member/memLogout" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Logout</a></c:if>
    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
  </div>
</div>

<!-- 햄버거메뉴 -->
<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Guest</a>
  <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Board</a>
  <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Pds</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Study</a>
</div>