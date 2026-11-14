package com.ingescompany.doofplus.platform.manufacturing.application.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllProductsQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query operations of Product.
 */
public interface ProductQueryService {
    List<Product> handle(GetAllProductsQuery query);
    Optional<Product> handle(GetProductByIdQuery query);
}
