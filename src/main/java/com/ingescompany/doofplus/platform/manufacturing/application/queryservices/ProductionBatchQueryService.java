package com.ingescompany.doofplus.platform.manufacturing.application.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllBatchesQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByNumberQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for querying production batch query operations.
 */
public interface ProductionBatchQueryService {
    Optional<ProductionBatch> handle(GetBatchByIdQuery query);
    Optional<ProductionBatch> handle(GetBatchByNumberQuery query);
    List<ProductionBatch> handle(GetAllBatchesQuery query);
}
