package com.nuclear.boomm.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtraDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extraDescriptionId;

    @Column(nullable = false)
    Long feedbackId;

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    String request;

    @Column(nullable = false)
    String response;

    @Builder.Default
    @Column(nullable = false)
    Boolean isResolved = false;

    @Column(nullable = false)
    Long constructor;
}
