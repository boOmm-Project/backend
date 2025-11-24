package com.nuclear.boomm.user.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="security_info")
@NoArgsConstructor
public class SecurityInfo extends BaseEntity {

    @Id
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean marketingAgreement;

    @Column(nullable = false)
    private String personalInfo;


}
