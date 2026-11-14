package com.ingescompany.doofplus.platform.manufacturing.application.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllProductionOrdersQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetProductionOrderByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query operations of ProductionOrder.
 */
public interface ProductionOrderQueryService {
    List<ProductionOrder> handle(GetAllProductionOrdersQuery query);
    Optional<ProductionOrder> handle(GetProductionOrderByIdQuery query);
}
