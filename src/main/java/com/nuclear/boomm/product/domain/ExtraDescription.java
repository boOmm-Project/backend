package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.product.error.CustomException;
import com.nuclear.boomm.product.error.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtraDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extraDescriptionId;

    @Column(nullable = false)
    Long feedbackId;

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    String request;

    @Builder.Default
    @Column(nullable = false)
    String response = "추가 설명을 입력해 주세요.";

    @Builder.Default
    @Column(nullable = false)
    Boolean isResolved = false;

    @Column(nullable = false)
    Long creatorId;

    public void updateResponse(String description) {
        if (description == null || description.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }

        this.response = description;
    }

    public void updateIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }
}
