package com.ingescompany.doofplus.platform.manufacturing.application.queryservices;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllRawMaterialLotsQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetRawMaterialLotByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query operations of RawMaterialLot.
 */
public interface RawMaterialLotQueryService {
    List<RawMaterialLot> handle(GetAllRawMaterialLotsQuery query);
    Optional<RawMaterialLot> handle(GetRawMaterialLotByIdQuery query);
}
