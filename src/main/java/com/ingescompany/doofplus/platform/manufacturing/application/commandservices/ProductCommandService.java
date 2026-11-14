package com.ingescompany.doofplus.platform.manufacturing.application.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateProductCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateProductCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command operations of products.
 */
public interface ProductCommandService {
    Result<Product, ApplicationError> handle(CreateProductCommand command);
    Result<Product, ApplicationError> handle(UpdateProductCommand command);
}
