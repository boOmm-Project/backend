package com.nuclear.boomm.caraccident.repository;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccidentIntakeRepository extends JpaRepository<AccidentIntakeEntity, Long> {
    boolean existsByInsuredPersonIdAndIncidentDateBetween(Long userId, LocalDateTime incidentDateBefore, LocalDateTime incidentDateAfter);

    Optional<AccidentIntakeEntity> findByIdAndInsuredPersonIdAndInsuranceClaimPersonName(Long id, Long insuredPersonId, String insuranceClaimPersonName);

    List<AccidentIntakeEntity> findByInsuredPersonIdAndInsuranceClaimPersonName(Long userId, String username);
}
