<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>임시파일삭제</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function fileCheck(folderName) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/tempFileLoad",
    		data : {folderName : folderName},
    		success:function(data) {
    			str = ''
		    	str += '<h4>(서버에 저장되어 있는 파일 리스트)</h4>';
		  	  str += '<p>${ctp}/data/~~*.*~~ ('+folderName+')</p>';
		  	  str += '<hr/>';
		  	  str += '<table class="table table-hover text-center">';
		  	  str += '  <tr class="table-dark text-dark">';
		  	  str += '    <th>번호</th>';
		  	  str += '    <th>파일명</th>';
		  	  str += '    <th>그림파일/일반파일</th>';
		  	  str += '    <th>삭제</th>';
		  	  str += '  </tr>';
		  	  for(var i in data) {
		  		  str += '<tr>';
		  		  str += '<td>'+i+'</td>';
		  		  str += '<td>'+data[i]+'</td>';
		  		  str += '<td><a href="${ctp}/data/'+data[i]+'" download="'+data[i]+'">';
		  		  var extNameArr = data[i].split('.');
		  		  extName = extNameArr[extNameArr.length-1].toLowerCase();
		  		  if(extName == 'jpg' || extName == 'png' || extName == 'gif' || extName == 'jpeg') {
		  			  if(folderName == 'DATA') {
		  		  		str += '<img src="${ctp}/data/'+data[i]+'" width="150px"/>';
		  			  }
		  			  else if(folderName == 'THUMB') {
		  		  		str += '<img src="${ctp}/data/thumbnail/'+data[i]+'" width="150px"/>';
		  			  }
		  			  else if(folderName == 'DBSHOP') {
		  		  		str += '<img src="${ctp}/data/dbShop/'+data[i]+'" width="150px"/>';
		  			  }
		  			  else if(folderName == 'BOARD') {
		  		  		str += '<img src="${ctp}/data/dbShop/'+data[i]+'" width="150px"/>';
		  			  }
		  		  } else {
		  		  	str += data[i];
		  		  }
		  		  str += '</a></td>';
			  	  str += '<td><a href="javascript:fileDelete(\''+folderName+'\',\''+data[i]+'\')" class="btn btn-outline-secondary btn-sm">삭제</a></td>';
		  		  str += '</tr>';
		  	  }
		  	  str += '</table>';
		  	  document.getElementById("demo").innerHTML = str;
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 파일 1개씩 삭제처리
    function fileDelete(folderName,file) {
    	var ans = confirm("파일을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/fileDelete",
    		data : {file : file,
    			      folderName : folderName},
    		success:function(res) {
    			if(res == "1") {
    				alert("삭제처리 되었습니다.");
    				location.reload();
    			}
    			else {
    				alert("삭제 실패(폴더는 삭제하실수 없습니다.)~~~");
    			}
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
</head>
<body>
<p><br></p>
<div class="container">
  <h2>임시파일 삭제 처리</h2>
  <hr/>
  <p>
  	<a href="${ctp}/admin/boardTempDelete" class="btn btn-secondary btn-sm">게시판 임시파일 삭제</a> /
    <input type="button" value="파일보기" onclick="fileCheck('BOARD')" class="btn btn-secondary btn-sm"/>
  </p>
  <p>
    <a href="${ctp}/admin/productTempDelete" class="btn btn-secondary btn-sm">상품등록 임시파일 삭제</a> /
    <input type="button" value="파일보기" onclick="fileCheck('DBSHOP')" class="btn btn-secondary btn-sm"/>
  </p>
  <p>
    <a href="${ctp}/admin/dataTempDelete" class="btn btn-secondary btn-sm">데이터폴더의 임시파일 삭제</a> /
    <input type="button" value="파일보기" onclick="fileCheck('DATA')" class="btn btn-secondary btn-sm"/>
  </p>
  <p>
    <a href="${ctp}/admin/thumbnailTempDelete" class="btn btn-secondary btn-sm">썸네일 연습파일 삭제</a> /
    <input type="button" value="파일보기" onclick="fileCheck('THUMB')" class="btn btn-secondary btn-sm"/>
  </p>
  <hr/>
  <div id="demo"></div>
  <hr/>
</div>
<br/>
</body>
</html>