package com.nuclear.boomm.underwriting.repository;

import com.nuclear.boomm.underwriting.domain.UnderWriting;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnderWritingRepository extends JpaRepository<UnderWriting, Long> {
    //목록 조회
    Page<UnderWriting> findAllByStatus(UnderWritingStatus status, Pageable pageable);

    // 개수 조회
    long countByStatus(UnderWritingStatus status);
}
