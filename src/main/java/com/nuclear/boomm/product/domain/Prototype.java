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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prototype {

    // product_file, risk_report, feedback, coverage, report, sysytem_and_regulation_prep에서 prototype의 id를 fk로 보유

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Long categoryId;        // category 테이블의 id

    @Column(nullable = false)
    private String targetCustomer;  // 타겟 고객층

    @Column(nullable = false)
    private int period;             // 보장 기간 (개월 수)

    @Column(nullable = false)
    private String salesChannel;    // 판매 채널

    @Column
    private boolean isReleased = false;     // 출시 된 상품인지 아닌지 확인

    @Builder
    public Prototype(String title, Long categoryId, String targetCustomer, int period, String salesChannel, boolean isReleased) {
        this.title = title;
        this.categoryId = categoryId;
        this.targetCustomer = targetCustomer;
        this.period = period;
        this.salesChannel = salesChannel;
        this.isReleased = isReleased;
    }
}
