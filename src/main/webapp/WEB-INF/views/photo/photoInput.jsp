<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>photo.jsp(포토갤러리 초기화면)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script src="${ctp}/ckeditor/ckeditor.js"></script>
  <script>
    function imgCheck() {
    	var title = myform.title.value;
    	if(title == "") {
    		alert("제목을 입력하세요!");
    		myform.title.focus();
    		return false;
    	}
    	else {
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
	<h2>포토 갤러리에 사진 올리기</h2>
	<hr/>
	<form name="myform" method="post" enctype="multipart/form-data">
	  <div style="margin:15px 0px;">올린이 : <input type="text" name="name" value="${sNickName}" readonly class="form-control"/></div>
	  <div>사진 분류 :
		  <select name="part" class="form-control">
		    <option value="전체">전체</option>
		    <option value="학습">학습</option>
		    <option value="여행" selected>여행</option>
		    <option value="음식">음식</option>
		    <option value="영화">영화</option>
		    <option value="기타">기타</option>
		  </select>
	  </div>
		<div style="margin:15px 0px;">제목 : <input type="text" name="title" id="title" class="form-control" autofocus required/></div>
		<div>
		  <textarea rows="6" name="content" id="CKEDITOR"id="CKEDITOR" required></textarea>
      <script>
		    CKEDITOR.replace("content",{
		    	uploadUrl:"${ctp}/photo/imageUpload",              /* 그림파일 드래그&드롭 처리로 여러개를 올릴때 앞의형식처럼 사용할수 있다. */
		    	filebrowserUploadUrl: "${ctp}/photo/imageUpload",  /* 이미지 업로드(앞줄의 내용을 빼면 그림아이콘으로 1개씩 올리게 된다.) */
		    	height:600
		    });
		  </script>
		</div>
		<div style="margin:15px 0px;">
		  <input type="button" value="사진등록" onclick="imgCheck()" class="btn btn-secondary" style="float:left; margin:0px 10px 0px 0px;"/> &nbsp; &nbsp;
		  <input type="reset" value="다시올리기" class="btn btn-secondary" style="float:left;"/>
		  <input type="button" value="돌아가기" onclick="location.href='${ctp}/photo/photo';" class="btn btn-secondary" style="float:right;"/>
		</div>
		<input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}"/>
	</form>
</div>
<p style="clear:both;"><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>