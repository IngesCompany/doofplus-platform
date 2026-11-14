package com.ingescompany.doofplus.platform.manufacturing.application.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ChangeProductionOrderStatusCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateProductionOrderCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateOperationCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command operations of production orders.
 */
public interface ProductionOrderCommandService {
    Result<ProductionOrder, ApplicationError> handle(CreateProductionOrderCommand command);
    Result<ProductionOrder, ApplicationError> handle(ChangeProductionOrderStatusCommand command);
    Result<ProductionOrder, ApplicationError> handle(UpdateOperationCommand command);
}
