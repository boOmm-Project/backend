package com.nuclear.boomm.product.repository.feedback;

import com.nuclear.boomm.product.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, FeedbackRepositoryCustom {
    void deleteAllByProduct_ProductId(Long productId);

    Optional<Feedback> findByFeedbackIdAndWriterId(Long feedbackId, Long userId);

    List<Feedback> findAllByWriterId(Long userId);

    Optional<Feedback> findByProduct_ProductId(Long productId);

    boolean existsByFeedbackId(Long feedbackId);

    boolean existsByFeedbackIdAndWriterId(Long feedbackId, Long userId);

    Optional<Feedback> findByFeedbackIdAndProduct_ProductId(Long feedbackId, Long productId);

    boolean existsByProduct_ProductIdAndWriterId(Long productId, Long userId);

    Optional<Feedback> findByProduct_ProductIdAndWriterId(Long productId, Long userId);
}
