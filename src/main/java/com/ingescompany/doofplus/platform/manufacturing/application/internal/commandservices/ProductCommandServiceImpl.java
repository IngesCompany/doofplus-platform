package com.ingescompany.doofplus.platform.manufacturing.application.internal.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductCommandService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateProductCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateProductCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command service of products: the code is unique.
 */
@Service
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductRepository productRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Result<Product, ApplicationError> handle(CreateProductCommand command) {
        if (productRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("Product", "manufacturing.error.product.code.duplicate"));
        }
        return Result.success(productRepository.save(new Product(command)));
    }

    @Override
    @Transactional
    public Result<Product, ApplicationError> handle(UpdateProductCommand command) {
        var product = productRepository.findById(command.productId());
        if (product.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Product", command.productId().toString()));
        }
        product.get().update(command);
        return Result.success(productRepository.save(product.get()));
    }
}
