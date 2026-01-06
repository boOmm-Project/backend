package com.nuclear.boomm.product.repository.feedback;

import com.nuclear.boomm.product.domain.Feedback;

import java.util.List;

public interface FeedbackRepositoryCustom {
    List<Feedback> searchAllFeedbackByUserIdWithProduct(Long userId);
}
