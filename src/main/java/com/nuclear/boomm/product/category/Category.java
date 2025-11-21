package com.nuclear.boomm.product.category;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.product.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

@Entity
@Getter
@Table(name = "category")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private String concept;
}
