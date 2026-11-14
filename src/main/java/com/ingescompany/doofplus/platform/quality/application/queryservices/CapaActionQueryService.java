package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaActionsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionsByCapaPlanIdQuery;

import java.util.List;
import java.util.Optional;

public interface CapaActionQueryService {

    Optional<CapaAction> handle(GetCapaActionByIdQuery query);

    List<CapaAction> handle(GetAllCapaActionsQuery query);

    List<CapaAction> handle(GetCapaActionsByCapaPlanIdQuery query);
}