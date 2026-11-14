package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AuditQueryService {
    Optional<Audit> handle(GetAuditByIdQuery query);

    List<Audit> handle(GetAllAuditsQuery query);
}