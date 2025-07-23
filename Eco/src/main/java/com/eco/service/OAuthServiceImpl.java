package com.eco.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eco.domain.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
	// Google OAuth2 Info
	@Value("${google.client.id}")
	private String GOOGLE_CLIENT_ID;

	@Value("${google.client.secret}")
	private String GOOGLE_CLIENT_SECRET;

	@Value("${google.redirect.uri}")
	private String GOOGLE_REDIRECT_URI;

	// Naver OAuth2 Info
	@Value("${naver.client.id}")
	private String NAVER_CLIENT_ID;
	@Value("${naver.client.secret}")
	private String NAVER_CLIENT_SECRET;
	@Value("${naver.redirect.uri}")
	private String NAVER_REDIRECT_URI;

	private final UserService userService; // UserVO 관련 서비스

	// 구글 로그인 URL
	@Override
	public String getGoogleLoginUrl() {
		return "https://accounts.google.com/o/oauth2/v2/auth" + "?scope=email%20profile" + "&access_type=offline"
				+ "&include_granted_scopes=true" + "&response_type=code" + "&client_id=" + GOOGLE_CLIENT_ID
				+ "&redirect_uri=" + GOOGLE_REDIRECT_URI + "&prompt=select_account";
	}

	@Override
	public UserVO processGoogleLogin(String code) {
		try {
			// 1. Access Token 요청
			URL url = new URL("https://oauth2.googleapis.com/token");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			String data = "code=" + code + "&client_id=" + GOOGLE_CLIENT_ID + "&client_secret=" + GOOGLE_CLIENT_SECRET
					+ "&redirect_uri=" + GOOGLE_REDIRECT_URI + "&grant_type=authorization_code";

			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			br.close();

			JSONObject json = new JSONObject(sb.toString());
			String accessToken = json.getString("access_token");

			// 2. 사용자 정보 요청
			URL userInfoUrl = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken);
			HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
			BufferedReader userReader = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
			StringBuilder userSb = new StringBuilder();
			String userLine;
			while ((userLine = userReader.readLine()) != null)
				userSb.append(userLine);
			userReader.close();

			JSONObject userInfo = new JSONObject(userSb.toString());
			String email = userInfo.getString("email");
			String name = userInfo.getString("name");

			// 3. 사용자 DB 처리
			String type = "G";
			UserVO user = userService.findByUserId(email, type);
			if (user == null) {
				user = new UserVO();
				user.setUser_id(email);
				user.setUser_pw("");
				user.setUser_nm(name);
				user.setUse_yn('Y');
				user.setUser_type("G");
				user.setUser_local("서울");
				user.setUser_email(email);
				userService.signup(user);
			}

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getNaverLoginUrl() {
		String state = UUID.randomUUID().toString();
		try {
			return "https://nid.naver.com/oauth2.0/authorize?response_type=code" + "&client_id=" + NAVER_CLIENT_ID
					+ "&redirect_uri=" + URLEncoder.encode(NAVER_REDIRECT_URI, "UTF-8") + "&state=" + state
					+ "&prompt=login";
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		}
	}

	@Override
	public UserVO processNaverLogin(String code, String state) {
	    // TODO: 실제 서비스에서는 전달받은 state 값을 세션 등에 저장된 값과 비교해 검증해야 함

	    try {
	        // 1. Access Token 요청
	        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
	                + "&client_id=" + NAVER_CLIENT_ID
	                + "&client_secret=" + NAVER_CLIENT_SECRET
	                + "&code=" + code
	                + "&state=" + state
	                + "&redirect_uri=" + URLEncoder.encode(NAVER_REDIRECT_URI, "UTF-8");

	        URL url = new URL(tokenUrl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        br.close();

	        JSONObject json = new JSONObject(sb.toString());
	        String accessToken = json.getString("access_token");

	        // 2. 사용자 정보 조회
	        URL userInfoUrl = new URL("https://openapi.naver.com/v1/nid/me");
	        HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
	        userConn.setRequestMethod("GET");
	        userConn.setRequestProperty("Authorization", "Bearer " + accessToken);

	        BufferedReader userBr = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
	        StringBuilder userSb = new StringBuilder();
	        String userLine;
	        while ((userLine = userBr.readLine()) != null) {
	            userSb.append(userLine);
	        }
	        userBr.close();

	        JSONObject userInfoJson = new JSONObject(userSb.toString());
	        JSONObject responseJson = userInfoJson.getJSONObject("response");

	        String email = responseJson.getString("email");
	        String name = responseJson.getString("name");

	        String type = "N";

	        UserVO user = userService.findByUserId(email, type);
	        if (user == null) {
	            user = new UserVO();
	            user.setUser_id(email);
	            user.setUser_pw("");
	            user.setUser_nm(name);
	            user.setUse_yn('Y');
	            user.setUser_type(type);
	            user.setUser_local("서울");
	            user.setUser_email(email);
	            userService.signup(user);
	        }

	        return user;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
