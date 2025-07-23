<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eco</title>
<link rel="stylesheet" type="text/css"
	href="/resources/css/common.css?after">
<script src="/resources/js/signup.js"></script>
</head>
<body>
	<div class="container">
		<a href="/"><img src="/resources/img/icon2.png" class="icon-2"></a>
		<h2>회원 가입</h2>
		<div class="inner-container">
			<form id="signupForm" action="signup" method="post"
				class="signup-form" onsubmit="return validateForm()">

				<div class="form-group">
					<label for="user_id">아이디</label>
					<div class="id-check-group">
						<input class="input-area" type="text" name="user_id" id="user_id"
							autocomplete="off"> <input class="input-btn-area"
							type="button" onclick="checkDuplicateId()" value="중복확인">

					</div>
				</div>

				<div class="form-group">
					<label for="user_pw">비밀번호</label> <input class="input-area"
						type="password" name="user_pw" id="user_pw"
						autocomplete="new-password" oninput="checkPwd()">


					<div class="form-group">
						<label for="user_pw_ck">비밀번호 확인</label> <input class="input-area"
							type="password" name="user_pw_ck" id="user_pw_ck"
							autocomplete="new-password" oninput="checkPwd()">
						<div class="invalid_feedback"></div>
					</div>

					<div class="form-group">
						<label for="user_nm">이름</label> <input class="input-area"
							type="text" name="user_nm" id="user_nm" autocomplete="off">
					</div>

					<div class="form-group">
						<label for="user_local">지역 (선택사항)</label> 
						<input class="input-area" type="text" name="user_local" id="user_local"
							autocomplete="off">
					</div>
							
					<div class="form-group">
						<label for="user_local">이메일 입력</label> 
						<div class="id-check-group"> 
							<input class="input-area" type="email" name="user_email" id="user_email" autocomplete="off">
							<input type="button" class="input-btn-area" onclick="sendEmailCode()" value="인증코드 보내기"></input>
						</div>
					</div>
					
					<div class="form-group">	
						<label for="email_code">인증코드 입력</label> 
						<div class="id-check-group"> 
							<input class="input-area" type="text" id="email_code" autocomplete="off">
							<input type="button" class="input-btn-area" onclick="verifyEmailCode()" value="코드 확인"></input>
						</div>
						<div id="email_status"></div>
					</div>
					<input class="input-submit-area" type="submit" value="가입하기">
				</div>
			</form>
		</div>

	<!-- 여긴 높이 보정용 푸터 입니다. -->
	<footer></footer>

	</div>
	
</body>
</html>