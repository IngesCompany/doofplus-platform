package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingsByAuditIdQuery;

import java.util.List;

public interface AuditFindingQueryService {
    List<AuditFinding> handle(GetAuditFindingsByAuditIdQuery query);
}