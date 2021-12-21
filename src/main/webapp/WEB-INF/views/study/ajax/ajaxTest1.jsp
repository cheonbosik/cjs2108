<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ajaxTest1.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    $(function() {
    	$("#do").change(function() {
    		var dodo = $(this).val();
    		if(dodo == "") {
    			alert("지역을 선택하세요");
    			return false;
    		}
    		
    		var query = {
    				dodo : dodo
    		}
    		
    		$.ajax({
    			type : "post",
    			url  : "${ctp}/study/ajax/ajaxTest1",
    			data : query,
    			success : function(data) {
    				var str = "";
    				str += "<option value=''>도시선택</option>";
    				for(var i=0;i<data.city.length; i++) {
    				  str += "<option>"+data.city[i]+"</option>";
    				}
    				$("#city").html(str);
    			},
    			error : function() {
    				alert("전송오류!!!");
    			}
    		});
    	});
    });
    
    function fCheck() {
    	var do2 = $("#do").val();
    	var city = $("#city").val();
    	
    	alert("선책하신 지역은? " + do2 + " / " + city);
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br></p>
<div class="container">
  <h2>aJax를 활용한 값의 전달1(ajaxTest1.jsp) - Map형식에 의한 값의 전달</h2>
  <hr/>
  <h3>도시를 선택하세요</h3>
  <form name="myform">
    <select name="do" id="do">
      <option value="">지역선택</option>
      <option value="서울">서울</option>
      <option value="경기">경기</option>
      <option value="충북">충북</option>
      <option value="충남">충남</option>
    </select>
    <select id="city">
      <option value="">도시선택</option>
    </select>
    <input type="button" value="선택" onclick="fCheck()"/> &nbsp;&nbsp;
    <input type="button" value="돌아가기" onclick="location.href='${ctp}/study/ajax/ajaxMenu';"/>
  </form>
</div>
<br/>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>