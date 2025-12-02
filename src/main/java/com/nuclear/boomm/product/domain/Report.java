package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.product.enums.ReportCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportCategory category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;    // 신고 반영 여부

    @Column(nullable = false)
    private Long productId;

    @Builder
    public Report(ReportCategory category, String title, String description, boolean isConfirmed, Long productId) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.isConfirmed = isConfirmed;
        this.productId = productId;
    }
}