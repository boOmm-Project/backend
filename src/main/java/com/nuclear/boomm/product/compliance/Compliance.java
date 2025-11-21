package com.nuclear.boomm.product.compliance;

import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "compliance")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Compliance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stakeholderId;

    private Long userId;        // user 테이블 pk 참조
    private Long productId;     // product 테이블 pk 참조
    private Long feedbackId;    // feedback 테이블 pk 참조
}
