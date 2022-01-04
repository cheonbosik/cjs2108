<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.net.URLDecoder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>aria.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    var str = "";
    var i = 0;
  
    function ariaCheck() {
    	var pwd = document.getElementById("pwd").value;
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/aria",
    		data : {pwd : pwd},
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
  <h2>ARIA에 대하여</h2>
  <p>
    ARIA는 경량 환경 및 하드웨어 구현을 위해 최적화된, Involutional SPN 구조를 갖는 범용 블록 암호 알고리즘이다.<br/>
	  ARIA가 사용하는 대부분의 연산은 XOR과 같은 단순한 바이트 단위 연산으로 구성되어 있으며 블록크기는 128bit이다.<br/>
	  ARIA라는 이름은 Academy(학계), Research Institute(연구소), Agency(정부 기관)의 첫 글자들을 딴 것으로, ARIA 개발에 참여한 학·연·관의 공동 노력을 표현하고 있다.<br/>
  </p>
  <hr/>
  <p>
    <input type="text" name="pwd" id="pwd" autofocus />
    <input type="button" value="실습하기" onclick="ariaCheck()"/>
  </p>
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