package com.nuclear.boomm.caraccident.repository;

import com.nuclear.boomm.caraccident.domain.AccidentFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccidentFileRepository extends JpaRepository<AccidentFileEntity,Long> {
    List<AccidentFileEntity> findAllByUserIdAndAccidentIntakeId(Long userId, Long intakeId);
}
