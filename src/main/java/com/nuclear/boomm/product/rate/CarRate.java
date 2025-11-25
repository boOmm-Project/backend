package com.nuclear.boomm.product.rate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "car_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarRate extends RateBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateId;

    @Column(nullable = false)
    private Long productId; // product 테이블의 pk 참조

    @Column(nullable = false)
    private double individualRate;

    @Builder
    public CarRate(Long productId, double individualRate) {
        this.productId = productId;
        this.individualRate = individualRate;
    }
}
