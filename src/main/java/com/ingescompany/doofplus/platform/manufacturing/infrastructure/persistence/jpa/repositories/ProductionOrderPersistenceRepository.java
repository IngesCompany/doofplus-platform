package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.ProductionOrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of ProductionOrderPersistenceEntity.
 */
@Repository
public interface ProductionOrderPersistenceRepository extends JpaRepository<ProductionOrderPersistenceEntity, Long> {
    boolean existsByCode(String code);
}
