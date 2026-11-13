package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.EvidenceQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByDeviationIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvidenceQueryServiceImpl implements EvidenceQueryService {

    private final IEvidenceRepository evidenceRepository;

    public EvidenceQueryServiceImpl(IEvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public List<Evidence> handle(GetAllEvidenceQuery query) {
        return evidenceRepository.findAll();
    }

    @Override
    public List<Evidence> handle(GetEvidenceByDeviationIdQuery query) {
        return evidenceRepository.findByDeviationId(query.deviationId());
    }
}