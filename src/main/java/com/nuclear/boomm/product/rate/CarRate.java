package com.nuclear.boomm.product.rate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "car_rate")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CarRate extends RateBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateId;

    private Long productId; // product 테이블의 pk 참조
    private double individualRate;
}
