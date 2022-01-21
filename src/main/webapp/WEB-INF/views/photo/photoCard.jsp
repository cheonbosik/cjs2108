<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<style>
	img {filter: drop-shadow(4px 4px 4px gray); margin-bottom:6px}
</style>
<div style="clear:both;"><hr/></div>
  <div>
  	<c:forEach var="vo" items="${vos}" begin="0" end="${imgCnt - 1}" varStatus="st">
	    <div style="text-align:center;float:left;padding:15px;margin-left:40px;">
	      <a href="${ctp}/photo/photoContent?idx=${vo.idx}"><img src="${ctp}/photo/${vo.thumbnail}" width="200px" title="${vo.title}"/></a><br/>
	      <a href="${ctp}/photo/photoContent?idx=${vo.idx}">${fn:substring(vo.title,0,11)}</a>
	    </div>
	  </c:forEach>
  </div>
<div style="clear:both;"><hr/></div>