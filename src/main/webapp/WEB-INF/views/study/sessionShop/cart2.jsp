<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>cart.jsp(장바구니)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function orderCheck() {
    	alert("상품을 구매합니다.......-생략-");
    }
    function cartReset() {
    	var ans = confirm("주문을 취소 하시겠습니까?");
    	if(ans) location.href = "${ctp}/sessionShop/cartReset";
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br></p>
<div class="container">
  <h2>세션 장바구니 보기</h2>
  <hr/>
  <p>${sNickName}님께서 구매하신 품목입니다.</p>
  <hr/>
  <div>
    <c:set var="productCnt" value="${fn:length(productList)}"/>
    <c:set var="imsiProduct" value=""/>
    <c:set var="sw" value="0"/>		<!-- 아래 품목 반복비교처리부분을 처음 수행시 1회만 초기값을 지정해 주기 위해 사용한 변수 -->
    <p>구입한 품목의 총 수량 : ${productCnt}개</p>
    <hr/>
    <c:forEach var="product" items="${productList}">
    	<c:if test="${imsiProduct == product}">   <!-- 다음 상품을 읽었을때, 같은 상품이면 누적처리 -->
    	  <c:set var="cnt" value="${cnt + 1}"/>
    	</c:if>
    
      <c:if test="${imsiProduct != product}">		<!-- 다음 상품을 읽었을때, 같은 상품이아니면 기존상품을 출력하고 cnt는 다시 초기화(1)시킨다. 그리고, 새로운 상품을 imsiProduct에 담아준다. -->
	      <c:if test="${sw != 0}">
	        <p>
		        - 상품명 : <b>${imsiProduct}</b> / 수량 : ${cnt}<b>개</b>
		        <c:set var="imsiProduct" value="${product}"/>
		        <c:set var="cnt" value="1"/>
	        </p>
	      </c:if>
	      <c:if test="${sw == 0}">
	        <c:set var="imsiProduct" value="${product}"/>
	        <c:set var="cnt" value="1"/>
	        <c:set var="sw" value="1"/>
	      </c:if>
      </c:if>
    </c:forEach>
    <p>
      - 상품명 : <b>${imsiProduct}</b> / 수량 : ${cnt}<b>개</b>
    </p>
    <hr/>
    <p>
      <a href="${ctp}/sessionShop/shopList" class="btn btn-secondary">계속쇼핑하기</a> &nbsp;
      <a href="javascript:cartReset()" class="btn btn-secondary">전체주문취소</a> &nbsp;
      <a href="javascript:orderCheck()" class="btn btn-secondary">주문하기</a>
    </p>
  </div>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>