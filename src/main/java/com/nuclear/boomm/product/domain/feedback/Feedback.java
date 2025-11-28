package com.nuclear.boomm.product.domain.feedback;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.FeedbackStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackStatus status;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long productId; // product 테이블 pk 참조

    @Column(nullable = false)
    private Long writerId;    // user 테이블 pk 참조. 피드백을 작성한 사람

//    @Column(nullable = false)
//    private Role role;  // 나중에 상준님이 Role enum 추가하시면 변경해야함

    @Builder
    public Feedback(FeedbackStatus status, String description, Long productId, Long writerId) {
        this.status = status;
        this.description = description;
        this.productId = productId;
        this.writerId = writerId;
    }
}
