package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    void deleteAllByProductId(Long productId);
}
