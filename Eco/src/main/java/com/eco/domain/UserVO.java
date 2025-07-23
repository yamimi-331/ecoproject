package com.eco.domain;

import lombok.Data;

@Data
public class UserVO {
	private int user_cd;	
	private String user_id;
	private String user_pw;
	private String user_nm;
	private char use_yn;
	private String user_type;
	private String user_local;
	private char admin_yn;
	private String user_email;
}
