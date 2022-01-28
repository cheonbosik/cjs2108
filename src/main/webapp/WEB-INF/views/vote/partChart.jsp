<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<select name="choiceChart" onchange="changeChart()">
  <option value="pie" <c:if test="${chartFlag=='pie'}"> selected </c:if>>원형차트보기</option>
  <option value="bar" <c:if test="${chartFlag=='bar'}"> selected </c:if>>막대차트보기</option>
  <option value="donut" <c:if test="${chartFlag=='donut'}"> selected </c:if>>성별투표수</option>
  <option value="line" <c:if test="${chartFlag=='line'}"> selected </c:if>>연령별투표수</option>
  <option value="area" <c:if test="${chartFlag=='area'}"> selected </c:if>>지역별</option>
  <option value="job" <c:if test="${chartFlag=='job'}"> selected </c:if>>직업별</option>
</select>