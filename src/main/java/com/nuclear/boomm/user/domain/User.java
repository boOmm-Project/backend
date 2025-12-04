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

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String idNum;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    private boolean wedding;

    private String companyName;

    @Column(nullable = false)
    private boolean marketingAgreement;

    @Column(nullable = false)
    private String personalInfo;


    @Builder
    public User(    int age, String name
            , String idNum, String address
            , String zipCode, String job
            , Gender gender
            , String phone, String email)
    {
        this.age = age;
        this.name = name;
        this.idNum = idNum;
        this.address = address;
        this.zipCode = zipCode;
        this.job = job;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }

    public void updateWedding(boolean wedding) {
        this.wedding = wedding;
    }
    public void updateCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void changeMarketingAgreement(boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
    }

    public void changePersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }
}

