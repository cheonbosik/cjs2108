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
  <title>dbShopContent.jsp(상품정보 상세보기)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <%-- <link rel="stylesheet" href="${ctp}/css/product.css?after"> --%>
  <script>
    var idxArray = new Array();
  
    $(function(){
    	$("#selectOption").change(function(){						// <option value="0:기본품목_${productVo.mainPrice}">기본품목</option>
    		var selectOption = $(this).val();							// <option value="${vo.idx}:${vo.optionName}_${vo.optionPrice}">${vo.optionName}</option>
    		var idx = selectOption.substring(0,selectOption.indexOf(":")); 																	// 현재 옵션의 고유번호(기본품목은 0으로 처리한다)
    		var optionName = selectOption.substring(selectOption.indexOf(":")+1,selectOption.indexOf("_")); // 옵션명
    		var optionPrice = selectOption.substring(selectOption.indexOf("_")+1); 													// 옵션가격
    		var commaPrice = numberWithCommas(optionPrice);																									// 콤마붙인 가격
    		
    		if($("#layer"+idx).length == 0 && selectOption != "") {
    		  idxArray[idx] = idx;		// 옵션을 선택한 개수만큼 배열의 크기로 잡는다.
    		  
	    		var str = "";
	    		str += "<div class='layer row' id='layer"+idx+"'><div class='col'>"+optionName+"</div>";
	    		str += "<input type='number' class='numBox' id='numBox"+idx+"' name='optionNum' onchange='numChange("+idx+")' value='1' min='1'/> &nbsp;";
	    		str += "<input type='text' id='imsiPrice"+idx+"' class='price' value='"+commaPrice+"' readonly />";
	    		str += "<input type='hidden' id='price"+idx+"' class='price' value='"+optionPrice+"'/> &nbsp;";
	    		str += "<input type='button' class='btn btn-outline-secondary btn-sm' onclick='remove("+idx+")' value='삭제'/>";
	    		str += "<input type='hidden' name='statePrice' id='statePrice"+idx+"' value='"+optionPrice+"'/>";
	    		str += "<input type='hidden' name='optionIdx' value='"+idx+"'/>";
	    		str += "<input type='hidden' name='optionName' value='"+optionName+"'/>";
	    		str += "<input type='hidden' name='optionPrice' value='"+optionPrice+"'/>";
	    		str += "</div>";
	    		$("#product1").append(str);
	    		onTotal();
    	  }
    	  else {
    		  alert("이미 선택한 옵션입니다.");
    	  }
    	});
    });
    
    // 등록(추가)시킨 옵션 상품 삭제하기
    function remove(idx) {
  	  $("div").remove("#layer"+idx);
  	  onTotal();
    }
    
    // 상품의 총 금액 (재)계산하기
    function onTotal() {
  	  var total = 0;
  	  for(var i=0; i<idxArray.length; i++) {
  		  if($("#layer"+idxArray[i]).length != 0) {
		  	  //alert(idxArray[i]);
  		  	total +=  parseInt(document.getElementById("price"+idxArray[i]).value);
  		  	document.getElementById("totalPriceResult").value = total;
  		  }
  	  }
  	  document.getElementById("totalPrice").value = numberWithCommas(total);
    }
    
    // 수량 변경시 처리하는 함수
    function numChange(idx) {
    	var price = document.getElementById("statePrice"+idx).value * document.getElementById("numBox"+idx).value;
    	document.getElementById("imsiPrice"+idx).value = numberWithCommas(price);
    	document.getElementById("price"+idx).value = price;
    	onTotal();
    }
    
    // 장바구니 호출시 수행함수
    function cart() {
    	if(document.getElementById("totalPrice").value==0) {
    		alert("옵션을 선택해주세요");
    		return false;
    	}
    	else {
    		var ans = confirm("장바구니로 이동하시겠습니까?");
    		if(!ans) {
    			return false;
    		}
    		else {
    			/* document.myform.sw.value = "cart"; */
    			document.myform.action = "${ctp}/dbShop/dbShopContent";
	    		document.myform.submit();
    		}
    	}
    }
    
    // 직접 주문하기
    function order(mid) {
    	if(mid == "") {
    		alert("로그인 후 이용 가능합니다.");
    		location.href = "${ctp}/member/mLogin";
    	}
    	else if(document.getElementById("totalPrice").value=="" || document.getElementById("totalPrice").value==0) {
    		alert("옵션을 선택해주세요");
    		return false;
    	}
    	else {
  			/* document.myform.sw.value = "order"; */
  			//document.myform.action = "${ctp}/dbShop/dbShopOrder"
    		document.myform.submit();
    	}
    }
    
    // 천단위마다 콤마를 표시해 주는 함수
    function numberWithCommas(x) {
    	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
    }
  </script>
  <style>
    .layer  {
      border:0px;
      width:100%;
      padding:15px;
      margin-left:1px;
      background-color:#eee;
    }
    .numBox {width:60px}
    .price  {
      width:160px;
      background-color:#eee;
      text-align:right;
      font-size:1.2em;
      border:0px;
      outline: none;
    }
    .totalPrice {
      text-align:right;
      margin-right:10px;
      color:#f63;
      font-size:1.5em;
      font-weight: bold;
      border:0px;
      outline: none;
    }
    .buttongroup {
      padding: 5px;
      text-align: center;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
  <div class="row">
    <div class="col p-4 text-center" style="border:1px solid #ccc">
		  <!-- 상품메인 이미지 -->
		  <div id="Thumbnail">
		    <img src="${ctp}/dbShop/${productVo.FSName}" width="100%"/>
		  </div>
		</div>
		<div class="container col  text-left">
		  <!-- 상품 기본정보 -->
		  <div id="iteminfor">
		    <h3>${productVo.productName}</h3>
		    <h3><fmt:formatNumber value="${productVo.mainPrice}"/>원</h3>
		    <p>${productVo.detail}</p>
		  </div>
		  <!-- 상품주문을 위한 옵션정보 출력 -->
		  <div class="form-group select_box">
		    <form name="optionForm">  <!-- 옵션의 정보를 보여주기위한 form -->
		      <select size="1" class="form-control" id="selectOption">
		        <option value="" disabled selected>상품옵션선택</option>
		        <option value="0:기본품목_${productVo.mainPrice}">기본품목</option>
		        <c:forEach var="vo" items="${optionVos}">
		          <option value="${vo.idx}:${vo.optionName}_${vo.optionPrice}">${vo.optionName}</option>
		        </c:forEach>
		      </select>
		    </form>
		  </div>
		  <form name="myform" method="post">  <!-- 실제 상품의 정보를 넘겨주기 위한 form -->
		    <input type="hidden" name="mid" value="${sMid}"/>
		    <input type="hidden" name="productIdx" value="${productVo.idx}"/>
		    <input type="hidden" name="productName" value="${productVo.productName}"/>
		    <input type="hidden" name="mainPrice" value="${productVo.mainPrice}"/>
		    <input type="hidden" name="thumbImg" value="${productVo.FSName}"/>
		    <input type="hidden" name="totalPrice" id="totalPriceResult"/>
		    <div id="product1"></div>
		  </form>
		  <!-- 상품의 총가격(옵션포함가격) 출력처리 -->
		  <div class="product2">
		    <hr/>
		    <font size="4" color="black">총상품금액</font>
		    <p align="right">
	        <b><input type="text" class="totalPrice text-right" id="totalPrice" value="<fmt:formatNumber value='0'/>" readonly /></b>
		    </p>
		  </div>
		  <!-- 장바구니보기/주문하기/계속쇼핑하기 처리 -->
		  <div class="buttongroup">
		    <button class="btn btn-secondary" onclick="cart()">장바구니</button> &nbsp;
		    <button class="btn btn-secondary" onclick="order()">주문하기</button> &nbsp;
		    <button class="btn btn-secondary" onclick="location.href='${ctp}/dbShop/dbShopList';">계속쇼핑하기</button>
		  </div>
		</div>
  </div>
  <br/><br/>
  <!-- 상품 상세설명 보여주기 -->
  <div id="content" class="container tab-pane active"><br/>
    <div class="next">
      ${productVo.content}
    </div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>