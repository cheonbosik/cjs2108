<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>fileUpload.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function fileUpload() {
    	// 업로드 하는 파일 체크(확장자... 길이...)
    	myform.submit();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br></p>
<div class="container">
  <form name="myform" method="post" enctype="multipart/form-data">
  	<p>파일명 :
  	  <input type="file" name="fName" id="fName" class="form-control-file border" accept=".jpg,.gif,.png,.zip"/>
  	</p>
  	<p>
  	  <input type="button" value="파일업로드" onclick="fileUpload()" class="btn btn-secondary"/>
  	  <input type="reset" value="다시선택" class="btn btn-secondary"/>
  	</p>
  </form>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>