package com.nuclear.boomm.consultation.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.user.enums.ConsultStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consultation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;

    @Column(nullable = false)
    private Long managerId;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT")    //columnDefinition은 JPA를 사용하여 데이터베이스 테이블을 자동으로 생성(DDL생성)할때,
    private String consultMemo;                                 //  해당 컬럼의 데이터 타입을 직접 명시적으로 정의하기위해 사용했음.

    @Column(columnDefinition = "TEXT")
    private String complimentMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsultStatus consultStatus;

    @Builder
    public Consultation(
            Long userId,
            String consultMemo,
            Long managerId
    ) {
        this.userId = userId;
        this.consultMemo = consultMemo;
        this.managerId = managerId;
    }
    public void changeConsultStatus(ConsultStatus consultStatus) {
        this.consultStatus = consultStatus;
    }

    public void changeComplimentMessage(String complimentMessage) {
        this.complimentMessage = complimentMessage;
    }


}

