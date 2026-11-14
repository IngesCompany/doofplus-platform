package com.ingescompany.doofplus.platform.manufacturing.application.internal.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductionBatchCommandService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CloseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.RegisterIncidentCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ReleaseBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ResumeBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.StartBatchCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateBatchProgressCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Incident;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductionBatchRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes production batch commands.
 */
@Service
public class ProductionBatchCommandServiceImpl implements ProductionBatchCommandService {
    private final ProductionBatchRepository productionBatchRepository;

    public ProductionBatchCommandServiceImpl(ProductionBatchRepository productionBatchRepository) {
        this.productionBatchRepository = productionBatchRepository;
    }

    @Override
    public Result<ProductionBatch, ApplicationError> handle(CreateBatchCommand command) {
        if(productionBatchRepository.existsByBatchNumber(command.batchNumber())) {
            return Result.failure(ApplicationError.conflict("ProductionBatch", "Batch number '%s' already exists".formatted(command.batchNumber().value())));
        }

        try {
            var productionBatch = new ProductionBatch(command);
            var savedBatch = productionBatchRepository.save(productionBatch);
            return Result.success(savedBatch);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-production-batch", e.getMessage()));
        }
    }

    @Override
    public Result<ProductionBatch, ApplicationError> handle(CloseBatchCommand command) {
        var batchOptional = productionBatchRepository.findById(command.batchId());
        if (batchOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ProductionBatch", command.batchId().toString()));
        }
        var batch = batchOptional.get();
        try {
            batch.close(command);
            var updatedBatch = productionBatchRepository.save(batch);
            return Result.success(updatedBatch);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.invalidState("ProductionBatch", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("close-production-batch", e.getMessage()));
        }
    }

    @Override
    public Result<Incident, ApplicationError> handle(RegisterIncidentCommand command) {
        var batchOptional = productionBatchRepository.findById(command.batchId());
        if (batchOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ProductionBatch", command.batchId().toString()));
        }
        var batch = batchOptional.get();
        try {
            batch.registerIncident(command);
            var savedBatch = productionBatchRepository.save(batch);
            return Result.success(savedBatch.getIncidents().getLast());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-incident", e.getMessage()));
        }
    }

    @Override
    public Result<ProductionBatch, ApplicationError> handle(UpdateBatchProgressCommand command) {
        var batchOptional = productionBatchRepository.findById(command.batchId());
        if (batchOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ProductionBatch", command.batchId().toString()));
        }
        var batch = batchOptional.get();
        batch.updateProgress(command);
        return Result.success(productionBatchRepository.save(batch));
    }

    @Override
    public Result<ProductionBatch, ApplicationError> handle(StartBatchCommand command) {
        return transition(command.batchId(), "start-batch", ProductionBatch::start);
    }

    @Override
    public Result<ProductionBatch, ApplicationError> handle(ResumeBatchCommand command) {
        return transition(command.batchId(), "resume-batch", ProductionBatch::resume);
    }

    @Override
    public Result<ProductionBatch, ApplicationError> handle(ReleaseBatchCommand command) {
        return transition(command.batchId(), "release-batch", batch -> batch.release(command));
    }

    /**
     * Loads a batch, applies a status change and saves it.
     *
     * @param batchId    identifier of the batch
     * @param rule       name of the business rule reported when the change is not allowed
     * @param transition aggregate method that changes the status
     * @return the updated batch, not found, or a conflict when the status does not allow the change
     */
    private Result<ProductionBatch, ApplicationError> transition(Long batchId, String rule,
                                                                 java.util.function.Consumer<ProductionBatch> transition) {
        var batchOptional = productionBatchRepository.findById(batchId);
        if (batchOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ProductionBatch", batchId.toString()));
        }
        var batch = batchOptional.get();
        try {
            transition.accept(batch);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.invalidState("ProductionBatch", e.getMessage()));
        }
        return Result.success(productionBatchRepository.save(batch));
    }
}
