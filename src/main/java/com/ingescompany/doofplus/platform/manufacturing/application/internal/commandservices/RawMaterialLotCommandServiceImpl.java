package com.ingescompany.doofplus.platform.manufacturing.application.internal.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.RawMaterialLotCommandService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.*;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.RawMaterialLotRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

/**
 * Command service of raw material lots: the code is unique and the status changes follow the receipt flow.
 */
@Service
public class RawMaterialLotCommandServiceImpl implements RawMaterialLotCommandService {
    private final RawMaterialLotRepository rawMaterialLotRepository;

    public RawMaterialLotCommandServiceImpl(RawMaterialLotRepository rawMaterialLotRepository) {
        this.rawMaterialLotRepository = rawMaterialLotRepository;
    }

    @Override
    @Transactional
    public Result<RawMaterialLot, ApplicationError> handle(ReceiveRawMaterialLotCommand command) {
        if (rawMaterialLotRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("RawMaterialLot", "manufacturing.error.lot.code.duplicate"));
        }
        return Result.success(rawMaterialLotRepository.save(new RawMaterialLot(command)));
    }

    @Override
    @Transactional
    public Result<RawMaterialLot, ApplicationError> handle(InspectRawMaterialLotCommand command) {
        return transition(command.rawMaterialLotId(), lot -> lot.inspect(command));
    }

    @Override
    @Transactional
    public Result<RawMaterialLot, ApplicationError> handle(ApproveRawMaterialLotCommand command) {
        return transition(command.rawMaterialLotId(), RawMaterialLot::approve);
    }

    @Override
    @Transactional
    public Result<RawMaterialLot, ApplicationError> handle(RejectRawMaterialLotCommand command) {
        return transition(command.rawMaterialLotId(), RawMaterialLot::reject);
    }

    @Override
    @Transactional
    public Result<RawMaterialLot, ApplicationError> handle(AllocateRawMaterialLotCommand command) {
        return transition(command.rawMaterialLotId(), lot -> lot.allocate(command));
    }

    private Result<RawMaterialLot, ApplicationError> transition(Long lotId, Consumer<RawMaterialLot> change) {
        var lot = rawMaterialLotRepository.findById(lotId);
        if (lot.isEmpty()) {
            return Result.failure(ApplicationError.notFound("RawMaterialLot", lotId.toString()));
        }
        try {
            change.accept(lot.get());
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("lot-status", e.getMessage()));
        }
        return Result.success(rawMaterialLotRepository.save(lot.get()));
    }
}
