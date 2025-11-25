package com.nuclear.boomm.user.domain;

import com.nuclear.boomm.user.enums.ConsultStatus;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultationId")
    private Long consultationId;

    @Column(name = "manageId", nullable = false)
    private Long managerId;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "consultedAt", nullable = false)
    private LocalDateTime consultedAt;

    @Column(name = "consultMemo", columnDefinition = "TEXT")    //columnDfinition은 JPA를 사용하여 데이터베이스 테이블을 자동으로 생성(DDL생성)할때,
    private String consultMemo;                                 //  해당 컬럼의 데이터 타입을 직접 명시적으로 정의하기위해 사용했음.

    @Column(name = "complimentMessage", columnDefinition = "TEXT")
    private String complimentMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "consultStatus", nullable = false)
    private ConsultStatus consultStatus;

    @Builder
    public Consultation(Long managerId,
                        Long userId,
                        LocalDateTime consultedAt,
                        String consultMemo,
                        String complimentMessage,
                        ConsultStatus consultStatus) {
        this.managerId = managerId;
        this.userId = userId;
        this.consultedAt = consultedAt;
        this.consultMemo = consultMemo;
        this.complimentMessage = complimentMessage;
        this.consultStatus = consultStatus;
    }

}
