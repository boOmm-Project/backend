package com.nuclear.boomm.product.product;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "product")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;

    private String productName;
    private Long category;  // Product_Category 테이블의 id
    private String targetCustomer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String salesChannel;
}