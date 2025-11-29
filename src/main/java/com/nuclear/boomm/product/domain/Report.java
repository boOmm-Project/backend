package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.product.enums.ReportCategory;
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
@Table(name = "report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private ReportCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;    // 신고 반영 여부

    @Builder
    public Report(ReportCategory category, String name, String description, boolean isConfirmed) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.isConfirmed = isConfirmed;
    }
}