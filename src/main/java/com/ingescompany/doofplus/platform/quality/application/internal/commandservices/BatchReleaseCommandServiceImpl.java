package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.BatchReleaseCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.ApproveBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.RejectBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.RequestBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IBatchReleaseRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class BatchReleaseCommandServiceImpl implements BatchReleaseCommandService {

    private final IBatchReleaseRepository batchReleaseRepository;

    public BatchReleaseCommandServiceImpl(IBatchReleaseRepository batchReleaseRepository) {
        this.batchReleaseRepository = batchReleaseRepository;
    }

    @Override
    public Result<BatchRelease, ApplicationError> handle(RequestBatchReleaseCommand command) {
        if (batchReleaseRepository.existsByBatchId(command.batchId())) {
            return Result.failure(
                    ApplicationError.conflict("BatchRelease", "A release request already exists for this batch")
            );
        }

        try {
            BatchRelease batchRelease = new BatchRelease(
                    command.batchId(),
                    command.batchCode(),
                    command.requestedBy().toString()
            );

            return Result.success(batchReleaseRepository.save(batchRelease));

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("BatchRelease", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("request-batch-release", e.getMessage())
            );
        }
    }

    @Override
    public Result<BatchRelease, ApplicationError> handle(ApproveBatchReleaseCommand command) {
        var releaseOptional = batchReleaseRepository.findById(command.releaseId());

        if (releaseOptional.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("BatchRelease", command.releaseId().toString())
            );
        }

        try {
            BatchRelease batchRelease = releaseOptional.get();

            batchRelease.approve(
                    command.decision(),
                    command.signatureReference(),
                    command.signedBy().toString()
            );

            return Result.success(batchReleaseRepository.save(batchRelease));

        } catch (IllegalStateException e) {
            return Result.failure(
                    ApplicationError.businessRuleViolation("approve-batch-release", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("approve-batch-release", e.getMessage())
            );
        }
    }

    @Override
    public Result<BatchRelease, ApplicationError> handle(RejectBatchReleaseCommand command) {
        var releaseOptional = batchReleaseRepository.findById(command.releaseId());

        if (releaseOptional.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("BatchRelease", command.releaseId().toString())
            );
        }

        try {
            BatchRelease batchRelease = releaseOptional.get();

            batchRelease.reject(
                    command.decision(),
                    command.signedBy().toString()
            );

            return Result.success(batchReleaseRepository.save(batchRelease));

        } catch (IllegalStateException e) {
            return Result.failure(
                    ApplicationError.businessRuleViolation("reject-batch-release", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("reject-batch-release", e.getMessage())
            );
        }
    }
}