package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.CreateProductionBatchResource;

public class CreateProductionBatchCommandFromResourceAssembler {
    public static CreateBatchCommand toCommandFromResource(CreateProductionBatchResource resource) {
        return new CreateBatchCommand(
                new BatchNumber(resource.batchNumber()),
                resource.productionOrderId(),
                new Quantity(resource.plannedQuantityValue(), resource.plannedQuantityUnit())
        );
    }
}
