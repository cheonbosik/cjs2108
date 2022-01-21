<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>photoContent.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    function photoDelete(idx) {
    	var ans = confirm("현 게시물을 삭제하시겠습니까?");
    	if(ans) {
    		location.href = "${ctp}/photo/photoDelete?idx=${vo.idx}";
    	}
    }
  </script>
  <style>
    th {background-color: #eee;}
    img {
      width:100%;
      /* height:auto; */
      object-fit:cover;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <div>
    <div><h3>포토갤러리(<font color="blue">${vo.part}</font>)</h3></div>
    <hr/>
    <div style="text-align:right;margin:0 0 10px 0;"><input type="button" value="돌아가기" onclick="history.back()" class="btn btn-secondary"/></div>
    <table class="table table-bordered">
      <tr>
        <th>올린이</th>
        <td>${vo.name}</td>
        <th>조회수</th>
        <td>${vo.readNum}</td>
      </tr>
      <tr>
        <th>제 목</th>
        <td colspan="3">${vo.title}</td>
      </tr>
      <tr>
        <th>올린날짜</th>
        <td>${vo.WDate}</td>
        <th>아이피</th>
        <td>${vo.hostIp}</td>
      </tr>
      <tr>
        <th colspan="4">상세내역</th>
      </tr>
      <tr>
        <td colspan="4">${fn:replace(vo.content, newLine, '<br/>')}</td>
      </tr>
    </table>
    <br/>
    <div>
      <c:if test="${vo.name eq sNickName}">
	      <input type="button" value="삭제" onclick="photoDelete()" class="btn btn-secondary"/> &nbsp;
      </c:if>
     	<input type="button" value="돌아가기" onclick="history.back()" class="btn btn-secondary"/>
    </div>
  </div>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>