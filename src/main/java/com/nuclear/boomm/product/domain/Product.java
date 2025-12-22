package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.dto.request.ProductRequest;
import com.nuclear.boomm.product.dto.response.ProductResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Builder.Default
    @Column(unique = true)
    private String productName = "임시 상품";

    @Builder.Default
    @Column
    private Long category = 1L;  // category 테이블의 id

    @Builder.Default
    @Column
    private String targetCustomer = "임시 고객";

    @Builder.Default
    @Column
    private int period = 12;     // 보장 기간

    @Builder.Default
    @Column
    private String salesChannel = "임시 판매 채널";

    @Column(nullable = false)
    private Long userId;    // User 테이블의 pk 참조. 해당 상품의 생성자

    @Builder.Default
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

    public void update(ProductRequest request) {
        this.productName = request.productName();
        this.category = request.category();
        this.targetCustomer = request.targetCustomer();
        this.period = request.period();
        this.salesChannel = request.salesChannel();
        this.userId = request.userId();
        this.isDone = request.isDone();
    }

    public void updateIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getCategory(),
                product.getTargetCustomer(),
                product.getPeriod(),
                product.getSalesChannel(),
                product.getUserId(),
                product.isDone,
                null
        );
    }
}