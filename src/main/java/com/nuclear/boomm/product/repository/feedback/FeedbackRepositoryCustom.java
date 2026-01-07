package com.nuclear.boomm.product.repository.feedback;

import com.nuclear.boomm.product.domain.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepositoryCustom {
    List<Feedback> searchAllFeedbackByUserIdWithProduct(Long userId);
    Optional<Feedback> findByFeedbackIdAndWriterId(Long feedbackId, Long userId);
}
