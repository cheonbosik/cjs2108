<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<div style="clear:both;">
  <table class="table table-hover">
    <tr style="text-align:center;" class="table-dark text-dark">
      <th>썸네일</th>
      <th>제목</th>
      <th>올린이</th>
      <th>분류</th>
      <th>올린날짜</th>
      <th>조회수</th>
    </tr>
    <c:forEach var="vo" items="${vos}" begin="0" end="${imgCnt - 1}" varStatus="st">
      <tr style="text-align:center;">
	      <td><a href="${ctp}/photo/photoContent?idx=${vo.idx}"><img src="${ctp}/photo/${vo.thumbnail}" width="100px" title="${vo.title}"/></a></td>
	      <td><a href="${ctp}/photo/photoContent?idx=${vo.idx}">${vo.title}</a></td>
	      <td>${vo.name}</td>
	      <td>${vo.part}</td>
	      <td>${fn:substring(vo.WDate,0,10)}</td>
	      <td>${vo.readNum}</td>
      </tr>
    </c:forEach>
  </table>
</div>