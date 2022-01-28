<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memMain.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br></p>
<div class="container">
  <h2>정규 회원방</h2>
  <hr/>
  <p><font color="blue">${sNickName}</font>님 로그인 중이십니다.</p>
  <p>현재 <font color="red">${sStrLevel}</font> 이십니다.</p>
  <p>최종 접속일 : <font color="blue">${sLastDate}</font></p>
  <p>총 방문횟수 : <font color="blue">${vo.visitCnt}</font> 회</p>
  <p>오늘 방문횟수 : <font color="blue">${vo.todayCnt}</font> 회</p>
  <p>총 포인트 : <font color="blue">${vo.point}</font> : 1일 1회 방문시 포인트 적립은 10포인트이고, 최대 50포인트까지 적립됩니다.</p>
  <hr/>
  <div>쇼핑몰 관련 :
    <button type="button" onclick="location.href='${ctp}/dbShop/dbCartList';" class="btn btn-outline-secondary btn-sm">장바구니</button> &nbsp;&nbsp;
    <button type="button" onclick="location.href='${ctp}/dbShop/dbMyOrder';" class="btn btn-outline-secondary btn-sm">주문내역</button>
  </div>
  <hr/>
  <p>활동내역 :<br/><br/>
  	방명록에 올린글수 : <font color="blue">${guestCnt}</font> 개 : 성명이나 아이디나 닉네임이 일치하는 자료의 갯수를 불러온다.<br/>
  	게시판에 올린글수 : <font color="blue">${boardCnt}</font> 개<br/>
  	자료실 업로드 횟수 : <font color="blue">${pdsCnt}</font>개<br/>
  </p>
  <hr/>
  
</div>
<br/>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>