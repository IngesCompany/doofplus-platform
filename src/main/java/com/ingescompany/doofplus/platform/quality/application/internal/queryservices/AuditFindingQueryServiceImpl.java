package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditFindingQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditFindingsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditFindingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the audit findings.
 */
@Service
public class AuditFindingQueryServiceImpl implements AuditFindingQueryService {

    private final IAuditFindingRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the audit findings
     */
    public AuditFindingQueryServiceImpl(IAuditFindingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AuditFinding> handle(GetAllAuditFindingsQuery query) {
        return query.auditCode() == null || query.auditCode().isBlank()
                ? repository.findAll()
                : repository.findByAuditCode(query.auditCode().trim());
    }

    @Override
    public Optional<AuditFinding> handle(GetAuditFindingByIdQuery query) {
        return repository.findById(query.auditFindingId());
    }
}
