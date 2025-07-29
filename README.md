# 냉난방 사용량, 요금 조회 서비스 EcoProject

## **💡 프로젝트 개요**

**EcoProject**는 냉난방 사용량 및 요금 조회 기능을 제공하고, SNS 로그인 연동을 지원하는 웹 애플리케이션입니다. 
사용자들은 본 서비스를 통해 에너지 사용량을 효율적으로 관리하고, 편리하게 로그인하여 개인화된 서비스를 이용할 수 있습니다.

## **🛠️ 개발 환경 및 기술 스택**

* **개발 기간**: 2025년 5월 29일 \~ 2025년 6월 12일  
* **개발자**: 신혁주(팀장), 박정현, 팀원 
* **프레임워크**: Spring Framework  
* **IDE**: STS3 (Spring Tool Suite 3\)  
* **서버**: Apache Tomcat 9  
* **데이터베이스**: MySQL  
* **아키텍처**: MVC (Model-View-Controller) 패턴

## **📦 프로젝트 구조**

프로젝트는 MVC 패턴을 기반으로 효율적인 관리를 위해 다음과 같이 패키지를 구성했습니다.

```
src/main/java  
└── com.eco  
    ├── controller  
    ├── service  
    ├── domain  
    ├── exception  
    └── mapper
```

* **controller**: 사용자 요청을 처리하고 응답을 반환하는 컨트롤러 클래스들을 포함합니다.  
* **service**: 비즈니스 로직을 담당하며, 데이터베이스와의 상호작용을 mapper 계층에 위임합니다.  
* **domain**: 데이터베이스 테이블과 매핑되는 도메인 객체(VO/DTO)들을 정의합니다.  
* **exception**: 프로젝트 전반에서 발생할 수 있는 예외를 처리하기 위한 사용자 정의 예외 클래스들을 포함합니다.  
* **mapper**: 데이터베이스 CRUD 작업을 수행하는 MyBatis 매퍼 인터페이스 및 XML 파일들을 포함합니다.

## **✨ 주요 기능**

* **냉난방 사용량 및 요금 조회**: 사용자들이 월별 냉난방 에너지 사용량과 이에 따른 요금을 직관적으로 확인할 수 있도록 합니다.  
* **SNS 로그인 연동**: 네이버, 구글 소셜 계정을 통해 간편하게 로그인할 수 있는 기능을 제공하여 사용자 편의성을 높였습니다.

## **🚀 프로젝트 실행 방법**

1. **프로젝트 클론**:  
   ```
    git clone https://github.com/tmxose/EcoProject.git 
    ```

2. **데이터베이스 설정**:  
   * MySQL 데이터베이스를 생성하고, 필요한 테이블을 생성합니다. (스키마는 src/main/resources/sql/schema.sql 또는 관련 문서 추가예정)  
   * src/main/webapp/WEB-INF/spring/root-context.xml 파일에 데이터베이스 연결 정보를 설정합니다.  
3. **STS3에서 프로젝트 임포트**:  
   * STS3를 실행하고, File \-\> Import \-\> Maven \-\> Existing Maven Projects를 선택하여 프로젝트를 임포트합니다.  
4. **Maven 의존성 설치**:  
   * 프로젝트 우클릭 \-\> Maven \-\> Update Project... 를 통해 필요한 의존성을 다운로드합니다.  
5. **Tomcat 서버 설정**:  
   * Apache Tomcat 9 서버를 STS3에 연동하고, 프로젝트를 서버에 추가합니다.  
6. **프로젝트 실행**:  
   * Tomcat 서버를 시작하여 웹 애플리케이션을 실행합니다.
  

## **🚀 프로젝트 시연 영상**

https://github.com/user-attachments/assets/9dac6487-5632-417b-987f-63282089ccbc



