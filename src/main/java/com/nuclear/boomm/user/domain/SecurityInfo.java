package com.nuclear.boomm.user.domain;

import com.nuclear.boomm.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean marketingAgreement;

    @Column(nullable = false)
    private String personalInfo;


    @Builder
    public SecurityInfo(Long userId, boolean marketingAgreement, String personalInfo) {
        this.userId = userId;
        this.marketingAgreement = marketingAgreement;
        this.personalInfo = personalInfo;

    }
}
