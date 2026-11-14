package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditQueryServiceImpl implements AuditQueryService {

    private final IAuditRepository auditRepository;

    public AuditQueryServiceImpl(IAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Optional<Audit> handle(GetAuditByIdQuery query) {
        return auditRepository.findById(query.auditId());
    }

    @Override
    public List<Audit> handle(GetAllAuditsQuery query) {
        return auditRepository.findAll();
    }
}