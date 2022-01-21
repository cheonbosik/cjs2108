<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>클릭한 위치에 마커 표시하기</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script>
		function addressCheck(latitude,longitude) {
			var address = document.myform.address.value;
			if(address == "") {
				alert("선택한 지점의 이름을 입력하세요.");
				document.myform.address.focus();
				return false;
			}
			var query = {
					address : address,
					latitude : latitude,
					longitude : longitude
			}
			$.ajax({
				type : "post",
				url  : "${ctp}/study2/kakaoEx1",
				data : query,
				success:function(data) {
					if(data == 1) {
					  alert("선택한 지점이 등록되었습니다.");
					}
					else {
						alert("이미 같은 지점이 있습니다. 이름을 변경해서 다시 저장하세요.");
					}
				},
				error : function() {
					alert("전송 오류!");
				}
			});
		}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
  <h2>클릭한 위치에 마커 표시하기</h2>
  <hr/>
  
	<div id="map" style="width:100%;height:500px;"></div>
	<p><em>지도를 클릭해주세요!</em></p>
	<form name="myform">
		<div id="clickLatlng"></div>
	</form>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e912774894fd7ebc1870f2fde5f3d25e"></script>
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = { 
		        center: new kakao.maps.LatLng(36.63508797975421, 127.45959376343134), // 지도의 중심좌표
		        level: 2 // 지도의 확대 레벨
		    };
		
		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
		
		// 지도를 클릭한 위치에 표출할 마커입니다
		var marker = new kakao.maps.Marker({
		    // 지도 중심좌표에 마커를 생성합니다 
		    position: map.getCenter() 
		}); 
		// 지도에 마커를 표시합니다
		marker.setMap(map);
		
		// 지도에 클릭 이벤트를 등록합니다
		// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
		    
		    // 클릭한 위도, 경도 정보를 가져옵니다 
		    var latlng = mouseEvent.latLng; 
		    
		    // 마커 위치를 클릭한 위치로 옮깁니다
				marker.setPosition(latlng);
		    
		    var message = '클릭한 위치의 위도는 <font color=red>' + latlng.getLat() + '</font> 이고, ';
		    message += '경도는 <font color=red>' + latlng.getLng() + '</font> 입니다';
		    message += '&nbsp; <input type="button" value="처음위치로복귀" onclick="location.reload();"/><br/>';
		    message += '<p>선택한 지점의 장소명 : <input type="text" name="address" autofocus /> <input type="button" value="장소저장" onclick="addressCheck('+latlng.getLat()+','+latlng.getLng()+')"/></p>';
		    
		    //var resultDiv = document.getElementById('clickLatlng'); 
		    //resultDiv.innerHTML = message;
		    document.getElementById('clickLatlng').innerHTML = message;
		});
	</script>
	
	<hr/>
	<div>
	  <p>
      <%@ include file="/WEB-INF/views/study/kakaomap/kakaoMenu.jsp" %>
	  </p>
	</div>
	
	</div>
  <p><br/></p>
	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
