package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.FeedbackStatus;
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
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackStatus status;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long productId;     // product 테이블 pk 참조

    @Column(nullable = false)
    private Long writerId;      // user 테이블 pk 참조. 피드백을 작성한 사람

    @Column(nullable = false)
    private String role;

    @Builder
    public Feedback(FeedbackStatus status, String description, Long productId, Long writerId,  String role) {
        this.status = status;
        this.description = description;
        this.productId = productId;
        this.writerId = writerId;
        this.role = role;
    }
}
