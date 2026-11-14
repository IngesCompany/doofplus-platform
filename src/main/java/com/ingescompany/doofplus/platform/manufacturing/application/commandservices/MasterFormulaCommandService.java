package com.ingescompany.doofplus.platform.manufacturing.application.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ApproveMasterFormulaCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateMasterFormulaCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command operations of master formulas.
 */
public interface MasterFormulaCommandService {
    Result<MasterFormula, ApplicationError> handle(CreateMasterFormulaCommand command);
    Result<MasterFormula, ApplicationError> handle(ApproveMasterFormulaCommand command);
}
