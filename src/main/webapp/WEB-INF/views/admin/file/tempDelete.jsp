<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>title</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<p><br></p>
<div class="container">
  <h2>임시파일 삭제 처리</h2>
  <hr/>
  <p><a href="${ctp}/admin/boardTempDelete">게시판 임시파일 삭제</a></p>
  <p><a href="${ctp}/admin/productTempDelete">상품등록 임시파일 삭제</a></p>
  <p><a href="${ctp}/admin/dataTempDelete">데이터폴더의 임시파일 삭제</a></p>
  <hr/>
</div>
<br/>
</body>
</html>