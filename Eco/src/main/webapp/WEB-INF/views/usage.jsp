<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // 오늘 날짜 생성
    java.time.LocalDate today = java.time.LocalDate.now();
	java.time.LocalDate firstDay = today.minusMonths(3).withDayOfMonth(1);
	java.time.LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

	request.setAttribute("firstDayStr", firstDay.toString());
	request.setAttribute("lastDayStr", lastDay.toString());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eco</title>
<link rel="stylesheet" type="text/css" href="/resources/css/common.css?after">
<link rel="stylesheet" type="text/css" href="/resources/css/usage.css?after">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
.box1{background-color: #008000; font-weight: bold; color: white;}
.box2{background-color: #c5ee8f; color: #000000;}
</style>
	<script>
		function validateDates(form) {
			let startDay = document.getElementById("startDate").value;
			let endDay = document.getElementById("endDate").value;
			if(!startDay){
				alert("시작일을 지정하여 주세요")
				return false;
			}
			if(!endDay){
				alert("종료일을 지정하여 주세요")
				return false;
			}
			
			let maxDiff = 24 * 30 * 24 * 60 * 60 * 1000;
			let start = new Date(startDay);
			let end = new Date(endDay);
		    let diff = end - start;
			if(diff > maxDiff) {
				alert("최대 24개월 사용량만 조회 가능합니다.");
				return false;
			}
			if(startDay && endDay && startDay > endDay) {
				alert("시작일은 종료일보다 빠른 날짜여야 합니다.");
				return false;
			}
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="inner-container">
			<div class="head-box">
				<!-- 아이콘 -->
				<a href="/"><img src="/resources/img/icon2.png" class="icon"></a>
				<!-- 메인화면 글씨 -->
				<div class="title-container"><h2>사용량 조회 페이지</h2></div>
				<!-- 버튼 내비게이션 -->
				<div class="header-container">
					<!-- 위쪽 텍스트 -->
					<div class="header-inner-container">
						<span>${userName} 님, 환영합니다.</span> 
					</div>
					<!-- 아래쪽 버튼 -->
					<div class="header-inner-container">
						<button class="green-btn-2" onclick='location.href="/mypage"'>회원정보수정</button>
						<button class="green-btn-2" onclick='location.href="/login/logout"'>로그아웃</button>
						<c:if test="${IsAdmin.toString() eq 'Y'}">
							<button class="green-btn-2" onclick="location.href='/admin'">관리자 기능</button>
						</c:if>
					</div>
				</div>
			</div>
			<div class="green-line"></div>
			<div class="main-container">
				<!-- 내 정보 보기 버튼 (로그인 안 되어 있으면 로그인 페이지로 이동) -->
				<a class="page-tab-a" href="/simplelookup">▶ 간편 요금 조회</a> 
				<a class="page-tab-a" href="/">▶ 메인페이지</a> 
			</div>
			<div class="green-line"></div>
		</div>
		<div class="inner-container">
			<div class="select-box">
				<a href="/usage" class="box1">사용량</a>
				<a href="/charge" class="box2">요금</a>
			</div>
			<div class="data-box">
				<div class="title">이번 달 냉/난방 사용 현황</div>
				<div class="table-box">
					<table>
						<colgroup>
							<col width="50%">
							<col width="50%">
						</colgroup>
						<tr>
							<th>도시가스</th>
							<th>전기</th>
						</tr>
						<tr>
							<td>
								<c:choose>
							        <c:when test="${not empty gasUsage}">
							            ${gasUsage.gasUsageAmount} ㎥
							        </c:when>
							        <c:otherwise>
							            ${gasUsageMsg}
							        </c:otherwise>
							    </c:choose>
							</td>
							<td>
								<c:choose>
							        <c:when test="${not empty elecUsage}">
							            ${elecUsage.elecUsageAmount} kWh
							        </c:when>
							        <c:otherwise>
							            ${elecUsageMsg}
							        </c:otherwise>
							    </c:choose>
							</td>
						</tr>
					</table>
				</div>
				<div class="title">냉/난방 사용 이력</div>
				<div class="chart-box">
					<div class="chart-container">
						<canvas class="usageChart" id="monthChart"></canvas>
					</div>
				</div>
				<div class="title">냉/난방 사용 이력</div>
				<form method="get" action="/usage/period" onsubmit="return validateDates(this)" class="form-box">
					<div class="inner-form-box">
						<span>기간 : </span>
						<input type="date" name="startDate" id="startDate" value="${not empty param.startDate ? param.startDate : firstDayStr}" pattern="yyyy-MM-dd">
						 ~ <input type="date" name="endDate" id="endDate" value="${not empty param.endDate ? param.endDate : lastDayStr}" pattern="yyyy-MM-dd">
						<input type="submit" class="green-btn-2" value="조회">
					</div>
					<div>* 최대 24개월 분의 자료만 조회가 가능합니다.</div>
				</form>				
				<div class="table-box">
					<table>
						<caption class="text-bold">가스 사용 상세 내역</caption>
						<colgroup>
							<col width="25%">
							<col width="25%">
							<col width="25%">
							<col width="25%">
						</colgroup>
						<tr>
							<th>용도</th>
							<th>표준원가</th>
							<th>사용량</th>
							<th>날짜</th>
						</tr>
						<c:choose>
							<c:when test="${not empty gasUse}">
								<c:forEach var="item" items="${gasUse}">
									<tr>
										<td>${item.usageType}</td>
										<td>${item.unitCharge}</td>
										<td>${item.gas_usage} ㎥</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${ item.gas_time }"/></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">${ gasUsageDetailMsg }</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<table>
						<caption class="text-bold">전기 사용 상세 내역</caption>
						<colgroup>
							<col width="25%">
							<col width="25%">
							<col width="25%">
							<col width="25%">
						</colgroup>
						<tr>
							<th>타입</th>
							<th>표준원가</th>
							<th>사용량</th>
							<th>날짜</th>
						</tr>
						<c:choose>
							<c:when test="${not empty elecUse}">
								<c:forEach var="item" items="${elecUse}">
									<tr>
										<td>${item.usageType}</td>
										<td>${item.unitCharge}</td>
										<td>${item.elec_usage} kWh</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${ item.elec_time }"/></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">${ elecUsageDetailMsg }</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</div>
		</div>
	</div>
		
	<!-- 여긴 높이 보정용 푸터 입니다. -->
	<footer></footer>
	
	<script>
		let jData = JSON.parse('${json}');
		
		let monthList = new Array();
		let gasMonthList = new Array();
		let elecMonthList = new Array();
		
		for(i=0; i<jData.length;i++){
			let j = jData[i];
			monthList.push(j.user_month);
			gasMonthList.push(j.gasUsageMonth);
			elecMonthList.push(j.elecUsageMonth);
		}
		
		const ctx = document.getElementById('monthChart').getContext('2d');
	    const myChart = new Chart(ctx, {
	        type: 'line',
	        data: {
	            labels: monthList,
	            datasets: [
	                {
	                    label: '도시가스',
	                    data: gasMonthList,
	                    backgroundColor: 'rgba(95, 164, 0, 0.2)',
	                    borderColor: 'rgba(95, 164, 0, 1)',
	                    borderWidth: 2,
	                    tension: 0.3
	                },
	                {
	                    label: '전기',
	                    data: elecMonthList,
	                    backgroundColor: 'rgba(255, 166, 0, 0.2)',
	                    borderColor: 'rgba(255, 166, 0, 1)',
	                    borderWidth: 2,
	                    tension: 0.3
	                }
	            ]
	        },
	        options: {
	        	responsive: true,
	        	maintainAspectRatio: false,
	            scales: {
	                y: {
	                    beginAtZero: true
	                }
	            }
	        }
	    });
	</script>
</body>
</html>