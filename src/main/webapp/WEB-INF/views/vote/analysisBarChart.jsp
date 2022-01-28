<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>analysisBarChart.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	
	<script type="text/javascript">
		// Load google charts
		google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawChart);
		
		// Draw the chart and set the chart values
		function drawChart() {
			<c:set var="subTitles" value="${fn:split(subTitles,'/')}"/>
			<%-- var pVo = "${pVo.subTitle}".split("/"); --%>
			var data = google.visualization.arrayToDataTable([
				<%--
        ['항목', '투표수','지역별','연령별'],
        ['${fn:substring(subTitles[0],0,12)}', Number("${vos.get(0).voteSum}"), 4, 2],
        ['${fn:substring(subTitles[1],0,12)}', Number("${vos.get(1).voteSum}"),  4, 2],
        ['${fn:substring(subTitles[2],0,12)}', Number("${vos.get(2).voteSum}"), 11, 3],
        ['${fn:substring(subTitles[3],0,12)}', Number("${vos.get(3).voteSum}"),  5, 3],
        ['${fn:substring(subTitles[4],0,12)}', Number("${vos.get(4).voteSum}"),  5, 3]
				--%>
        ['항목', '투표수'],
        ['${fn:substring(subTitles[0],0,12)}', ${arrVoteSum[0]}],
        ['${fn:substring(subTitles[1],0,12)}', ${arrVoteSum[1]}],
        ['${fn:substring(subTitles[2],0,12)}', ${arrVoteSum[2]}],
        ['${fn:substring(subTitles[3],0,12)}', ${arrVoteSum[3]}],
        ['${fn:substring(subTitles[4],0,12)}', ${arrVoteSum[4]}],
      ]);
			var options = {'title':'주제 : ${vos.get(0).title}', 'width':680, 'height':380};
			var chart = new google.charts.Bar(document.getElementById('chartView'));
      chart.draw(data, google.charts.Bar.convertOptions(options));
		}
		
		function changeChart() {
			var chartFlag = myform.choiceChart.value;
			location.href="${ctp}/vote/chartAnalysis?idx=${vos.get(0).popularIdx}&chartFlag="+chartFlag;
		}
		
	</script>
</head>
<body>
<div class="container">
  - 주제명 : ${vos.get(0).title}<br/>
  - 투표 참여인원 : 총 ${voteTotCnt}명
  <form name="myform">
    <p>선택 :
    <%-- 
    <select name="choiceChart" onchange="changeChart()">
      <option value="0" <c:if test="${flag==0}"> selected </c:if>>항목별 투표결과</option>
      <option value="1">연령별 투표결과</option>
      <option value="2">지역별 투표결과</option>
      <option value="3">성별 투표결과</option>
    </select>
     --%>
    <jsp:include page="partChart.jsp"/>
  </form>
  <div id="chartView"></div>
</div>
</body>
</html>