<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eco</title>
<link rel="stylesheet" type="text/css"
	href="/resources/css/common.css?after">
	
<script>
    function validateForm() {
    	const userIdInput = document.querySelector('input[name="user_id"]');
		const userPwInput = document.querySelector('input[name="user_pw"]');
			
		const userId = userIdInput.value.trim();
		const userPw = userPwInput.value.trim();
        
		if (userId === "" || userId === null) {
            alert("아이디를 입력해주세요.");
            userIdInput.focus();
            return false; // Prevent form submission
        }
     
        if (userPw === "" || userPw === null) {
            alert("비밀번호를 입력해주세요.");
            userPwInput.focus();
            return false;
        }
        return true;
    }
   
</script>
</head>
<body>
	<div class="container">
		<a href="/"><img src="/resources/img/icon2.png" class="icon-2"></a>
    	<h2>로그인</h2>
    	<div class="inner-container">
		<form action="login" method="post" class="signup-form" onsubmit="return validateForm()">
			<!-- 아이디 입력 그룹 -->
			<div class="form-group">
			 	<label for="user_id">아이디</label>
                <div class="id-check-group">
                    <input class="input-area" type="text" name="user_id" id="user_id" autocomplete="off">
                </div>
			</div>
			
			<!-- 비밀번호 입력 그룹 --> 
			<div class="form-group">
                <label for="user_pw">비밀번호</label>
                <input  class="input-area" type="password" name="user_pw" id="user_pw" autocomplete="new-password">
            </div>
            
            <!-- 로그인 회원가입 버튼 그룹 -->
            <div class="form-group">
               <input class="input-submit-area" type="submit" value="로그인"/>
               <input class="input-submit-area" type="button" onclick="location.href='/signup'" value="회원가입"/>
            </div>
			
			<!-- 구분선 -->
			<div class="form-group-center">
				<div class="green-line"></div>
			</div>
			
			<!-- 소셜로그인(구글) -->       
			<div class="form-group-center">
            	<a href="/login/googleLogin"> 
            		<img class="login-button" src="../../resources/img/google_signin.png" alt="Google 로그인">
            	</a>
            </div>
            
            <!-- 소셜로그인(네이버) --> 
  			<div class="form-group-center">
            	<a href="/login/naverLogin"> 
            		<img class="login-button" src="../../resources/img/naver_login.png" alt="네이버 로그인">
            	</a>
            </div>
		</form>
		</div>
	</div>
		
	<!-- 여긴 높이 보정용 푸터 입니다. -->
	<footer></footer>
	<c:if test="${not empty errorMessage}">
   	 	<script>
        	alert('${errorMessage}');
    	</script>
	</c:if>
	
	
</body>
</html>