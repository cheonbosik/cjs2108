<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<div>
  <p>
    <a href="${ctp}/study2/kakaoEx1" class="btn btn-secondary">마커표시/DB저장</a>&nbsp;
    <a href="${ctp}/study2/kakaoEx2" class="btn btn-secondary">지명검색</a>&nbsp;
    <a href="${ctp}/study2/kakaoEx3" class="btn btn-secondary">DB에저장된지명검색/삭제</a>&nbsp;
    <a href="${ctp}/study2/kakaoEx4" class="btn btn-secondary">카테고리별 장소검색(DB)</a>&nbsp;
  </p>
</div>