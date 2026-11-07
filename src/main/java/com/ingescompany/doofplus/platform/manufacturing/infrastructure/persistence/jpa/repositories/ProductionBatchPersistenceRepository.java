package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.ProductionBatchPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring data repository for managing ProductionBatch entities in the database.
 */
@Repository
public interface ProductionBatchPersistenceRepository extends
        JpaRepository<ProductionBatchPersistenceEntity, Long> {

    Optional<ProductionBatchPersistenceEntity> findByBatchNumber(String batchNumber);

    boolean existsByBatchNumber(String batchNumber);
}
