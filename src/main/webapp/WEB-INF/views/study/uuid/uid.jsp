<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>uuid.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    var str = "";
    var i = 0;
  
    function uidCheck() {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/uuid",
    		success:function(data) {
    			i++;
    			str += i + " : " + data + "<br/>";
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("전송오류!!");
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
  <h2>UUID에 대하여</h2>
  <p>
    UUID(Universally Unique Identifier)란, 네트워크상에서 고유성이 보장되는 id를 만들기 위한 규약.<br/>
    32자리의 16진수(128bit)로 표현된다. 표시형식은 8-4-4-4-12표현한다.<br/>
    예: 550e8400-e296-41d4-a716-446655440000
  </p>
  <hr/>
  <p><input type="button" value="실습하기" onclick="uidCheck()"/></p>
  <hr/>
  <div>
    출력결과<br/>
    <span id="demo"></span>
  </div>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>