<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memLogin.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br></p>
<div class="container">
  <div class="modal-dialog">
    <div class="modal-content">
			<div class="container" style="padding:30px;">
			  <h2>회원 로그인</h2>
			  <p>회원 아이디와 비밀번호를 입력해 주세요</p>
			  <form name="myform" method="post" class="was-validated">
				  <div class="form-group">
				    <label for="mid">회원 아이디 :</label>
				    <input type="text" class="form-control" id="mid" placeholder="아이디를 입력하세요." name="mid" value="${mid}" required autofocus />
				    <div class="valid-feedback">정확한 아이디를 입력하세요.</div>
				    <div class="invalid-feedback">회원아이디는 필수 입력사항입니다.</div>
				  </div>
				  <div class="form-group">
				    <label for="pwd">비밀번호 :</label>
				    <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력하세요." name="pwd" maxlength="9" required />
				    <div class="valid-feedback">정확한 비밀번호를 입력하세요.</div>
				    <div class="invalid-feedback">비밀번호는 필수 입력사항입니다.</div>
				  </div>
				  <button type="submit" class="btn btn-primary">인증하기</button>&nbsp;
				  <button type="reset" class="btn btn-primary">취소</button>&nbsp;
				  <button type="button" onclick="location.href='${ctp}/';" class="btn btn-primary">돌아가기</button>&nbsp;
				  <button type="button" onclick="location.href='${ctp}/member/memInput';" class="btn btn-primary">회원가입</button><br/>
				  <div class="row" style="font-size:12px;">
				    <span class="col mt-3"><input type="checkbox" name="idCheck" checked/> 아이디 저장</span>
				    <span class="col mt-3">[<a href="${ctp}/member/idConfirm">아이디찾기</a>] / [<a href="${ctp}/member/pwdConfirm">비밀번호찾기</a>]</span>
				  </div>
				</form>
			</div>
		</div>
	</div>
</div>
<br/>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>