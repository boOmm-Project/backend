package com.nuclear.boomm.product.repository.product;

import com.nuclear.boomm.product.domain.ExtraDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtraDescriptionRepository extends JpaRepository<ExtraDescription, Long> {
    Optional<ExtraDescription> findByExtraDescriptionId(Long extraDescriptionId);

    List<ExtraDescription> findAllByProductId(Long productId);

    Optional<ExtraDescription> findByExtraDescriptionIdAndFeedbackId(Long extraDescriptionId, Long feedbackId);
}
