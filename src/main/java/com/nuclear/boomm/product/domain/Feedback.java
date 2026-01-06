package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "feedback")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackStatus status = FeedbackStatus.STAKEHOLDER_FEEDBACK_PENDING;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description = "피드백 사항을 작성해 주세요.";

    @Column(nullable = false)
    private Long writerId;    // user 테이블 pk 참조. 피드백을 작성한 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void updateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new CustomException(ErrorCode.FEEDBACK_NOT_FOUND);
        }

        this.description = description;
    }

    public void updateStatus(FeedbackStatus feedbackStatus) {
        if (feedbackStatus == null) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }

        this.status = feedbackStatus;
    }

//    @Column(nullable = false)
//    private Role role;  // 나중에 상준님이 Role enum 추가하시면 변경해야함
}
