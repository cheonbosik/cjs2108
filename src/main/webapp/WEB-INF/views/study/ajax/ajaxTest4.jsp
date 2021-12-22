<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ajaxTest4.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function idSearch() {
    	var mid = myform.mid.value;
    	if(mid == "") {
    		alert("아이디를 입력하세요!");
    		myform.mid.focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/ajax/ajaxTest4",
    		data : {mid : mid},
    		success:function(data) {		// 컨트롤러에서 넘어온 vo를 data가 받아준다.
    			str = "<b>전송결과</b><hr/>";
    			if(data != "") {
	    			str += "번호 : " + data.idx + "<br/>";
	    			str += "아이디 : " + data.mid + "<br/>";
	    			str += "성명 : " + data.name + "<br/>";
	    			str += "별명 : " + data.nickName + "<br/>";
	    			str += "생일 : " + data.birthday + "<br/>";
	    			str += "성별 : " + data.gender;
    			}
    			else {
    				str += "<font color='red'>찾는 자료가 없습니다.</font>";
    			}
    			$("#demo").html(str);
    		},
    		error:function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function idList() {
    	var mid = myform.mid.value;
    	if(mid == "") {
    		alert("아이디를 입력하세요!");
    		myform.mid.focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/ajax/ajaxTest4_2",
    		data : {mid : mid},
    		success:function(vos) {		// 컨트롤러에서 넘어온 vos를 data가 받아준다.
    			str = "<b>전송결과</b><hr/>";
    			if(vos != "") {
    				str += "<table class='table table-hover'>";
    				str += "<tr class='text-center table-dark text-dark'>";
    				str += "<th>번호</th><th>아이디</th><th>성명</th><th>별명</th><th>생일</th><th>성별</th>";
    				str += "</tr>";
    				for(var i=0; i<vos.length; i++) {
	    				str += "<tr align='center'>";
    					
		    			str += "<td>" + vos[i].idx + "</td>";
		    			str += "<td>" + vos[i].mid + "</td>";
		    			str += "<td>" + vos[i].name + "</td>";
		    			str += "<td>" + vos[i].nickName + "</td>";
		    			str += "<td>" + vos[i].birthday.substring(0,10) + "</td>";
		    			str += "<td>" + vos[i].gender + "</td>";
		    			
		    			str += "</tr>";
    				}
    				str += "</table>";
    			}
    			else {
    				str += "<font color='red'>찾는 자료가 없습니다.</font>";
    			}
    			$("#demo").html(str);
    		},
    		error:function() {
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
  <form name="myform">
	  <h2>aJax를 활용한 '회원 아이디'로 검색하기</h2>
	  <hr/>
	  <p>아이디
	    <input type="text" name="mid" autofocus />
	    <input type="button" value='아이디검색' onclick="idSearch()" class="btn btn-secondary"/> &nbsp;
	    <input type="button" value='아이디통합검색' onclick="idList()" class="btn btn-secondary"/> &nbsp;
	    <input type="button" value="다시검색" onclick="location.href='${ctp}/study/ajax/ajaxTest4';" class="btn btn-secondary"/>
	  </p>
	  <hr/>
	  <p id="demo"></p>
  </form>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>