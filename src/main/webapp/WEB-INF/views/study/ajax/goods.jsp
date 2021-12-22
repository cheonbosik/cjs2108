<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>goods.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    $(function() {
    	// 대분류 선택시 수행
    	$("#product1").change(function(){
    		var product1 = $(this).val();
    		var query = {product1 : product1};
    		$.ajax({
    			type : "post",
    			url  : "${ctp}/study/goods1",
    			data : query,
    			success:function(data) {
    				var str = "";
    				str += "<option value=''>중분류</option>";
    				for(var i=0; i<data.length; i++) {
    					str += "<option>"+data[i].product2+"</option>";
    				}
    				$("#product2").html(str);
    			},
    			error : function() {
    				alert("전송오류!");
    			}
    		});
    	});
    	
    	// 중분류 선택시 수행
    	$("#product2").change(function(){
    		var product1 = $("#product1").val();
    		var product2 = $(this).val();
    		var query = {
    				product1 : product1,
    				product2 : product2
    			};
    		$.ajax({
    			type : "post",
    			url  : "${ctp}/study/goods2",
    			data : query,
    			success:function(data) {
    				var str = "";
    				str += "<option value=''>소분류</option>";
    				for(var i=0; i<data.length; i++) {
    					str += "<option>"+data[i].product3+"</option>";
    				}
    				$("#product3").html(str);
    			},
    			error : function() {
    				alert("전송오류!");
    			}
    		});
    	});
    });
    
    function productInput() {
    	var product1 = $("#product1").val();
    	var product2 = $("#product2").val();
    	var product3 = $("#product3").val();
    	var product = $("#product").val();
    	
    	if(product1=="" || product2=="" || product3=="" || product=="") {
    		alert("대/중/소/품목 을 고르시거나 입력하셔야 합니다.");
    	}
    	else {
    		alert("선택하신 대/중/소/품목은? " + product1 + "/" + product2 + "/" + product3 + "/" + product);
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br></p>
<div class="container">
  <h2>상품등록하기</h2>
  <form name="myform" method="post">
    대분류선택
    <select id="product1" name="product1">
      <option value="">대분류</option>
      <c:forEach var="vo" items="${vos}">
        <option>${vo.product1}</option>
      </c:forEach>
    </select> - &nbsp;
    중분류선택
    <select id="product2" name="product2">
    	<option value="">중분류</option>
    </select> - &nbsp;
    소분류선택
    <select id="product3" name="product3">
    	<option value="">소분류</option>
    </select> &nbsp;<br/><br/>
    상품명 <input type="text" name="product" id="product"/> &nbsp;
    <input type="button" value="상품등록" onclick="productInput()"/>
  </form>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>