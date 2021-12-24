<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pwdConfirm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br></p>
<div class="container">
  <h2>비밀번호 검색</h2>
  <hr/>
  <form method="post">
    <table class="table table-bordered">
      <tr>
        <th>아이디</th>
        <td><input type="text" name="mid" placeholder="회원 아이디를 입력하세요" class="form-control"/></td>
      </tr>
      <tr>
        <th>이메일</th>
        <td><input type="text" name="toMail" placeholder="회원가입시에 등록된 이메일주소를 입력하세요" class="form-control"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <button type="submit" class="btn btn-secondary">임시비밀번호발급</button> &nbsp;
          <button type="reset" class="btn btn-secondary">다시입력</button> &nbsp;
          <button type="button" onclick="location.href='${ctp}/member/memLogin';" class="btn btn-secondary">돌아가기</button>
        </td>
      </tr>
    </table>
  </form> 
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>