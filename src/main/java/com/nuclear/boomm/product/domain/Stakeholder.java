package com.nuclear.boomm.product.domain;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stakeholder extends BaseEntity {

    @Id
    private Long stakeholderId; // user 테이블 pk 참조

    @Column(nullable = false)
    private Long productId;     // product 테이블 pk 참조

    public Stakeholder(Long stakeholderId, Long productId) {
        this.stakeholderId = stakeholderId;
        this.productId = productId;
    }
}
