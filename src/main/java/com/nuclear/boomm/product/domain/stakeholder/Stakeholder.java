package com.nuclear.boomm.product.domain.stakeholder;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stakeholder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stakeholder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stakeholderId;

    @Column(nullable = false)
    private Long userId;        // user 테이블 pk 참조

    @Column(nullable = false)
    private Long productId;     // product 테이블 pk 참조

    @Builder
    public Stakeholder(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
