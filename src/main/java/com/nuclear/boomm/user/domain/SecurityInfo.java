package com.nuclear.boomm.user.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="securityInfo")
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
    public SecurityInfo(Long userId, boolean marketingAgreement, String address, String zipCode) {
        this.userId = userId;
        this.marketingAgreement = marketingAgreement;
        this.personalInfo = address;
    }
}
