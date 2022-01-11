<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbOrder.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <link rel="stylesheet" href="${ctp}/css/cart.css?after">
  <script>
	  $(document).ready(function(){
		  $(".nav-tabs a").click(function(){
		    $(this).tab('show');
		  });
		  $('.nav-tabs a').on('shown.bs.tab', function(event){
		    var x = $(event.target).text();         // active tab
		    var y = $(event.relatedTarget).text();  // previous tab
		  });
		});
  
	  // 결재하기
    function order() {
		  var paymentCard = document.getElementById("paymentCard").value;
    	var payMethodCard = document.getElementById("payMethodCard").value;
		  var paymentBank = document.getElementById("paymentBank").value;
    	var payMethodBank = document.getElementById("payMethodBank").value;
    	if(paymentCard == "" && paymentBank == "") {
    		alert("결제방식과 결제번호를 입력하세요.");
    		return false;
    	}
    	if(paymentCard != "" && payMethodCard == "") {
    		alert("카드번호를 입력하세요.");
    		document.getElementById("payMethodCard").focus();
    		return false;
    	}
    	else if(paymentBank != "" && payMethodBank == "") {
    		alert("입금자명을 입력하세요.");
    		return false;
    	}
    	var ans = confirm("결재하시겠습니까?");
    	if(ans) {
    		if(paymentCard != "" && payMethodCard != "") {
    			document.getElementById("payment").value = "C"+paymentCard;
    			document.getElementById("payMethod").value = payMethodCard;
    		}
    		else {
    			document.getElementById("payment").value = "B"+paymentBank;
    			document.getElementById("payMethod").value = payMethodBank;
    		}
	    	myform.action = "${ctp}/dbShop/orderInput";
	    	myform.submit();
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
	<div class="head"><h2>주문 / 결제</h2></div>
	<p><br/></p> 
	
	<table class="list table-bordered">
	 <thead>
	  <tr class="tablehead">
	    <td colspan="2">상품</td>
	    <td>총상품금액</td>
	  </tr>
	  </thead>
	    
	  <!-- 주문서 목록출력 -->
	  <c:set var="orderTotalPrice" value="0"/>
	  <c:forEach var="vo" items="${orderVos}">  <!-- 세션에 담아놓은 orderVos의 품목내역들을 화면에 각각 보여주는 작업처리 -->
	    <tbody>
	    <tr align="center">
	      <td><img src="${ctp}/dbShop/${vo.thumbImg}" class="thumb"/></td>
	      <td align="left">
	        <p><br/>주문번호 : ${orderIdx}</p>
	        <p><br/>모델명 : <span style="color:orange;font-weight:bold;">${vo.productName}</span><br/> &nbsp; &nbsp; <fmt:formatNumber value="${vo.mainPrice}"/>원</p><br/>
	        <c:set var="optionNames" value="${fn:split(vo.optionName,',')}"/>
	        <c:set var="optionPrices" value="${fn:split(vo.optionPrice,',')}"/>
	        <c:set var="optionNums" value="${fn:split(vo.optionNum,',')}"/>
	        <p>
	          - 주문 옵션 내역 : 총 ${fn:length(optionNames)}개<br/>
	          <c:forEach var="i" begin="1" end="${fn:length(optionNames)}">
	            &nbsp; &nbsp; ㆍ ${optionNames[i-1]} / <fmt:formatNumber value="${optionPrices[i-1]}"/>원 / ${optionNums[i-1]}개<br/>
	          </c:forEach> 
	        </p>
	      </td>
	      <td>
	        <b>총 : <fmt:formatNumber value="${vo.totalPrice}" pattern='#,###원'/></b><br/><br/>
	      </td>
	    </tr>
	    </tbody>
		  <%-- <input type="hidden" name="cartIdx" value="${idx}"/> --%>  <!-- 장바구니고유번호를 비롯한 주문된 상품의 정보들은 세션에 담겨있기에 굳이 따로 넘길필요없다. 즉 따로이 입력된 배송지 정보들만 넘긴다. -->
	    <c:set var="orderTotalPrice" value="${orderTotalPrice + vo.totalPrice}"/>
	  </c:forEach>
	</table>
	<div>
	  <div style="width:100%; background-color:#eee;text-align:center;">
	    <span class="ft">총 주문(결재) 금액 : </span> 
	    <span class="inputFont"><b><fmt:formatNumber value="${orderTotalPrice}" pattern='#,###원'/></b></span>
	  </div>
	</div>
	<p><br/></p>
	<form name="myform" method="post">
		<div class="head"><h2>배송지 정보 / 결재수단</h2></div>
		<p>이름 : <input type="text" name="name" value="${memberVo.name}"/></p>
		<p>주소 : <input type="text" name="address" value="${memberVo.address}"/></p>
		<p>연락처 : <input type="text" name="tel" value="${memberVo.tel}"/></p>
		<p>배송시 요청사항 : <input type="text" name="message"/></p>
		<hr/>
		
	  <!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist">
      <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#card">카드결재</a></li>
	    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#bank">은행결재</a></li>
	    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#telCheck">상담사연결</a></li>
	  </ul>
	
	  <!-- Tab panes -->
	  <div class="tab-content">
	    <div id="card" class="container tab-pane active"><br>
	      <h3>카드결재</h3>
	      <p>
	        <select name="paymentCard" id="paymentCard">
	          <option value="">카드선택</option>
	          <option value="국민">국민</option>
	          <option value="현대">현대</option>
	          <option value="신한">신한</option>
	          <option value="농협">농협</option>
	          <option value="BC">BC</option>
	          <option value="롯데">롯데</option>
	          <option value="삼성">삼성</option>
	          <option value="LG">LG</option>
	        </select>
	      </p>
				<p>카드번호 : <input type="text" name="payMethodCard" id="payMethodCard"/></p>
	    </div>
	    <div id="bank" class="container tab-pane fade"><br>
	      <h3>은행결재</h3>
	      <p>
	        <select name="paymentBank" id="paymentBank">
	          <option value="">은행선택</option>
	          <option value="국민">국민(111-111-111)</option>
	          <option value="신한">신한(222-222-222)</option>
	          <option value="우리">우리(333-333-333)</option>
	          <option value="농협">농협(444-444-444)</option>
	          <option value="신협">신협(555-555-555)</option>
	        </select>
	      </p>
				<p>입금자명 : <input type="text" name="payMethodBank" id="payMethodBank"/></p>
	    </div>
	    <div id="telCheck" class="container tab-pane fade"><br>
	      <h3>전화상담</h3>
	      <p>콜센터(☎) : 02-1234-1234</p>
	    </div>
	  </div>
		<hr/>
		<div align="center">
		  <button type="button" class="btn btn-secondary" onClick="order()">결제하기</button>
			<button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/dbShop/dbCartList';">장바구니보기</button>
		  <button type="button" class="btn btn-secondary" onClick="location.href='${ctp}/dbShop/dbShopList';">계속 쇼핑하기</button>
		  <%-- <a href="${ctp}/dbShop/dbShopList" class="btn btn-secondary">계속쇼핑하기</a> --%>
		</div>
		<input type="hidden" name="orderVos" value="${orderVos}"/>
    <%-- <input type="hidden" name="oIdx" value="${oIdx}"/> --%>  				<!-- 주문테이블 고유번호 -->
	  <input type="hidden" name="orderIdx" value="${orderIdx}"/>  <!-- 주문번호 -->
	  <input type="hidden" name="orderTotalPrice" value="${orderTotalPrice}"/>
	  <input type="hidden" name="mid" value="${sMid}"/>
	  <input type="hidden" name="payment" id="payment"/>
	  <input type="hidden" name="payMethod" id="payMethod"/>
	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>