<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbOrderConfirm.jsp(주문확인)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <link rel="stylesheet" href="${ctp}/css/cart.css?after">
  <script>
	  function nWin(orderIdx) {
	  	var url = "${ctp}/dbShop/dbOrderBaesong?orderIdx="+orderIdx;
	  	window.open(url,"dbOrderBaesong","width=350px,height=400px");
	  }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
  <h2>주문완료</h2>
  <hr/>
  <table class="table table-bordered">
    <tr style="text-align:center;background-color:#ccc;">
      <th>상품이미지</th>
      <th>주문일시</th>
      <th>주문내역</th>
      <th>비고</th>
    </tr>
    <c:forEach var="vo" items="${vos}">
      <tr>
        <td style="text-align:center;">
          <img src="${ctp}/dbShop/${vo.thumbImg}" class="thumb" width="80px"/>
        </td>
        <td style="text-align:center;"><br/>
          <p>주문번호 : ${vo.orderIdx}</p>
          <p>총 주문액 : <fmt:formatNumber value="${vo.totalPrice}"/>원</p>
          <p><input type="button" value="배송지정보" onclick="nWin('${vo.orderIdx}')"></p>
        </td>
        <td align="left">
	        <p><br/>모델명 : <span style="color:orange;font-weight:bold;">${vo.productName}</span><br/> &nbsp; &nbsp; <fmt:formatNumber value="${vo.mainPrice}"/>원</p><br/>
	        <c:set var="optionNames" value="${fn:split(vo.optionName,',')}"/>
	        <c:set var="optionPrices" value="${fn:split(vo.optionPrice,',')}"/>
	        <c:set var="optionNums" value="${fn:split(vo.optionNum,',')}"/>
	        <p>
	          - 주문 내역
	          <c:if test="${fn:length(optionNames) > 1}">(옵션 ${fn:length(optionNames)-1}개 포함)</c:if><br/>
	          <c:forEach var="i" begin="1" end="${fn:length(optionNames)}">
	            &nbsp; &nbsp; ㆍ ${optionNames[i-1]} / <fmt:formatNumber value="${optionPrices[i-1]}"/>원 / ${optionNums[i-1]}개<br/>
	          </c:forEach> 
	        </p>
	      </td>
        <td style="text-align:center;"><br/>결제완료<br/>(배송준비중)</td>
      </tr>
    </c:forEach>
  </table>
  <hr/>
  <p class="text-center"><a href="${ctp}/dbShop/dbShopList" class="btn btn-secondary">계속쇼핑하기</a> &nbsp;
    <a href="${ctp}/dbShop/dbMyOrder" class="btn btn-secondary">구매내역보기</a>
  </p>
  <hr/>
<p><br/></p>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>