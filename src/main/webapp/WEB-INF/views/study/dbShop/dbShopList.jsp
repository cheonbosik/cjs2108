<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbShopList.jsp(판매상품진열하기)</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
  <span>[<a href="${ctp}/dbShop/dbShopList">전체보기</a>]</span> /
  <c:forEach var="subTitle" items="${subTitleVos}" varStatus="st">
  	<span>[<a href="${ctp}/dbShop/dbShopList?part=${subTitle.categorySubName}">${subTitle.categorySubName}</a>]</span>
	  <c:if test="${!st.last}"> / </c:if>
  </c:forEach>
  <hr/><h4>상품 리스트 : <font color="brown"><b>${part}</b></font></h4><hr/>
  <c:set var="cnt" value="0"/>
  <div class="row mt-4">
    <c:forEach var="vo" items="${vos}">
      <div class="col-md-4">
        <div style="text-align:center">
          <a href="${ctp}/dbShop/dbShopContent?idx=${vo.idx}">
            <img src="${ctp}/dbShop/${vo.FSName}" width="200px" height="180px"/>
            <div><font size="2">${vo.productName}</font></div>
            <div><font size="2" color="orange"><fmt:formatNumber value="${vo.mainPrice}" pattern="#,###"/>원</font></div>
            <div><font size="2">${vo.detail}</font></div>
          </a>
        </div>
      </div>
      <c:set var="cnt" value="${cnt+1}"/>
      <c:if test="${cnt%3 == 0}">
        </div>
        <div class="row mt-5">
      </c:if>
    </c:forEach>
    <div class="container">
      <c:if test="${fn:length(vos) == 0}"><h3>제품 준비 중입니다.</h3></c:if>
    </div>
  </div>
  <br/>
  <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/dbShop/dbCartList';">장바구니보기</button>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>