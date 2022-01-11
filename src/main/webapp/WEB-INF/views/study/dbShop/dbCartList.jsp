<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctp" value="${pageContext.request.contextPath }" />
<html>
<head>
  <title>dbCartList.jsp(장바구니)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <link rel="stylesheet" href="${ctp}/css/cart.css?after">
  <script>
    function onTotal(){
      var total = 0;
      var maxIdx = document.getElementById("maxIdx").value;
      for(var i=1;i<=maxIdx;i++){
        if($("#totalPrice"+i).length != 0 && document.getElementById("idx"+i).checked){  
          total = total + parseInt(document.getElementById("totalPrice"+i).value); 
        }
      }
      document.getElementById("total").value=numberWithCommas(total);
      
      if(total>=50000||total==0){
        document.getElementById("baesong").value=0;
      } else {
        document.getElementById("baesong").value=2500;
      }
      var lastPrice=parseInt(document.getElementById("baesong").value)+total;
      document.getElementById("lastPrice").value = numberWithCommas(lastPrice);  // 화면에 보여주는 주문 총금액
      document.getElementById("orderTotalPrice").value = numberWithCommas(lastPrice);  // 값을 넘겨주는 '주문 총 금액 필드'
    }
    
    function onCheck(){
      var maxIdx = document.getElementById("maxIdx").value;

      var cnt=0;
      for(var i=1;i<=maxIdx;i++){
        if($("#idx"+i).length != 0 && document.getElementById("idx"+i).checked==false){
          cnt++;
          break;
        }
      }
      if(cnt!=0){
        document.getElementById("allcheck").checked=false;
      } 
      else {
        document.getElementById("allcheck").checked=true;
      }
      onTotal();
    }
    
    function allCheck(){
      var maxIdx = document.getElementById("maxIdx").value;
      if(document.getElementById("allcheck").checked){
        for(var i=1;i<=maxIdx;i++){
          if($("#idx"+i).length != 0){
            document.getElementById("idx"+i).checked=true;
          }
        }
      }
      else {
        for(var i=1;i<=maxIdx;i++){
          if($("#idx"+i).length != 0){
            document.getElementById("idx"+i).checked=false;
          }
        }
      }
      onTotal();
    }
    
    // cart에 들어있는 품목 삭제하기
    function cartDel(idx){
    	if(!document.getElementById("idx"+idx).checked) {
    		alert("현재 상품을 삭제하시려면 현상품의 체크박스에 체크해주세요.");
    		return false;
    	}
      var ans = confirm("선택하신 현재상품을 장바구니에서 삭제하시겠습니까?");
      if(!ans) return false;
      
      $.ajax({
        type : "post",
        url  : "${ctp}/dbShop/dbCartDel",
        data : {idx : idx},
        success:function() {
          location.reload();
        },
        error : function() {
        	alert("전송에러!");
        }
      });
    }
    
    function order(){
      //구매하기위해 체크한 장바구니에만 아이디가 check인 필드의 값을 1로 셋팅. 체크하지 않은것은 check아이디필드의 기본값은 0이다.
      var maxIdx = document.getElementById("maxIdx").value;
      for(var i=1;i<=maxIdx;i++){
        if($("#idx"+i).length != 0 && document.getElementById("idx"+i).checked){
          document.getElementById("checkItem"+i).value="1";
        }
      }
      //배송비넘기기
      //document.myform.baesong.value=document.getElementById("baesong").value;
      
      if(document.getElementById("lastPrice").value==0){
        alert("장바구니에서 상품을 선택해주세요!");
        return false;
      } 
      else {
	      //document.myform.action="${contextPath}/study/dbshop/dbOrder";
        document.myform.submit();
      }
    }
    
    // 천단위마다 쉼표 표시하는 함수
    function numberWithCommas(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  </script>
  
  <style>
    .finalTot {
      width : 520px;
      height: 85px;
      margin : 0 auto;
      background-color:#ddd;
      padding:5px;
    }
    .totBox {
      float : left;
      background-color:#ddd;
      width : 95px;
      text-align : center;
      padding : 10px;
    }
    .totSubBox {
      background-color:#ddd;
      border : none;
      width : 95px;
      text-align : center;
      font-weight: bold;
      padding : 5 0px;
      color : brown;
    }
  </style>
  
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
	<div class="head"><h2>장바구니</h2></div>
	<p><br/></p> 
	<form name="myform" method="post">
		<table class="table-bordered">
		 <thead>
		  <tr class="tablehead">
		    <td><input type="checkbox" id="allcheck" onClick="allCheck()" class="m-2"/></td>
		    <td colspan="2">상품</td>
		    <td colspan="2">총상품금액</td>
		  </tr>
		  </thead>
		    
		  <!-- 장바구니 목록출력 -->
		  <c:set var="maxIdx" value="0"/>
		  <c:forEach var="listVo" items="${cartListVos}">
		    <tbody>
		    <tr align="center">
		      <td><input type="checkbox" name="idxChecked" id="idx${listVo.idx}" value="${listVo.idx}" onClick="onCheck()" /></td>
		      <td><img src="${ctp}/dbShop/${listVo.thumbImg}" class="thumb"/></td>
		      <td align="left">
		        <p class="contFont"><br/>
		          모델명 : <span style="color:orange;font-weight:bold;">${listVo.productName}</span><br/>
		          <span class="container pl-5 ml-4"><fmt:formatNumber value="${listVo.mainPrice}"/>원</span>
		        </p><br/>
		        <c:set var="optionNames" value="${fn:split(listVo.optionName,',')}"/>
		        <c:set var="optionPrices" value="${fn:split(listVo.optionPrice,',')}"/>
		        <c:set var="optionNums" value="${fn:split(listVo.optionNum,',')}"/>
		        <p class="contFont">
		          <%-- <c:set var="optionNames" value="${fn:length(optionNames)}"/>-${optionNames} --%>
		          - 주문 내역
		          <c:if test="${fn:length(optionNames) > 1}">(옵션 ${fn:length(optionNames)-1}개 포함)</c:if><br/>
		          <c:forEach var="i" begin="0" end="${fn:length(optionNames)-1}">
		            &nbsp;&nbsp; ㆍ${optionNames[i]} / <fmt:formatNumber value="${optionPrices[i]}"/>원 / ${optionNums[i]}개<br/>
		          </c:forEach> 
		        </p>
		      </td>
		      <td>
		        <div class="totalFont">
			        <b>총 : <fmt:formatNumber value="${listVo.totalPrice}" pattern='#,###원'/></b><br/><br/>
			        <span style="color:#690;" class="buyFont">구매일자 : ${fn:substring(listVo.cartDate,0,10)}</span>
			        <input type="hidden" id="totalPrice${listVo.idx}" value="${listVo.totalPrice}"/>
		        </div>
		      </td>
		      <td>
		        <%-- <input type="button" class="btn btn-secondary btn-sm btnFont" value="삭제" onClick="cartDel(${listVo.idx})"/> --%>
		        <button type="button" class="btn btn-secondary btn-sm btnFont" onClick="cartDel(${listVo.idx})">삭제</button>
		        <input type="hidden" name="checkItem" value="0" id="checkItem${listVo.idx}"/>
		        <input type="hidden" name="idx" value="${listVo.idx }"/>
		        <input type="hidden" name="thumbImg" value="${listVo.thumbImg}"/>
		        <input type="hidden" name="productName" value="${listVo.productName}"/>
		        <input type="hidden" name="mainPrice" value="${listVo.mainPrice}"/>
		        <input type="hidden" name="optionName" value="${optionNames}"/>
		        <input type="hidden" name="optionPrice" value="${optionPrices}"/>
		        <input type="hidden" name="optionNum" value="${optionNums}"/>
		        <input type="hidden" name="totalPrice" value="${listVo.totalPrice}"/>
		        <input type="hidden" name="mid" value="${smid}"/>
		      </td>
		    </tr>
		    </tbody>
		    <c:set var="maxIdx" value="${listVo.idx}"/>
		  </c:forEach>
		  <!-- <input type="hidden" name="post" value="0" /> -->
		</table>
	  <input type="hidden" id="maxIdx" name="maxIdx" value="${maxIdx}"/>
	  <input type="hidden" name="orderTotalPrice" id="orderTotalPrice"/>
	</form>
	<br/>
	<div align="center">
	  <b>실제 주문총금액</b>(구매하실 상품에 체크해 주세요. 총주문금액이 산출됩니다.)
	</div>
	<div class="finalTot">
	  <div class="totBox">
	    상품금액<br/>
	    <input type="text" id="total" value="0" class="totSubBox" readonly/>
	  </div>
	  <div class="totBox"> + </div>
	  <div class="totBox">
	    배송비<br/>
	    <input type="text" id="baesong" value="0" class="totSubBox" readonly/>
	  </div>
	  <div class="totBox"> = </div>
	  <div class="totBox">
	    총주문금액<br/>
	    <input type="text" id="lastPrice" value="0" class="totSubBox" readonly/>
	  </div>
	</div>
	<p><br/></p>
	<div align="center" style="clear:both;">
	  <button class="btn btn-secondary" onClick="order()">주문하기</button> &nbsp;&nbsp;
	  <button class="btn btn-secondary" onClick="location.href='${ctp}/dbShop/dbShopList';">계속 쇼핑하기</button>
	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>