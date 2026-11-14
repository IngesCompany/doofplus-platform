package com.ingescompany.doofplus.platform.manufacturing.application.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.*;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command operations of raw material lots.
 */
public interface RawMaterialLotCommandService {
    Result<RawMaterialLot, ApplicationError> handle(ReceiveRawMaterialLotCommand command);
    Result<RawMaterialLot, ApplicationError> handle(InspectRawMaterialLotCommand command);
    Result<RawMaterialLot, ApplicationError> handle(ApproveRawMaterialLotCommand command);
    Result<RawMaterialLot, ApplicationError> handle(RejectRawMaterialLotCommand command);
    Result<RawMaterialLot, ApplicationError> handle(AllocateRawMaterialLotCommand command);
}
