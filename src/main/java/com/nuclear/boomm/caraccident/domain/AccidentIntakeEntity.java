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

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "car_accident_intake")
public class AccidentIntakeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String accidentDescription;

    @Column(columnDefinition = "text", nullable = false)
    private String damageDescription;

    @Column(nullable = false)
    private LocalDate incidentDate;

    @Column(nullable = false)
    private String carNumber;

    @Column(nullable = false)
    private Long insuredPersonId;

    @Column(nullable = false)
    private String insuranceClaimPersonName;

    @Column(nullable = false)
    private String insuranceClaimPersonEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankEnum insurancePaymentBank;

    @Column(nullable = false)
    private String insurancePaymentBankAccount;

    @Enumerated(EnumType.STRING)
    private InsuranceClaimStatus intakeStatus;

    @Column
    private Long intakeManagerId;

    @Builder
    public AccidentIntakeEntity(String accidentDescription, String damageDescription, LocalDate incidentDate,
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
