package com.nuclear.boomm.product.repository;

import com.nuclear.boomm.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
