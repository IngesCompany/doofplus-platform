package com.ingescompany.doofplus.platform.manufacturing.domain.repositories;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;

import java.util.List;
import java.util.Optional;

/**
 * Manufacturing Production Batch Repository port.
 */
public interface ProductionBatchRepository {

    ProductionBatch save(ProductionBatch productionBatch);

    Optional<ProductionBatch> findById(Long id);

    Optional<ProductionBatch> findByBatchNumber(BatchNumber batchNumber);

    List<ProductionBatch> findAll();

    boolean existsByBatchNumber(BatchNumber batchNumber);
}
