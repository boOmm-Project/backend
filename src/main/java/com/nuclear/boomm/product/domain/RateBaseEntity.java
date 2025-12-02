package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;

@MappedSuperclass
public class RateBaseEntity extends BaseEntity {

    @Column(precision = 13, scale = 2)
    private BigDecimal basePremium;    // 기본 보험료

    @Column(precision = 6, scale = 2)
    private BigDecimal basicRate;      // 기본 요율
}
