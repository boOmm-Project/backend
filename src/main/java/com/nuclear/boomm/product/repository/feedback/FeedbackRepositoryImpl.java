package com.nuclear.boomm.product.repository.feedback;

import com.nuclear.boomm.product.domain.Feedback;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.nuclear.boomm.product.domain.QFeedback.feedback;
import static com.nuclear.boomm.product.domain.QProduct.product;

@RequiredArgsConstructor
public class FeedbackRepositoryImpl implements FeedbackRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Feedback> searchAllFeedbackByUserIdWithProduct(Long userId) {
        return queryFactory
                .selectFrom(feedback)
                .join(feedback.product, product).fetchJoin()
                .where(product.userId.eq(userId))
                .fetch();
    }

    @Override
    public Optional<Feedback> findByFeedbackIdAndWriterId(Long feedbackId, Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(feedback)
                .join(feedback.product, product).fetchJoin()
                .where(
                        feedback.feedbackId.eq(feedbackId),
                        feedback.writerId.eq(userId)
                )
                .fetchOne()
        );
    }

    @Override
    public List<Feedback> findAllByProduct_ProductIdAndWriterId(Long productId, Long userId) {
        return queryFactory
                .selectFrom(feedback)
                .join(feedback.product, product).fetchJoin()
                .where(
                        feedback.product.productId.eq(productId),
                        feedback.writerId.eq(userId)
                )
                .fetch();
    }
}
