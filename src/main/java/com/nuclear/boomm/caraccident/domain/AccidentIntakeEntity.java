package com.nuclear.boomm.caraccident.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.common.enums.BankEnum;
import com.nuclear.boomm.common.enums.InsuranceClaimStatus;
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

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "car_accident_intake")
public class AccidentIntakeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String accidentDescription; // 사고 사항

    @Column(columnDefinition = "TEXT", nullable = false)
    private String damageDescription; // 피해 사항

    @Column(nullable = false)
    private LocalDateTime incidentDate; // 사고 일자

    @Column(nullable = false)
    private String carNumber; // 차량번호

    @Column(nullable = false)
    private Long insuredPersonId; // 피보험자 아이디

    @Column(nullable = false)
    private String insuranceClaimPersonName; // 보험금청구 접수자

    @Column(nullable = false)
    private String insuranceClaimPersonEmail; // 보험금청구 접수자 이메일

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankEnum insurancePaymentBank; // 보험금 지금 계좌 은행

    @Column(nullable = false)
    private String insurancePaymentBankAccount; // 보험금 지급 계좌

    @Enumerated(EnumType.STRING)
    private InsuranceClaimStatus intakeStatus; // 진행 상태

    @Column
    private Long intakeManagerId; // 접수 담당자 아이디

    @Builder
    public AccidentIntakeEntity(String accidentDescription, String damageDescription, LocalDateTime incidentDate,
                                String carNumber, Long insuredPersonId, String insuranceClaimPersonName,
                                String insuranceClaimPersonEmail, BankEnum insurancePaymentBank, String insurancePaymentBankAccount
                                ) {
        this.accidentDescription = accidentDescription;
        this.damageDescription = damageDescription;
        this.incidentDate = incidentDate;
        this.carNumber = carNumber;
        this.insuredPersonId = insuredPersonId;
        this.insuranceClaimPersonName = insuranceClaimPersonName;
        this.insuranceClaimPersonEmail = insuranceClaimPersonEmail;
        this.insurancePaymentBank = insurancePaymentBank;
        this.insurancePaymentBankAccount = insurancePaymentBankAccount;
    }

    public AccidentIntakeEntity updateStatus(InsuranceClaimStatus status) {
        this.intakeStatus = status;
        return this;
    }

    public AccidentIntakeEntity assignIntakeManager(Long intakeManagerId) {
        this.intakeManagerId = intakeManagerId;
        return this;
    }


}
