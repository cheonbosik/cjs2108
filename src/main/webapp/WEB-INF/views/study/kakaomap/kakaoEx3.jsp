<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>키워드로 장소검색하기</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script>
	  var address = '${address}';
	  var latitude = '${vo.latitude}';
	  var longitude = '${vo.longitude}';
	  function addressSearch() {
		  address = document.getElementById("address").value;
		  if(address == "") {
			  alert("DB에 저장된 지역명을 선택하세요");
			  return false;
		  }
		  location.href = "${ctp}/study2/kakaoEx3?address="+address;
	  }
	  
	  function addressDelete() {
		  var address = document.getElementById("address").value;
		  if(address == "") {
			  alert("지역명을 선택하세요");
			  return false;
		  }
		  var ans = confirm("선택하신 지역명을 DB에서 삭제하시겠습니까?");
		  if(!ans) return false;
		  $.ajax({
			  type : "post",
			  url  : "${ctp}/study2/kakaoEx3Delete",
			  data : {address : address},
			  success:function() {
				  alert("DB에 저장된 주소가 삭제되었습니다.");
				  location.href="${ctp}/study2/kakaoEx3";
			  },
			  error : function() {
				  alert("전송오류!");
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
  <form name="myform" method="post">
		<div>
		  <font size="4"><b>저장된 지명으로 검색하기</b></font>
		  <select name="address" id="address">
		    <option value="">저장된지명선택</option>
		    <c:forEach var="vo" items="${vos}">
		    	<option value="${vo.address}" <c:if test="${vo.address == address}">selected</c:if>>${vo.address}</option>
		    </c:forEach>
		  </select>
		  <input type="button" value="지역검색" onclick="addressSearch()"/>
		  <input type="button" value="지역명DB에서삭제" onclick="addressDelete()"/>
		</div>
	</form>
	<hr/>
  <div id="map" style="width:100%;height:600px;"></div>
  <hr/>
	<%@ include file="/WEB-INF/views/study/kakaomap/kakaoMenu.jsp" %>
 
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e912774894fd7ebc1870f2fde5f3d25e&libraries=services"></script>
<script>
// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(); 

// 키워드로 장소를 검색합니다
ps.keywordSearch(address, placesSearchCB); 

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();
				/* 
        for (var i=0; i<data.length; i++) {
            displayMarker(data[i]);    
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }       
				*/
				// 아래로 수정함(1곳의 좌표만 찾아서 표시해주기위해 앞쪽 for문을 주석처리하고 아래쪽 코드로 수정함(변수로 위도와 경도를 받았다))
        displayMarker(data[0]);		// 마커함수를 호출한다.
        //bounds.extend(new kakao.maps.LatLng(data[0].y, data[0].x));
        //bounds.extend(new kakao.maps.LatLng(37.00594409863884, 127.23070101677001));		// 연습
        bounds.extend(new kakao.maps.LatLng(latitude, longitude));
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    
    // 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        //position: new kakao.maps.LatLng(place.y, place.x) 
        //position: new kakao.maps.LatLng(37.00594409863884, 127.23070101677001)		// 연습 
        // 마커를 앞에서 정의한 '위도/경도'변수로 받아서 처리했다.(앞의 문장은 주석처리함)
        position: new kakao.maps.LatLng(latitude, longitude) 
    });

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });
}
</script>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>