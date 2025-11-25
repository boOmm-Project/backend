package com.nuclear.boomm.product.product;

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

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private Long category;  // category 테이블의 id

    @Column(nullable = false)
    private String targetCustomer;

    @Column(nullable = false)
    private int period;     // 보장 기간

    @Column(nullable = false)
    private String salesChannel;

    @Builder
    public Product(String productName, Long category, String targetCustomer, int period, String salesChannel) {
        this.productName = productName;
        this.category = category;
        this.targetCustomer = targetCustomer;
        this.period = period;
        this.salesChannel = salesChannel;
    }
}