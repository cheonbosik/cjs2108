<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>analysisAreaChart.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
  	google.charts.load('current', {'packages':['corechart']});
	  google.charts.setOnLoadCallback(drawChart);
	
		function drawChart() {
			var data = google.visualization.arrayToDataTable([
        ['문항','${areaName[0]}','${areaName[1]}','${areaName[2]}','${areaName[3]}','${areaName[4]}','${areaName[5]}','${areaName[6]}'],
				<%--
        ['1번', ${areaVoteNum[0][0]},${areaVoteNum[0][1]},${areaVoteNum[0][2]},${areaVoteNum[0][3]},${areaVoteNum[0][4]},${areaVoteNum[0][5]},${areaVoteNum[0][6]}],
        ['2번', ${areaVoteNum[1][0]},${areaVoteNum[1][1]},${areaVoteNum[1][2]},${areaVoteNum[1][3]},${areaVoteNum[1][4]},${areaVoteNum[1][5]},${areaVoteNum[1][6]}],
        ['3번', ${areaVoteNum[2][0]},${areaVoteNum[2][1]},${areaVoteNum[2][2]},${areaVoteNum[2][3]},${areaVoteNum[2][4]},${areaVoteNum[2][5]},${areaVoteNum[2][6]}],
        ['4번', ${areaVoteNum[3][0]},${areaVoteNum[3][1]},${areaVoteNum[3][2]},${areaVoteNum[3][3]},${areaVoteNum[3][4]},${areaVoteNum[3][5]},${areaVoteNum[3][6]}],
        ['5번', ${areaVoteNum[4][0]},${areaVoteNum[4][1]},${areaVoteNum[4][2]},${areaVoteNum[4][3]},${areaVoteNum[4][4]},${areaVoteNum[4][5]},${areaVoteNum[4][6]}],
        --%>
				<c:forEach var="i" begin="0" end="4">
					['${i+1}번문항', ${areaVoteNum[i][0]}, ${areaVoteNum[i][1]}, ${areaVoteNum[i][2]}, ${areaVoteNum[i][3]}, ${areaVoteNum[i][4]}, ${areaVoteNum[i][5]}, ${areaVoteNum[i][6]}],
				</c:forEach>
      ]);
		
		  var options = {
        title: '${vos.get(0).title}',
        hAxis: {title: '지역',  titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0},
        width: 700,
	    	height: 400
      };
		
		  var chart = new google.visualization.AreaChart(document.getElementById('chartView'));
		  chart.draw(data, options);
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
  - 투표 참여인원 : 총 ${areaSum}명
  <form name="myform">
    <p>선택 :
    <jsp:include page="partChart.jsp"/>
  </form>
  <div id="chartView"></div>
</div>
</body>
</html>