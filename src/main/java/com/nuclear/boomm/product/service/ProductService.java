package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.Product;
import com.nuclear.boomm.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long createProduct(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Long userId = Long.parseLong(username);

        Product product = Product.builder()
                .userId(userId)
                .build();

        return productRepository.save(product).getProductId();
    }
}
