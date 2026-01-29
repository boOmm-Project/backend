package com.nuclear.boomm.underwriting.repository;

import com.nuclear.boomm.underwriting.domain.UnderWriting;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnderWritingRepository extends JpaRepository<UnderWriting, Long> {
    List<UnderWriting> findAllByStatus(UnderWritingStatus status);
}
