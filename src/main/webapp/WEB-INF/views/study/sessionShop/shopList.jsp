<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>shopList.jsp(상품리스트 보여주기)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function fCheck() {
    	var product = myform.product.value;
    	if(product == "") {
    		alert("상품을 선택하세요");
    		return false;
    	}
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/sessionShop/shopList",
    		data : {product : product},
    		success:function() {
    			alert("구매품목("+product+")이 장바구니에 담겼습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br></p>
<div class="container">
  <h2>세션을 이용한 상품 구매하기</h2>
  <hr/>
  <p>${sNickName}님께서 구매중이십니다.</p>
  <hr/>
  <h4>상품 리스트</h4>
  <p>(이곳의 상품은 DB에서 상품 리스트를 가져와서 구성해야 한다.)</p>
  <div>
    - 사과 : 500원<br/>
    - 배 : 1000원<br/>
    - 바나나 : 2500원<br/>
    - 포도 : 3000원<br/>
    - 딸기 : 5500원<br/>
    - 키위 : 3500원<br/>
    - 복숭아 : 1500원<br/>
  </div>
  <hr/>
  <form name="myform" method="post">
    <select name="product">
      <option value="">상품선택</option>
      <option value="사과">사과</option>
      <option value="배">배</option>
      <option value="바나나">바나나</option>
      <option value="포도">포도</option>
      <option value="딸기">딸기</option>
      <option value="키위">키위</option>
      <option value="복숭아">복숭아</option>
    </select>&nbsp;
    <input type="button" value="장바구니담기" onclick="fCheck()"/><br/>
    <hr/>
    <p><button type="button" onclick="location.href='${ctp}/sessionShop/cart';" class="btn btn-secondary">장바구니보기</button></p>
  </form>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>