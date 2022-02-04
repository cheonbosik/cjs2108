<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>qrcode.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    function qrCreate(no) {
    	if(no == 1)	var moveUrl = myform.moveUrl.value;
    	else var moveUrl = myform.moveUrl2.value;
    	$.ajax({
  			url  : "${ctp}/study2/qrCreate",
  			type : "post",
  			data : {moveUrl : moveUrl},
  			success : function(data) {
 					alert("qr코드 생성완료 : "+data);
 					$("#qrView").html(data);
 					var qrImage = '<img src="${ctp}/data/qrcode/'+data+'"/>';
 					$("#qrImage").html(qrImage);
				}
			});
		}
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <form name="myform" method="post" action="${ctp}/study2/barCode/barCreate2">
	  <h2>QR코드 생성하기</h2>
	  <div>
	    <h4>QR코드 체크시 이동할 주소를 입력후 QR코드를 생성해 주세요.</h4>
	  </div>
	  <hr/>
	  <p>
	    이동할 주소1 : <input type="text" name="moveUrl" value="http://blog.daum.net/cjsk1126" size="30"/>
	    <input type="button" value="qr코드 생성1" onclick="qrCreate(1)"/>
	  </p>
	  <p>
	    이동할 주소2 : <input type="text" name="moveUrl2" value="http://blog.naver.com/cjsk1126" size="30"/>
	    <input type="button" value="qr코드 생성2" onclick="qrCreate(2)"/>
	  </p>
	  <hr/>
	  <div>
	    <h3>생성된 QR코드 확인하기</h3>
	    <div>
		  - 생성된 qr코드명 : <span id="qrView"></span><br/>
		  <span id="qrImage"></span>
		  </div>
	  </div>
  </form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>