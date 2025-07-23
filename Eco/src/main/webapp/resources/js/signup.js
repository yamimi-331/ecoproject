let isIdChecked = false; // 아이디 중복 확인 완료 여부
let isPwdValid = false;  // 비밀번호 확인 완료 여부
let isEmailVerified = false; // 이메일 인증 완료 여부

function validateForm() {
    const userIdInput = document.querySelector('input[name="user_id"]');
    const userPwInput = document.querySelector('input[name="user_pw"]');
    const userNmInput = document.querySelector('input[name="user_nm"]');
        
    const userId = userIdInput.value.trim();
    const userPw = userPwInput.value.trim();
    const userNm = userNmInput.value.trim();
    
    if (userId === "" || userId === null) {
        alert("아이디를 입력해주세요.");
        userIdInput.focus();
        return false; // Prevent form submission
    }
    if (!isIdChecked) {
        alert("아이디 중복 확인을 해주세요.");
        userIdInput.focus();
        return false;
    }
    if (userPw === "" || userPw === null) {
        alert("비밀번호를 입력해주세요.");
        userPwInput.focus();
        return false;
    }
    if (!isPwdValid) {
        alert("비밀번호 확인을 완료해주세요.");
        document.querySelector("#user_pw_ck").focus();
        return false;
    }
    if (userNm === "" || userNm === null) {
        alert("이름을 입력해주세요.");
        userNmInput.focus();
        return false;
    }
	if (!isEmailVerified) {
	    alert("이메일 인증을 완료해주세요.");
	    document.getElementById("email_code").focus();
	    return false;
	}
    return true;
}
// 아이디 중복 확인
function checkDuplicateId() {
        const userId = document.querySelector('input[name="user_id"]').value;
        if (!userId) {
            alert("아이디를 입력하세요.");
            return;
        }

        fetch("/check-id?user_id=" + encodeURIComponent(userId))
            .then(res => res.text())
            .then(result => {
                if (result === "duplicate") {
                    alert("이미 사용 중인 아이디입니다.");
                    isIdChecked = false;
                } else {
                    alert("사용 가능한 아이디입니다!");
                    isIdChecked = true;
                }
            })
            .catch(err => {
                console.error("중복 확인 에러:", err);
                isIdChecked = false;
            });
}

// 비밀번호 확인 검사
function checkPwd() {
    const pw = document.querySelector("#user_pw").value;
    const pw_ck = document.querySelector("#user_pw_ck").value;

    const pw_ckInput = document.querySelector("#user_pw_ck");
    const feedback = document.querySelector(".invalid_feedback");

    pw_ckInput.classList.remove("is-valid", "is-invalid");
    feedback.textContent = "";
    feedback.style.color = "";

    if (pw === "" || pw_ck === "" || pw !== pw_ck) {
        pw_ckInput.classList.add("is-invalid");
        feedback.textContent = "비밀번호가 일치하지 않습니다.";
        feedback.style.color = "red";
        isPwdValid = false;
    } else {
        pw_ckInput.classList.add("is-valid");
        isPwdValid = true;
    }
}

// 이벤트 등록 (DOM 로드 이후 실행)
document.addEventListener("DOMContentLoaded", function () {
    const pwCkInput = document.querySelector("#user_pw_ck");
    const form = document.querySelector("#signupForm");

    if (pwCkInput) {
        pwCkInput.addEventListener("input", checkPwd);
    }

    if (form) {
        form.addEventListener("submit", function (event) {
            if (!validateForm()) {
                event.preventDefault();
            }
        });
    }
});

// 이메일 정규식 
function isValidEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

// 이메일 본인인증
function sendEmailCode() {
    const email = document.getElementById("user_email").value;
    
    if (!isValidEmail(email)) {
        alert("올바른 이메일 형식을 입력해주세요.");
        document.getElementById("user_email").focus();
        return;
    }
    
    fetch("/send-code", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "email=" + encodeURIComponent(email)
    }).then(res => res.text())
      .then(alert);
}

function verifyEmailCode() {
    const code = document.getElementById("email_code").value;
    fetch("/verify-code", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "code=" + encodeURIComponent(code)
    }).then(res => res.text())
      .then(result => {
          const div = document.getElementById("email_status");
          if (result === "success") {
              div.innerText = "인증 완료";
               isEmailVerified = true;
          } else {
              div.innerText = "인증 실패";
              isEmailVerified = false;
          }
      });
}
