<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>memUpdate.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <%@ include file="/WEB-INF/views/include/slide.jsp" %>
  <!-- 아래는 다음 주소 API를 활용한 우편번호검색 -->
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <script>
		var nickCheckOn = 0;
	  
		// 닉네임 중복체크
	  function nickCheck() {
	  	var nickName = $("#nickName").val();
	  	if('${sNickName}' == nickName) {
	  		alert("닉네임을 수정하지 않으셨습니다.");
	  		nickCheckOn = 1;
	  		return false;
	  	}
	  	if(nickName=="" || $("#nickName").val().length<2 || $("#nickName").val().length>20) {
	  		alert("닉네임을 체크하세요!(닉네임은 2~20자 이내로 사용하세요.)");
	  		myform.nickName.focus();
	  		return false;
	  	}
	  	$.ajax({
	  		type : "post",
	  		url  : "${ctp}/member/nickNameCheck",
	  		data : {nickName : nickName},
	  		success:function(data) {
	  			if(data == "1") {
	  				alert("이미 사용중인 닉네임 입니다.");
	  				$("#nickName").focus();
	  			}
	  			else {
	  				alert("사용 가능한 닉네임 입니다.");
	  				nickCheckOn = 1;	// 닉네임 검색버튼을 클릭한경우는 nickCheckOn값이 1이 된다.
	  			}
	  		},
	  		error : function() {
	  			alert("전송오류!");
	  		}
	  	});
	  }
		
		function nickReset() {
			nickCheckOn = 0;
		}
	  
		// 회원가입폼 체크
	  function fCheck() {
			// 정규식체크할것~~~~(이곳에선 생략했음...)
			var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; // 이메일 체크
			
	  	var mid = myform.mid.value;
	  	var pwd = myform.pwd.value;
	  	var nickName = myform.nickName.value;
	  	var name = myform.name.value;
	  	var email = myform.email1.value + "@" + myform.email2.value;
	  	var tel = myform.tel1.value + "-" + myform.tel2.value + "-" + myform.tel3.value;
	  	
	  	// 회원 사진 업로드
	  	var fName = myform.fName.value;
	  	var ext = fName.substring(fName.lastIndexOf(".")+1);	// 파일 확장자 발췌
	  	var uExt = ext.toUpperCase();
	  	var maxSize = 1024 * 1024 * 2;	// 업로드할 파일의 최대 용량은 2MByte로 한다.
	  	
	  	if(pwd == "") {
	  		alert("비밀번호를 입력하세요");
	  		myform.pwd.focus();
	  	}
	  	else if(nickName == "") {
	  		alert("닉네임을 입력하세요");
	  		myform.nickName.focus();
	  	}
	  	else if(name == "") {
	  		alert("성명을 입력하세요");
	  		myform.name.focus();
	  	}
	  	else if(!regExpEmail.test(email)) {
	  		alert("이메일을 확인하세요");
	  		myform.email1.focus();
	  	}
	  	// 기타 추가 체크해야 할 항목들을 모두 체크하세요.
	  	else {
	  		if(nickCheckOn == 1) {
	  			//alert("입력처리 되었습니다.!");
	  			var postcode = myform.postcode.value;
	  			var roadAddress = myform.roadAddress.value;
	  			var detailAddress = myform.detailAddress.value;
	  			var extraAddress = myform.extraAddress.value;
	  			var address = postcode + "/" + roadAddress + "/" + detailAddress + "/" + extraAddress
	  			if(address == "///") address = "";
	  			myform.address.value = address;
	  			myform.email.value = email;
	  			myform.tel.value = tel;
	  			
	  			// 사진파일 업로드 체크
	  			if(fName.trim() != "") {
				    	var fileSize = document.getElementById("fName").files[0].size;
				    	
				    	if(uExt != "JPG" && uExt != "GIF" && uExt != "PNG") {
				    		alert("업로드 가능한 파일은 'JPG/GIF/PNG");
				    		return false;
				    	}
				    	else if(fName.indexOf(" ") != -1) {
				    		alert("업로드할 파일명에는 공백을 포함하실수 없습니다.");
				    		return false;
				    	}
				    	else if(fileSize > maxSize) {
				    		alert("업로드할 파일의 크기는 2MByte 이하입니다.");
				    		return false;
				    	}
	  			}
			    	myform.submit();
				}
	  		else {
	  			if(nickCheckOn == 0) {
	  				alert("닉네임, 중복체크버튼을 눌러주세요!");
	  			}
	  		}
	  	}
	  }
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<div class="container" style="padding:30px">
  <form name="myform" method="post" action="${ctp}/member/memUpdate" enctype="multipart/form-data" class="was-validated">
    <h2>회 원 정 보 수 정</h2>
    <br/>
    <div class="form-group">
      아이디 : ${sMid}
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호 : </label>
      <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력하세요." name="pwd" value="${sPwd}" maxlength="9" required/>
    </div>
    <div class="form-group">
      <label for="nickname">닉네임 : &nbsp; &nbsp;<input type="button" value="닉네임 중복체크" class="btn btn-secondary" onclick="nickCheck()"/></label>
      <input type="text" class="form-control" id="nickName" onkeyup="nickReset()"  placeholder="별명을 입력하세요." name="nickName" value="${vo.nickName}" required/>
    </div>
    <div class="form-group">
      <label for="name">성명 :</label>
      <input type="text" class="form-control" id="name" placeholder="성명을 입력하세요." name="name" value="${vo.name}" required/>
    </div>
    <div class="form-group">
      <label for="email">Email address:</label>
				<div class="input-group mb-3">
				  <input type="text" class="form-control" placeholder="Email" id="email1" name="email1" value="${fn:split(vo.email,'@')[0]}" required/>
				  <div class="input-group-append">
				    <select name="email2" class="custom-select">
						  <c:set var="email2" value="${fn:split(vo.email,'@')[1]}"/>
					    <option value="naver.com" 	${email2=='naver.com'   ? 'selected' : ''}>naver.com</option>
					    <option value="hanmail.net" ${email2=='hanmail.net' ? 'selected' : ''}>hanmail.net</option>
					    <option value="hotmail.com" ${email2=='hotmail.com' ? 'selected' : ''}>hotmail.com</option>
					    <option value="gmail.com"   ${email2=='gmail.com'   ? 'selected' : ''}>gmail.com</option>
					    <option value="nate.com"    ${email2=='nate.com'    ? 'selected' : ''}>nate.com</option>
					    <option value="yahoo.com"   ${email2=='yahoo.com'   ? 'selected' : ''}>yahoo.com</option>
					  </select>
				  </div>
				</div>
	  </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="gender" value="남자" ${vo.gender=='남자' ? 'checked' : ''}>남자
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="gender" value="여자" ${vo.gender=='여자' ? 'checked' : ''}>여자
			  </label>
			</div>
    </div>
    <div class="form-group">
      <label for="birthday">생일</label>
			<input type="date" name="birthday" value="${fn:substring(vo.birthday,0, 10)}" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="input-group mb-3">
	      <div class="input-group-prepend">
	        <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
			      <select name="tel1" class="custom-select">
			        <c:set var="tel1" value="${fn:split(vo.tel,'-')[0]}"/>
					    <option value="010" ${tel1=='010' ? 'selected' : ''}>010</option>
					    <option value="02"  ${tel1=='02' ? 'selected' : ''}>서울</option>
					    <option value="031" ${tel1=='031' ? 'selected' : ''}>경기</option>
					    <option value="032" ${tel1=='032' ? 'selected' : ''}>인천</option>
					    <option value="041" ${tel1=='041' ? 'selected' : ''}>충남</option>
					    <option value="042" ${tel1=='042' ? 'selected' : ''}>대전</option>
					    <option value="043" ${tel1=='043' ? 'selected' : ''}>충북</option>
			        <option value="051" ${tel1=='051' ? 'selected' : ''}>부산</option>
			        <option value="052" ${tel1=='052' ? 'selected' : ''}>울산</option>
			        <option value="061" ${tel1=='061' ? 'selected' : ''}>전북</option>
			        <option value="062" ${tel1=='062' ? 'selected' : ''}>광주</option>
					  </select>-
	      </div>
	      <input type="text" name="tel2" value="${fn:split(vo.tel,'-')[1]}" size=4 maxlength=4 class="form-control"/>-
	      <input type="text" name="tel3" value="${fn:split(vo.tel,'-')[2]}" size=4 maxlength=4 class="form-control"/>
	    </div> 
    </div>
    <div class="form-group">
      <label for="address">주소</label>
      <input type="hidden" class="form-control" name="address"/>
      <input type="text" name="postcode" value="${fn:split(vo.address,'/')[0]}" id="sample4_postcode" placeholder="우편번호">
			<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
			<input type="text" name="roadAddress" value="${fn:split(vo.address,'/')[1]}" id="sample4_roadAddress" size="50" placeholder="도로명주소">
			<!-- <input type="text" id="sample4_jibunAddress" placeholder="지번주소"> -->
			<span id="guide" style="color:#999;display:none"></span>
			<input type="text" name="detailAddress" value="${fn:split(vo.address,'/')[2]}" id="sample4_detailAddress" size="30" placeholder="상세주소">
			<input type="text" name="extraAddress" value="${fn:split(vo.address,'/')[3]}" id="sample4_extraAddress" placeholder="참고항목">
    </div>
    <div class="form-group">
	    <label for="homepage">Homepage address:</label>
	    <input type="text" class="form-control" name="homePage" value="${vo.homePage}" placeholder="이메일을 입력하세요." id="homePage"/>
	  </div>
    <div class="form-group">
      <label for="name">직업</label>
      <select class="form-control" id="job" name="job">
        <option ${vo.job=='학생'   ? 'selected' : ''}>학생</option>
        <option ${vo.job=='회사원' ? 'selected' : ''}>회사원</option>
        <option ${vo.job=='공무원' ? 'selected' : ''}>공무원</option>
        <option ${vo.job=='군인'  ? 'selected' : ''}>군인</option>
        <option ${vo.job=='의사'  ? 'selected' : ''}>의사</option>
        <option ${vo.job=='법조인' ? 'selected' : ''}>법조인</option>
        <option ${vo.job=='세무인' ? 'selected' : ''}>세무인</option>
        <option ${vo.job=='자영업' ? 'selected' : ''}>자영업</option>
        <option ${vo.job=='기타'  ? 'selected' : ''}>기타</option>
      </select>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">취미</span> &nbsp; &nbsp;
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="등산" name="hobby" <c:if test="${fn:contains(vo.hobby,'등산')}">checked</c:if>/>등산
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="낚시" name="hobby"<c:if test="${fn:contains(vo.hobby,'낚시')}">checked</c:if>/>낚시
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="수영" name="hobby"<c:if test="${fn:contains(vo.hobby,'수영')}">checked</c:if>/>수영
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="독서" name="hobby"<c:if test="${fn:contains(vo.hobby,'독서')}">checked</c:if>/>독서
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="영화감상" name="hobby" <c:if test="${fn:contains(vo.hobby,'영화감상')}">checked</c:if>/>영화감상
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="바둑" name="hobby" <c:if test="${fn:contains(vo.hobby,'바둑')}">checked</c:if>/>바둑
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="축구" name="hobby" <c:if test="${fn:contains(vo.hobby,'축구')}">checked</c:if>/>축구
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="checkbox" class="form-check-input" value="기타" name="hobby" <c:if test="${fn:contains(vo.hobby,'기타')}">checked</c:if>/>기타
			  </label>
			</div>
    </div>
    <div class="form-group">
      <label for="content">자기소개</label>
      <textarea rows="5" class="form-control" id="content" name="content">${vo.content}</textarea>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">정보공개</span>  &nbsp; &nbsp; 
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="userInfor" value="공개" ${vo.userInfor=='공개'  ? 'checked' : ''}/>공개
			  </label>
			</div>
			<div class="form-check-inline">
			  <label class="form-check-label">
			    <input type="radio" class="form-check-input" name="userInfor" value="비공개" ${vo.userInfor=='비공개'  ? 'checked' : ''}/>비공개
			  </label>
			</div>
    </div>
    <div  class="form-group">
      회원 사진(파일용량:2MByte이내) : <a href="${ctp}/member/${vo.photo}" target="_blank"><img src="${ctp}/member/${vo.photo}" width="80px"/></a>
      <input type="file" name="fName" id="fName" class="form-control-file border"/>
    </div>
    <button type="button" class="btn btn-secondary" onclick="fCheck()">회원정보수정</button>
    <button type="reset" class="btn btn-secondary">다시작성</button>
    <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/member/memMain';">돌아가기</button>
    <input type="hidden" name="mid" value="${sMid}"/>
    <input type="hidden" name="photo" value="${vo.photo}"/>
    <input type="hidden" name="email"/>
    <input type="hidden" name="tel"/>
  </form>
  <p><br/></p>
</div>
<br/>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>