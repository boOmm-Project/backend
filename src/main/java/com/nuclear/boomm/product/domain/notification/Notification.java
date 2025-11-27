package com.nuclear.boomm.product.domain.notification;

import com.nuclear.boomm.common.BaseEntity;
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

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private Long senderId;      // user 테이블 pk 참조

    @Column(nullable = false)
    private Long recipientId;   // user 테이블 pk 참조

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime sendDate;

    @Column(nullable = false)
    private LocalDateTime receiveDate;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isConfirmed;

    @Builder
    public Notification(Long senderId, Long recipientId, String description, LocalDateTime sendDate, LocalDateTime receiveDate, boolean isConfirmed) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.description = description;
        this.sendDate = sendDate;
        this.receiveDate = receiveDate;
        this.isConfirmed = isConfirmed;
    }
}
