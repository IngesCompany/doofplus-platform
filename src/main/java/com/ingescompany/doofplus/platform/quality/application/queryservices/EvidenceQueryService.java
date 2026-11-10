package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByDeviationIdQuery;

import java.util.List;

public interface EvidenceQueryService {

    List<Evidence> handle(GetAllEvidenceQuery query);

    List<Evidence> handle(GetEvidenceByDeviationIdQuery query);
}