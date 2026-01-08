package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.dto.request.product.ProductRequest;
import com.nuclear.boomm.product.dto.response.product.ProductResponse;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.common.error.ErrorCode;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Builder.Default
    @Column(nullable = false, unique = true)
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

    @Builder.Default
    @Column(nullable = false)
    private boolean isReleased = false;

    public void update(ProductRequest request) {
        if (this.isReleased) {
            throw new CustomException(ErrorCode.PRODUCT_IS_RELEASED);
        }
        if (request.isReleased()) {
            throw new CustomException(ErrorCode.PRODUCT_IS_RELEASED);
        }

        this.productName = request.productName();
        this.category = request.category();
        this.targetCustomer = request.targetCustomer();
        this.period = request.period();
        this.salesChannel = request.salesChannel();
    }

    public void updateIsDone(boolean isDone) {
        if (isReleased) {
            throw new CustomException(ErrorCode.PRODUCT_IS_RELEASED);
        }

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
                product.isDone(),
                product.isReleased()
        );
    }
}