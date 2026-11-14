package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllDeviationsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationsByBatchIdQuery;

import java.util.List;
import java.util.Optional;

public interface DeviationQueryService {

    Optional<Deviation> handle(GetDeviationByIdQuery query);

    List<Deviation> handle(GetAllDeviationsQuery query);

    List<Deviation> handle(GetDeviationsByBatchIdQuery query);
}