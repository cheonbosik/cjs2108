<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>mainImageList.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    function partCheck(sw) {
    	var idx = "";
    	if(sw == 1) idx = partForm.idx.value;
    	else idx = partForm.idx2.value;
	   	location.href = "${ctp}/study/mainImageList?idx="+idx;
    }
    function mainImageDelete(idx) {
    	var ans = confirm("현재 이미지들을 삭제 하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/study/mainImageDelete",
    		data : {idx : idx},
    		success:function() {
    			alert("삭제되었습니다.");
    			location.href = "${ctp}/study/mainImageList";
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
  <style type="text/css">
    #left {float: left;}
    #right {
      float: left;
      margin-left: 60px;
    }
    #footer {clear: both;}
    img {border: 1px solid #ddd;}
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <h3>메인 이미지 선택하기</h3>
  <div class="row">
    <div class="col">
  		<button type="button" onclick="javascript:mainImageDelete(${idx})" class="btn btn-secondary">현재메인이미지삭제</button>
  	</div>
    <div class="col">
  		<button type="button" onclick="location.href='${ctp}/study/mainImage';" class="btn btn-secondary">메인이미지등록하기</button>
  	</div>
  </div>
  <hr/>
  <div id="left" class="text-center bordered">
    <c:forEach var="vo" items="${mainImageVos}" begin="0" end="${fn:length(mainImageVos)-1}" varStatus="st">
      <img src="${ctp}/data/mainImage/${vo.mainFName}" width="90px" 
        onmouseover="document.getElementById('mainImage').innerHTML='<img src=${ctp}/data/mainImage/${vo.mainFName} width=500px>'" style="border:1px solid #ccc"/> &nbsp;
    </c:forEach>
    <br/><br/>
    <p id="mainImage"><img src="${ctp}/data/mainImage/${mainImageVos[0].mainFName}" width="500px"/></p>
  </div>
  <div id="right">
    <h4>샘플 고르기(분류:<font color="blue">${part}</font>)</h4>
    <form name="partForm">
	    <select name="idx" onchange="partCheck(1)" class="form-control">
	      <c:forEach var="vo" items="${partVos}">
	        <option value="${vo.idx}" ${idx==vo.idx ? 'selected' : ''}>${vo.idx} / ${vo.part}</option>
	      </c:forEach>
	    </select>
	    <br/><hr/><br/>
	    <h4>샘플 고르기(분류:<font color="blue">${part}</font>)</h4>
	    <select name="idx2" size="${fn:length(partVos)}" onchange="partCheck(2)" class="form-control">
	      <c:forEach var="vo" items="${partVos}">
	        <option value="${vo.idx}" ${idx==vo.idx ? 'selected' : ''}>${vo.idx} / ${vo.part}</option>
	      </c:forEach>
	    </select>
    </form>
  </div>
</div>
<p id="footer"><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>