package com.nuclear.boomm.product.rate;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class RateBaseEntity extends BaseEntity {

    private double basePremium;    // 기본 보험료
    private double basicRate;      // 기본 요율
}
