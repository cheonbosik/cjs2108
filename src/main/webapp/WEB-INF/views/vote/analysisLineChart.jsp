<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>analysisLineChart.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
	  google.charts.load('current', {'packages':['line']});
	  google.charts.setOnLoadCallback(drawChart);
	
		function drawChart() {
		  var data = new google.visualization.DataTable();
		  data.addColumn('number', '연령(대)');
		  data.addColumn('number', '투표수');
		
		  data.addRows([
			  <%--
		    [${arrAge[0]}, ${arrAgeSum[0]}],
		    [${arrAge[1]}, ${arrAgeSum[1]}],
		    [${arrAge[2]}, ${arrAgeSum[2]}],
		    [${arrAge[3]}, ${arrAgeSum[3]}],
		    [${arrAge[4]}, ${arrAgeSum[4]}],
		    [${arrAge[5]}, ${arrAgeSum[5]}],
		    --%>
			  <c:forEach var="i" begin="0" end="5" varStatus="st">
			  	[${arrAge[i]}, ${arrAgeSum[i]}],
			  </c:forEach>
		  ]);
		
		  var options = {
		    chart: {
		      title: '연령대별로 각 문항을 선택한 투표결과',
		      subtitle: '${vos.get(0).title}'
		    },
		    width: 700,
		    height: 400,
		    axes: {
		      x: {
		        0: {side: 'top'}
		      }
		    }
		  };
		
		  var chart = new google.charts.Line(document.getElementById('chartView'));
		
		  chart.draw(data, google.charts.Line.convertOptions(options));
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
  - 투표 참여인원 : 총 ${ageSum}명(중복배제)
  <form name="myform">
    <p>선택 :
    <jsp:include page="partChart.jsp"/>
  </form>
  <div id="chartView"></div>
</div>
</body>
</html>