package com.eco.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserTypeChargeDTO {    
    private int userCd;
    private String userNm;

    private String energyType; // "전기" or "가스"
    private String usageType;  // "주택용", "산업용"
    private float usageAmount;
    private int unitCharge;
    private float totalCharge;
 
    //당월 에너지 사용량 합계
    private float gasUsageAmount;
    private float elecUsageAmount;
    //지역별 에너지 총량 위한 변수
    private String user_local;
    
    //상세 데이터 가져오기 위한 변수
    private float gas_usage;
    private Date gas_time;
    private float elec_usage;
    private Date elec_time;
    
    //월별 데이터 가져오기 위한 변수
    private int year;
    private int month;
    //월별 요금
    private float gasCharge;
    private float elecCharge;
    
}
