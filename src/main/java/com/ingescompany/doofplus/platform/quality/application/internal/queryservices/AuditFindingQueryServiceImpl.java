package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditFindingQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingsByAuditIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditFindingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditFindingQueryServiceImpl implements AuditFindingQueryService {

    private final IAuditFindingRepository auditFindingRepository;

    public AuditFindingQueryServiceImpl(IAuditFindingRepository auditFindingRepository) {
        this.auditFindingRepository = auditFindingRepository;
    }

    @Override
    public List<AuditFinding> handle(GetAuditFindingsByAuditIdQuery query) {
        return auditFindingRepository.findByAuditId(query.auditId());
    }
}