package com.nuclear.boomm.user.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.user.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    //  네이버가 무조건 주는 필수 정보
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    // 소셜 로그인 필수 식별자
    @Column(nullable = false)
    private String provider;   //  kakao,goole 추후에 추가할 예정

    @Column(nullable = false)
    private String providerId; // 네이버 고유 식별값

    @Column(nullable = false)
    private String role;

    // 네이버가 줄 수도 있고 안 줄 수도 있는 정보 (Nullable = true 처리)
    // 네이버는 출생연도(YYYY)와 생일(MM-DD)을 따로 줍니다.
    // 일단 String으로 저장하거나, 서비스 로직에서 합쳐서 LocalDate로 변환해야 합니다.
    @Column(nullable = true)
    private String birthYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    // 네이버가 절대 안 주는 정보 (추후 입력받아야 함 -> Nullable 필수)
    @Column(nullable = true)
    private String idNum;  // 절대 안 줌

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String zipCode;

    @Column(nullable = true)
    private String job;

    @Column(nullable = true)
    private int age; // 연령대 [ 네이버는 "20-29" 같은 범위를 문자열로 준다. int로 바로 못 받음 ]

    // 기타 선택 약관 정보
    private boolean wedding;
    private String companyName;
    private boolean marketingAgreement;
    private String personalInfo;

    @Builder
    public User(String name, String email, String phone,
                String provider, String providerId, String role,
                Gender gender, String birthYear) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.gender = gender;
        this.birthYear = birthYear;
    }

    // 추가 정보 입력 메서드
    public void updateAdditionalInfo(String address, String zipCode, String job, String idNum) {
        this.address = address;
        this.zipCode = zipCode;
        this.job = job;
        this.idNum = idNum;
    }
}




//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class User extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @Column(nullable = false)
//    private int age;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String idNum;
//
//    @Column(nullable = false)
//    private String address;
//
//    @Column(nullable = false)
//    private String zipCode;
//
//    @Column(nullable = false)
//    private String job;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Gender gender;
//
//    @Column(nullable = false)
//    private String phone;
//
//    @Column(nullable = false)
//    private String email;
//
//    private boolean wedding;
//
//    private String companyName;
//
//    @Column(nullable = false)
//    private boolean marketingAgreement;
//
//    @Column(nullable = false)
//    private String personalInfo;
//
//
//    @Builder
//    public User(    int age, String name
//            , String idNum, String address
//            , String zipCode, String job
//            , Gender gender
//            , String phone, String email)
//    {
//        this.age = age;
//        this.name = name;
//        this.idNum = idNum;
//        this.address = address;
//        this.zipCode = zipCode;
//        this.job = job;
//        this.gender = gender;
//        this.phone = phone;
//        this.email = email;
//    }
//
//    public void updateWedding(boolean wedding) {
//        this.wedding = wedding;
//    }
//    public void updateCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//    public void changePersonalInfo(String personalInfo) {
//        this.personalInfo = personalInfo;
//    }
//    public void changeMarketingAgreement(boolean marketingAgreement) {
//        this.marketingAgreement = marketingAgreement;
//
//
//    }
//}

