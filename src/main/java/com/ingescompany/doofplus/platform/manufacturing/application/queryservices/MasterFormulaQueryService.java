package com.ingescompany.doofplus.platform.manufacturing.application.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllMasterFormulasQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetMasterFormulaByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query operations of MasterFormula.
 */
public interface MasterFormulaQueryService {
    List<MasterFormula> handle(GetAllMasterFormulasQuery query);
    Optional<MasterFormula> handle(GetMasterFormulaByIdQuery query);
}
