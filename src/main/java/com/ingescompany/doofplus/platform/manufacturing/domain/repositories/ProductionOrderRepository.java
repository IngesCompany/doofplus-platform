package com.ingescompany.doofplus.platform.manufacturing.domain.repositories;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;

import java.util.List;
import java.util.Optional;

/**
 * Repository of production orders.
 */
public interface ProductionOrderRepository {
    ProductionOrder save(ProductionOrder productionOrder);
    Optional<ProductionOrder> findById(Long id);
    List<ProductionOrder> findAll();
    boolean existsByCode(String code);
}
