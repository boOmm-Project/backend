package com.nuclear.boomm.caraccident.repository;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentIntakeRepository extends JpaRepository<AccidentIntakeEntity, Long> {
    Object findByInsuredPersonId(Long insuredPersonId);
}
