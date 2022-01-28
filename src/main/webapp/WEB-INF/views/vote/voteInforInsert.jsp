<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="UTF-8">
  <title>voteInforInsert.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script>
	  function fCheck() {
		  var gender = myform.gender.value;
		  var age = myform.age.value;
		  var job = document.getElementById("job").value;
		  var area = myform.area.value;
		  if(gender == "") {
			  alert("성별을 선택하세요!");
		  }
		  else if(age == "") {
			  alert("나이를 선택하세요!");
		  }
		  else if(job == "") {
			  alert("직업을 선택하세요!");
		  }
		  else if(area == "") {
			  alert("지역을 선택하세요!");
		  }
		  else {
			  myform.submit();
		  }
	  }
	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <form name="myform" method="post" class="w3-container w3-card-4 w3-light-grey w3-text-black w3-margin">
		<h2 class="w3-center">투표 정보 등록(회원 정보 확인)</h2>
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:60px">성 별</div>
      <input class="w3-radio" type="radio" name="gender" value="남자" <c:if test="${memberVO.gender=='남자'}">checked</c:if>><label>남자</label>&nbsp;
		  <input class="w3-radio" type="radio" name="gender" value="여자" <c:if test="${memberVO.gender=='여자'}">checked</c:if>><label>여자</label>
		</div>
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:60px">연 령</div>
      <input class="w3-radio" type="radio" name="age" value="10대" ${memberVO.ageGroup == 10 ? 'checked' : ''}><label>10대</label>&nbsp;
      <input class="w3-radio" type="radio" name="age" value="20대" ${memberVO.ageGroup == 20 ? 'checked' : ''}><label>20대</label>&nbsp;
      <input class="w3-radio" type="radio" name="age" value="30대" ${memberVO.ageGroup == 30 ? 'checked' : ''}><label>30대</label>&nbsp;
      <input class="w3-radio" type="radio" name="age" value="40대" ${memberVO.ageGroup == 40 ? 'checked' : ''}><label>40대</label>&nbsp;
      <input class="w3-radio" type="radio" name="age" value="50대" ${memberVO.ageGroup == 50 ? 'checked' : ''}><label>50대</label>&nbsp;
		  <input class="w3-radio" type="radio" name="age" value="60대" ${memberVO.ageGroup == 60 ? 'checked' : ''}><label>60대</label>
		</div>
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:60px">직 업</div>
      <select class="w3-select w3-border" name="job" id="job">
		    <option value="" disabled selected>직업선택</option>
		    <option ${memberVO.job=='학생'  ? 'selected' : ''}>학생</option>
        <option ${memberVO.job=='회사원' ? 'selected' : ''}>회사원</option>
        <option ${memberVO.job=='공무원' ? 'selected' : ''}>공무원</option>
        <option ${memberVO.job=='군인'  ? 'selected' : ''}>군인</option>
        <option ${memberVO.job=='의사'  ? 'selected' : ''}>의사</option>
        <option ${memberVO.job=='법조인' ? 'selected' : ''}>법조인</option>
        <option ${memberVO.job=='세무인' ? 'selected' : ''}>세무인</option>
        <option ${memberVO.job=='자영업' ? 'selected' : ''}>자영업</option>
        <option ${memberVO.job=='기타'  ? 'selected' : ''}>기타</option>
		  </select>
		</div>
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:60px">지 역</div>
      <select class="w3-select w3-border" name="area">
		    <option value="" disabled selected>지역선택</option>
		    <option value="서울"   ${fn:substring(memberVO.address,6,8) == '서울' ? 'selected' : ''}>서울</option>
		    <option value="경기도" ${fn:substring(memberVO.address,6,8) == '경기' ? 'selected' : ''}>경기도</option>
		    <option value="강원도" ${fn:substring(memberVO.address,6,8) == '강원' ? 'selected' : ''}>강원도</option>
		    <option value="충청도" ${fn:substring(memberVO.address,6,8) == '충청' ? 'selected' : ''}>충청도</option>
		    <option value="전라도" ${fn:substring(memberVO.address,6,8) == '전라' ? 'selected' : ''}>전라도</option>
		    <option value="경상도" ${fn:substring(memberVO.address,6,8) == '경상' ? 'selected' : ''}>경상도</option>
		    <option value="제주도" ${fn:substring(memberVO.address,6,8) == '제주' ? 'selected' : ''}>제주도</option>
		  </select>
		</div>
		<button type="button" class="w3-button w3-block w3-section w3-gray w3-ripple w3-padding" onclick="fCheck()">전 송</button>
	</form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>