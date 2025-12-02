package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    private Long id;    // prototype 테이블의 id 참조

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Long categoryId;  // category 테이블의 id

    @Column(nullable = false)
    private int period;     // 보장 기간 (개월 수)

    @Builder
    public Product(String title, Long categoryId, int period) {
        this.title = title;
        this.categoryId = categoryId;
        this.period = period;
    }
}