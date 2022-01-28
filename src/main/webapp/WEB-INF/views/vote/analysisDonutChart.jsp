<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>analysisDonutChart.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
    google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['성별', '투표수'],
        ['${genderVos.get(0).gender}(${genderVos.get(0).voteSum}명)', Number('${genderVos.get(0).voteSum}')],
        ['${genderVos.get(1).gender}(${genderVos.get(1).voteSum}명)', Number('${genderVos.get(1).voteSum}')]
      ]);

      var options = {
        title: '${vos.get(0).title}',
        pieHole: 0.4,
        width:700,
        height:400
      };

      var chart = new google.visualization.PieChart(document.getElementById('chartView'));
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
  - 투표 참여인원 : 총 ${genderVos.get(0).voteSum + genderVos.get(1).voteSum}명(중복참여 제외)
  <form name="myform">
    <p>선택 :
    <jsp:include page="partChart.jsp"/>
  </form>
  <div id="chartView"></div>
</div>
</body>
</html>