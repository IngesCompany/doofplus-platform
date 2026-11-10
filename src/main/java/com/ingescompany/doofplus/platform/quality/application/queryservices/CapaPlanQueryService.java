package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaPlansQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaPlanByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CapaPlanQueryService {

    Optional<CapaPlan> handle(GetCapaPlanByIdQuery query);

    List<CapaPlan> handle(GetAllCapaPlansQuery query);
}