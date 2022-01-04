<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsInput.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
	  function fCheck() {
		  var pwd = myform.pwd.value;
	  	var file = myform.file.value;
	  	var ext = file.substring(file.lastIndexOf(".")+1);  // 화일의 확장자만 구하기
	  	var uExt = ext.toUpperCase();  // 확장자를 대문자로 치환
	  	var maxSize = 1024 * 1024 * 10;   // 최대 10MByte 까지 허용
	  	
	  	var title = myform.title.value;
	  	var pwd = myform.pwd.value;
	  	
	  	if(file == "") {
	  		alert("업로드할 파일을 선택하세요!");
	  		return false;
	  	}
	  	else if(title == "") {
	  		alert("업로드할 파일의 제목을 입력하세요!");
	  		myform.title.focus();
	  		return false;
	  	}
	  	else if(pwd == "") {
	  		alert("비밀번호를 입력하세요!");
	  		myform.pwd.focus();
	  		return false;
	  	}
	  	/* 
	  	var fileSize = document.getElementById("file").files[0].size;  // 업로드할 파일의 사이즈를 구한다.
	  	myform.fsize.value = fileSize;
	  	
	  	if(uExt != "ZIP" && uExt != "GIF" && uExt != "JPG" && uExt != "PNG" && uExt != "HWP" && uExt != "PPT" && uExt != "PPTX") {
	  		alert("업로드 가능파일은 'zip/gif/jpg/png/hwp'파일만 가능합니다.");
	  		return false;
	  	}
	  	else if(fileSize > maxSize) {
	  		alert("업로들할 파일의 최대용량은 10MByte이내입니다.");
	  		return false;
	  	}
	  	else {
					myform.submit();
	  	}
	  	*/
	  	
	  	var fileSize = 0;
	  	var files = document.getElementById("file").files;
	  	var file;
  	  for(var i=0; i<files.length; i++) {
  		  file = files[i];
		  	//alert("file name : " + file.name);
		  	//alert("file size : " + file.size);
		  	
  	  	if(file.name != "" && file.name != null) {
  	  	  fname = file.name;
  	  		var ext = fname.substring(fname.lastIndexOf(".")+1);  // 확장자 발
	        var uExt = ext.toUpperCase();
	        
	        if(uExt != "ZIP" && uExt != "JPG" && uExt != "GIF" && uExt != "PNG" && uExt != "HWP" && uExt != "PPT" && uExt != "PPTX") {
	      	  alert("업로드 가능한 파일은 'ZIP/JPG/GIF/PNG/HWP'입니다.");
	      	  return false;
	        }
	        else if(fname.indexOf(" ") != -1) {
	      	  alert("업로드하는 파일에는 공백이 포함될 수 없습니다.");
	      	  return false;
	        }
	        else {
	        	fileSize += file.size;
	        }
  	  	}
  	  	
  	  }
  	  if(fileSize == 0 || fileSize > maxSize) {
    	  alert("업로드하는 파일의 최대용량은 10MByte 입니다.");
    	  return false;
  	  }
  	  else {
    	  //alert("업로드파일 총 용량? " + fileSize);
  		  myform.fSize.value = fileSize;
  		  myform.submit();
  	  }
	  }
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <h2>멀 티 자 료 올 리 기</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <input multiple="multiple" type="file" name="file" id="file" class="form-control-file border" accept=".zip,.jpg,.gif,.png,.hwp,.ppt,.pptx"/>
    </div>
    <div class="form-group">올린이 : ${sNickName}</div>
    <div class="form-group">
      <label for="title">자료제목 : </label>
      <input type="text" name="title" id="title" placeholder="자료 제목을 입력하세요." class="form-control" required/>
    </div>
    <div class="form-group">
      <label for="content">자료내용 : </label>
      <textarea rows="4" name="content" id="content" class="form-control" required></textarea>
    </div>
    <div class="form-group">
      <label for="part">자료분류 : </label>
      <select name="part" size="1" class="form-control">
        <option value="학습" selected>학습</option>
        <option value="여행">여행</option>
        <option value="음식">음식</option>
        <option value="기타">기타</option>
      </select>
    </div>
    <div class="form-group">
      <div class="form-check-inline">공개여부 : &nbsp; &nbsp;
	      <label class="form-check-label">
	        <input type="radio" name="openSw" class="form-check-input" value="공개" checked/>공개 &nbsp; &nbsp;
	      </label>
	      <label class="form-check-label">
	        <input type="radio" name="openSw" class="form-check-input" value="비공개"/>비공개 &nbsp; &nbsp;
	      </label>
      </div>
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호 : </label>
      <input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력하세요." class="form-control"/>
    </div>
    <div class="form-group">
      <button type="button" onclick="fCheck()" class="btn btn-secondary">자료올리기</button>
      <button type="reset" class="btn btn-secondary">다시쓰기</button>
      <button type="button" onclick="location.href='${ctp}/pds/pdsList';" class="btn btn-secondary">돌아가기</button>
    </div>
    <input type="hidden" name="mid" value="${sMid}"/>
    <input type="hidden" name="nickName" value="${sNickName}"/>
    <input type="hidden" name="fSize"/>
  </form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>