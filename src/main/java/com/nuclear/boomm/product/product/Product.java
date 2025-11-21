package com.nuclear.boomm.product.product;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private Long category;                    // Product_Category 테이블의 id
    private String targetCustomer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String salesChannel;
    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;
}