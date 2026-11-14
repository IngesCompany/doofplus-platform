package com.ingescompany.doofplus.platform.manufacturing.domain.repositories;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repository of products.
 */
public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    boolean existsByCode(String code);
}
