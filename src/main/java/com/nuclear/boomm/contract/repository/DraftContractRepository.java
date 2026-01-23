package com.nuclear.boomm.contract.repository;

import com.nuclear.boomm.contract.domain.DraftContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DraftContractRepository extends JpaRepository<DraftContract, Long> {

    Optional<DraftContract> findTopByUserIdOrderByIdDesc(Long userId);
}
