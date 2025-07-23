<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // 오늘 날짜 생성
    java.time.LocalDate today = java.time.LocalDate.now();
	java.time.LocalDate firstDay = today.withDayOfMonth(1);
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
<script>
    function validateForm() {
    	const userNumber = document.querySelector('input[name="user_num"]');
		const userNmInput = document.querySelector('input[name="user_nm"]');
		const userNum = userNumber.value.trim();
		const userNm = userNmInput.value.trim();
        
		if (userNum === "" || userNum === null) {
            alert("납입자 번호를 입력해주세요.");
            userNumber.focus();
            return false; // Prevent form submission
		}
        if (userNm === "" || userNm === null) {
            alert("성함을 입력해주세요.");
            userNmInput.focus();
            return false;
        }
            
        let startDay = document.getElementById("startDate").value;
		let endDay = document.getElementById("endDate").value;
		
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
				<div class="title-container">
					<c:choose>
					    <c:when test="${empty sessionScope.currentUserInfo}">
					        <h2>비회원 사용 요금 조회</h2>
					    </c:when>
					    <c:otherwise>
					    	<h2>간편 요금 조회</h2>
					    </c:otherwise>
					</c:choose>
				</div>
				<!-- 버튼 내비게이션 -->
				<div class="header-container">
					<!-- 위쪽 텍스트 -->
					<div class="header-inner-container">
						<c:if test="${not empty sessionScope.currentUserInfo}">
							<span>${currentUserInfo.user_nm} 님, 환영합니다.</span>
						</c:if>
					</div>
					<!-- 아래쪽 버튼 -->
					<div class="header-inner-container">
						<c:if test="${not empty sessionScope.currentUserInfo}">
							<button class="green-btn-2" onclick='location.href="/mypage"'>회원정보수정</button>
							<button class="green-btn-2" onclick='location.href="/login/logout"'>로그아웃</button>
						</c:if>
						<c:choose>
						    <c:when test="${sessionScope.currentUserInfo.admin_yn.toString() eq 'Y'}">
						        <button class="green-btn-2" type="button" onclick="location.href='/admin'">관리자 기능</button>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
						<c:if test="${empty sessionScope.currentUserInfo}">
							<button class="green-btn-2" onclick='location.href="/login"'>로그인</button>
							<button class="green-btn-2" onclick='location.href="/signup"'>회원가입</button>
						</c:if>
					</div>
				</div>
			</div>
			<div class="green-line"></div>
			<div class="main-container">
				<a href="javascript:void(0);" class="page-tab-a" onclick="goToMyUsagePage()">▶ 내 사용량 조회</a>
				<a class="page-tab-a" href="/">▶ 메인페이지</a> 
			</div>
			<div class="green-line"></div>
		</div>
	    <div class="inner-container">
	    	<div class="data-box">
	    		<div class="form-title">간편 요금 조회</div>
		    	<div class="table-box">
			        <form action="/simplelookup" method="post" onsubmit="return validateForm()" class="form-box-2">
		        		<div class="form-group">
			                <label for="user_num">납입자 번호</label>
			                <input  class="input-area" type="password" name="user_num" id="user_num" value="${ empty param.user_num ? '' : param.user_num }" autocomplete="off">
			            </div>
						
						<div class="form-group">
			                <label for="user_nm">이름</label>
			                <input class="input-area" type="text" name="user_nm" id="user_nm" value="${ empty param.user_nm ? '' : param.user_nm }" autocomplete="off">
			            </div>

						<div class="form-group">
			                <label for="user_local">조회 기간</label>
			                <div class="inner-form-box-2">
				                <input class="input-area" type="date" name="startDate" id="startDate" autocomplete="off" value="${not empty param.startDate ? param.startDate : firstDayStr}" pattern="yyyy-MM-dd">
				                <p>~</p>
				                <input class="input-area" type="date" name="endDate" id="endDate" autocomplete="off" value="${not empty param.endDate ? param.endDate : lastDayStr}" pattern="yyyy-MM-dd">
			                </div>
			                <div>* 최대 24개월 분의 자료만 조회가 가능합니다.</div>
			            </div>
			            <input class="input-submit-area" type="submit" value="조회하기">
			        </form>
		        </div>
		        
		        
		        <div class="title">지정 기간 요금</div>
				<div class="table-box">
					<table>
						<colgroup>
							<col width="50%">
							<col width="50%">
						</colgroup>
						<tr>
							<th>도시가스 사용량</th>
							<th>전기 사용량</th>
						</tr>
						<tr>
							<td>
								<c:choose>
							        <c:when test="${not empty gasCharge}">
							            ${gasCharge.gasUsageAmount} ㎥
							        </c:when>
							        <c:otherwise>
							            ${gasChargeMsg}
							        </c:otherwise>
							    </c:choose>
							</td>
							<td>
								<c:choose>
							        <c:when test="${not empty elecCharge}">
							            ${elecCharge.elecUsageAmount} kWh
							        </c:when>
							        <c:otherwise>
							            ${elecChargeMsg}
							        </c:otherwise>
							    </c:choose>
							</td>
						</tr>
					</table>
					
					<table>
						<colgroup>
							<col width="50%">
							<col width="50%">
						</colgroup>
						<tr>
							<th>도시가스 요금</th>
							<th>전기 요금</th>
						</tr>
						<tr>
							<td>
								<c:choose>
							        <c:when test="${not empty gasCharge}">
							            <fmt:formatNumber value="${gasCharge.totalCharge}" type="currency" />
							        </c:when>
							        <c:otherwise>
							            ${gasChargeMsg}
							        </c:otherwise>
							    </c:choose>
							</td>
							<td>
								<c:choose>
							        <c:when test="${not empty elecCharge}">
							        	<fmt:formatNumber value="${elecCharge.totalCharge}" type="currency" />
							        </c:when>
							        <c:otherwise>
							            ${elecChargeMsg}
							        </c:otherwise>
							    </c:choose>
							</td>
						</tr>
					</table>
				</div>
	        </div>
	    </div>
	</div>
	<footer></footer>
	<c:if test="${not empty msg}">
	    <script>
	        alert('${msg}');
	    </script>
	</c:if>
	<script>
		const isLoggedIn = ${not empty sessionScope.currentUserInfo};
		function goToMyUsagePage() {
			if (isLoggedIn) {
				location.href = '/usage'; // 실제 내 정보 보기 페이지로 변경
			} else {
				alert('로그인이 필요합니다.');
				location.href = '/login';
			}
		}
	</script>
</body>
</html>