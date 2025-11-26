package com.nuclear.boomm.complaintsmanager;

import com.nuclear.boomm.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComplaintsManager extends BaseEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long consultationId;

    @Column(nullable = false)
    private Long managerId;

    @Column(nullable = false)
    private String consultationMemo;


    @Builder
    public ComplaintsManager(Long consultationId, Long managerId, String consultationMemo) {
        this.consultationId = consultationId;
        this.managerId = managerId;
        this.consultationMemo = consultationMemo;
    }

}
