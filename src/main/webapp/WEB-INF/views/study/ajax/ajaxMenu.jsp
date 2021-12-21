<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ajaxMenu.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br></p>
<div class="container">
  <h2>AJax 연습</h2>
  <hr/>
  <p>
    <a href="${ctp}/study/ajax/ajaxTest1" class="btn btn-secondary">시(도)/구(동)(HashMap)</a> &nbsp;
    <a href="${ctp}/study/ajax/ajaxTest2" class="btn btn-secondary">시(도)/구(동)(ArrayList)</a> &nbsp;
    <a href="${ctp}/study/ajax/ajaxTest3" class="btn btn-secondary">시(도)/구(동)(String)</a>
  </p>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>