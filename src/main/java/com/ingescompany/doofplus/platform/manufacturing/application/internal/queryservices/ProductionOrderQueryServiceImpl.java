package com.ingescompany.doofplus.platform.manufacturing.application.internal.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductionOrderQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllProductionOrdersQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetProductionOrderByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductionOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Query service of ProductionOrder.
 */
@Service
public class ProductionOrderQueryServiceImpl implements ProductionOrderQueryService {
    private final ProductionOrderRepository productionOrderRepository;

    public ProductionOrderQueryServiceImpl(ProductionOrderRepository productionOrderRepository) {
        this.productionOrderRepository = productionOrderRepository;
    }

    @Override
    public List<ProductionOrder> handle(GetAllProductionOrdersQuery query) {
        return productionOrderRepository.findAll();
    }

    @Override
    public Optional<ProductionOrder> handle(GetProductionOrderByIdQuery query) {
        return productionOrderRepository.findById(query.productionOrderId());
    }
}
