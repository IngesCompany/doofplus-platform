package com.ingescompany.doofplus.platform.manufacturing.application.internal.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductionOrderCommandService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ChangeProductionOrderStatusCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateProductionOrderCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateOperationCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.MasterFormulaRepository;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductionOrderRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command service of production orders: the code is unique and the master formula must be approved.
 */
@Service
public class ProductionOrderCommandServiceImpl implements ProductionOrderCommandService {
    private final ProductionOrderRepository productionOrderRepository;
    private final MasterFormulaRepository masterFormulaRepository;

    public ProductionOrderCommandServiceImpl(ProductionOrderRepository productionOrderRepository,
                                             MasterFormulaRepository masterFormulaRepository) {
        this.productionOrderRepository = productionOrderRepository;
        this.masterFormulaRepository = masterFormulaRepository;
    }

    @Override
    @Transactional
    public Result<ProductionOrder, ApplicationError> handle(CreateProductionOrderCommand command) {
        if (productionOrderRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("ProductionOrder", "manufacturing.error.order.code.duplicate"));
        }
        var formula = masterFormulaRepository.findById(command.masterFormulaId());
        if (formula.isEmpty()) {
            return Result.failure(ApplicationError.notFound("MasterFormula", command.masterFormulaId().toString()));
        }
        if (!formula.get().isApproved()) {
            return Result.failure(ApplicationError.businessRuleViolation("formula-not-approved",
                    "manufacturing.error.order.formula.notApproved"));
        }
        return Result.success(productionOrderRepository.save(new ProductionOrder(command, formula.get().getProductCode())));
    }

    @Override
    @Transactional
    public Result<ProductionOrder, ApplicationError> handle(ChangeProductionOrderStatusCommand command) {
        var order = productionOrderRepository.findById(command.productionOrderId());
        if (order.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ProductionOrder", command.productionOrderId().toString()));
        }
        try {
            order.get().changeStatus(command.status());
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("order-status", e.getMessage()));
        }
        return Result.success(productionOrderRepository.save(order.get()));
    }

    @Override
    @Transactional
    public Result<ProductionOrder, ApplicationError> handle(UpdateOperationCommand command) {
        var order = productionOrderRepository.findById(command.productionOrderId());
        if (order.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ProductionOrder", command.productionOrderId().toString()));
        }
        if (!order.get().updateOperation(command)) {
            return Result.failure(ApplicationError.notFound("Operation", command.sequence().toString()));
        }
        return Result.success(productionOrderRepository.save(order.get()));
    }
}
