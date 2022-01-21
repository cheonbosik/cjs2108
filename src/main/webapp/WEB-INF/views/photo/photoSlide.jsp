<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctp}/css/img.css?after">

<div style="clear:both;"></div>
<div class="slideshow-container">
  <c:forEach var="vo" items="${vos}" varStatus="st">
		<div class="imgSlides fade">
		  <div class="numbertext">${st.count} / ${imgCnt}</div>
		  <img src="${ctp}/photo/${vo.firstFile}" style="width:100%">
		  <div class="text">${vo.title}</div>
		</div>
	</c:forEach>
	<a class="prev" onclick="plusSlides(-2)">&#10094;</a>		<!-- 진행중이기에 -1이 아닌 -2를 빼어주었다. -->
	<a class="next" onclick="plusSlides(1)">&#10095;</a>
</div>
<br/>
<div style="text-align:center">
	<c:forEach var="i" begin="0" end="${fn:length(vos)-1}">
	  <span class="dot" onclick="currentSlide(${i})" title="${vos[i].title}"></span>
  </c:forEach> 
</div>

<script>
	// Automatic Slideshow - change image every 4 seconds
	var slideIndex = 0;
	var clearTime;
	imgShow();
	
	function imgShow() {
	  var i;
	  var x = document.getElementsByClassName("imgSlides");
	  for (i = 0; i < x.length; i++) {
	    x[i].style.display = "none";  
	  }
	  slideIndex++;
	  console.log("slideIndex : " + slideIndex);
	  if (slideIndex > x.length) {slideIndex = 0}    
	  if (slideIndex != 0) x[slideIndex-1].style.display = "block";  
	  clearTime = setTimeout(imgShow, 4000);    
	}
	function currentSlide(n) {
	  imgShow(slideIndex = n);
	}
	function plusSlides(n) {
	  imgShow(slideIndex += n);
	  clearTimeout(clearTime);
	}
</script>