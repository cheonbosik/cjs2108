<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>adGuestList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    // 전체선택
    $(function(){
    	$("#checkAll").click(function(){
    		if($("#checkAll").prop("checked")) {
	    		$(".chk").prop("checked", true);
    		}
    		else {
	    		$(".chk").prop("checked", false);
    		}
    	});
    });
    
    // 선택항목 반전
    $(function(){
    	$("#reverseAll").click(function(){
    		$(".chk").prop("checked", function(){
    			return !$(this).prop("checked");
    		});
    	});
    });
    
    // 선택항목 삭제하기(ajax처리하기)
    function selectDelCheck() {
    	var ans = confirm("선택된 모든 게시물을 삭제 하시겠습니까?");
    	if(!ans) return false;
    	var delItems = "";
    	for(var i=0; i<myform.chk.length; i++) {
    		if(myform.chk[i].checked == true) delItems += myform.chk[i].value + "/";
    	}
  		
    	$.ajax({
    		type : "post",
    		data : {delItems : delItems},
    		success:function() {
    			location.reload();
    		},
    		error  :function() {
    			alert("전송오류!!");
    		}
    	});
    }
  </script>
</head>
<body>
<p><br></p>
<div class="m-4">
  <form name="myform">
	  <table class="table table-borderless m-0">
	    <tr class="text-center">
	      <td><h3>방 명 록 리 스 트(총:${totRecCnt}건)</h3></td>
	    </tr>
	    <tr>
	      <td>
	        <input type="checkbox" id="checkAll"/>전체선택/해제 &nbsp;
	        <input type="checkbox" id="reverseAll"/>선택반전 &nbsp;
	        <input type="button" value="선택항목삭제" onclick="selectDelCheck()" class="btn btn-secondary btn-sm"/>
	      </td>
	    </tr>
	  </table>
	  <table class="table table-hover">
	    <tr class="table-dark text-dark text-center">
	      <th>선택</th>
	      <th>번호</th>
	      <th>올린이</th>
	      <th>방문소감</th>
	      <th>올린날짜</th>
	      <th>접속IP</th>
	    </tr>
	    <c:forEach var="vo" items="${vos}">
	      <tr class="text-center">
		      <td><input type="checkbox" name="chk" class="chk" value="${vo.idx}"/></td>
		      <td>${curScrStrarNo}</td>
		      <td>${vo.name}</td>
		      <td class="text-left">${fn:replace(vo.content,newLine,"<br/>")}</td>
		      <td>${fn:substring(vo.VDate,0,10)}</td>
		      <td>${vo.hostIp}</td>
	      </tr>
	      <c:set var="curScrStrarNo" value="${curScrStrarNo - 1}"/>
	    </c:forEach>
	  </table>
  </form>
</div>
<hr/>
<!-- 페이징처리 시작 -->
<c:if test="${totPage == 0}"><p style="text-align:center"><font color="red"><b>자료가 없습니다.</b></font></p></c:if>
<c:if test="${totPage != 0}">
	<div style="text-align:center">
	  <c:if test="${pag != 1}"><a href="${ctp}/admin/adGuestList?pag=1">◁◁</a></c:if> &nbsp;&nbsp;
	  <c:if test="${pag > 1}"><a href="${ctp}/admin/adGuestList?pag=${pag-1}">◀</a></c:if>
	  &nbsp;&nbsp; ${pag}Page / ${totPage}pages &nbsp;&nbsp;
	  <c:if test="${pag < totPage}"><a href="${ctp}/admin/adGuestList?pag=${pag+1}">▶</a></c:if> &nbsp;&nbsp;
	  <c:if test="${pag != totPage}"><a href="${ctp}/admin/adGuestList?pag=${totPage}">▷▷</a></c:if>
	</div>
</c:if>
<!-- 페이징처리 끝 -->
</body>
</html>