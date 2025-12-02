package com.nuclear.boomm.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarRate extends RateBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId; // product 테이블의 pk 참조

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal individualRate;

    @Builder
    public CarRate(Long productId, BigDecimal individualRate) {
        this.productId = productId;
        this.individualRate = individualRate;
    }
}
