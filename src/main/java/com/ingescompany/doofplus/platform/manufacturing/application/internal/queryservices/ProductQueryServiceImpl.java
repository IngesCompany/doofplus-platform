package com.ingescompany.doofplus.platform.manufacturing.application.internal.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllProductsQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetProductByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Query service of Product.
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.productId());
    }
}
