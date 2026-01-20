package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.contract.dto.request.ContractRequest;
import com.nuclear.boomm.contract.enums.ContractStatus;
import com.nuclear.boomm.contract.enums.ProcessingStatus;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DraftContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private Long productId;

    // 임시 저장용 필드(null 허용)

    @Enumerated(EnumType.STRING)
    private InsurancePurpose insurancePurpose;

    @Column(columnDefinition="TEXT")
    private String medicalHistory;

    private boolean recentHospitalization;

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPremium;

    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;

    public void updateDraftInfo(ContractRequest req) {
        this.productId = req.productId();
        this.insurancePurpose = req.insurancePurpose();
        this.medicalHistory = req.medicalHistory();
        this.recentHospitalization = req.recentHospitalization();
        this.startDate = req.startDate();
        this.totalPremium = req.totalPremium();
    }

    public void changeProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }
}
