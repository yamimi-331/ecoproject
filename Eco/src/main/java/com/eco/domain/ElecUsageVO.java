package com.eco.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ElecUsageVO {
	private int usage_cd;
	private int user_cd;
	private int elec_cd;
	private float elec_usage;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date elec_time;
	private char use_yn;
}
