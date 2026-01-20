package com.nuclear.boomm.contract.repository;

import com.nuclear.boomm.contract.domain.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, String> {

}
