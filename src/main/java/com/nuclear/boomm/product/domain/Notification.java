package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long senderId;      // user 테이블 pk 참조

    @Column(nullable = false)
    private Long recipientId;   // user 테이블 pk 참조

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;

    @Builder
    public Notification(Long senderId, Long recipientId, String description) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.description = description;
    }

    public boolean getIsConfirmed() {
        return isConfirmed;
    }
}
