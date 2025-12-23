package com.nuclear.boomm.caraccident.repository;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AccidentIntakeRepository extends JpaRepository<AccidentIntakeEntity, Long> {
    boolean existsByUserIdAndIncidentDateBetween(Long userId, LocalDateTime incidentDateBefore, LocalDateTime incidentDateAfter);
}
