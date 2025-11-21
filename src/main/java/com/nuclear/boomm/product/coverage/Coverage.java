package com.nuclear.boomm.product.coverage;

import com.nuclear.boomm.product.enums.CoverageCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "coverage")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverageId;

    @Enumerated(EnumType.STRING)
    private CoverageCategory category;

    private Long productId;         // product 테이블의 productId 참조
    private String description;
    private double minCoverageLimit;
    private double maxCoverageLimit;
    private boolean isMandatory;
    private String damageCalStandard;
}
