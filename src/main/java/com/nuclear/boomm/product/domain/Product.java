package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
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
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true)
    private String productName = "임시 상품";

    @Column
    private Long category = 1L;  // category 테이블의 id

    @Column
    private String targetCustomer = "임시 고객";

    @Column
    private int period = 12;     // 보장 기간

    @Column
    private String salesChannel = "임시 판매 채널";

    @Column(nullable = false)
    private Long userId;    // User 테이블의 pk 참조. 해당 상품의 생성자

    @Column(nullable = false)
    private boolean isDone = false;

    @Builder
    public Product(String productName, Long category, String targetCustomer, int period, String salesChannel, Long userId, boolean isDone) {
        this.productName = productName;
        this.category = category;
        this.targetCustomer = targetCustomer;
        this.period = period;
        this.salesChannel = salesChannel;
        this.userId = userId;
        this.isDone = isDone;
    }
}