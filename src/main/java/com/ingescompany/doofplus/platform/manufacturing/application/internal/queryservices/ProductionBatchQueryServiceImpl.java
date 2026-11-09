package com.ingescompany.doofplus.platform.manufacturing.application.internal.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductionBatchQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllBatchesQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByNumberQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductionBatchRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProductionBatchQueryService that executes queries related to production batches.
 */
public class ProductionBatchQueryServiceImpl implements ProductionBatchQueryService {

    private final ProductionBatchRepository productionBatchRepository;

    public ProductionBatchQueryServiceImpl(ProductionBatchRepository productionBatchRepository) {
        this.productionBatchRepository = productionBatchRepository;
    }

    @Override
    public Optional<ProductionBatch> handle(GetBatchByIdQuery query) {
        return productionBatchRepository.findById(query.id());
    }

    @Override
    public Optional<ProductionBatch> handle(GetBatchByNumberQuery query) {
        return productionBatchRepository.findByBatchNumber(query.batchNumber());
    }

    @Override
    public List<ProductionBatch> handle(GetAllBatchesQuery query) {
        return productionBatchRepository.findAll();
    }
}
