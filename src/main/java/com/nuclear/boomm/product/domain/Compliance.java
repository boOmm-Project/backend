package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Compliance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long complianceId;        // user 테이블 pk 참조

    @Column(nullable = false)
    private Long productId;           // product 테이블 pk 참조

    @Builder
    public Compliance(Long complianceId, Long productId) {
        this.complianceId = complianceId;
        this.productId = productId;
    }
}
