package com.nuclear.boomm.contract.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.contract.enums.ContractStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "policy_status_change_history")
@Getter
@NoArgsConstructor
public class PolicyStatusChangeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String policyNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatus preStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatus changedStatus;

    @Column(nullable = false)
    private String changedBy;
}
