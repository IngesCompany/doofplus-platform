package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllDeviationsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviationQueryServiceImpl implements DeviationQueryService {

    private final IDeviationRepository deviationRepository;

    public DeviationQueryServiceImpl(IDeviationRepository deviationRepository) {
        this.deviationRepository = deviationRepository;
    }

    @Override
    public Optional<Deviation> handle(GetDeviationByIdQuery query) {
        return deviationRepository.findById(query.deviationId());
    }

    @Override
    public List<Deviation> handle(GetAllDeviationsQuery query) {
        return deviationRepository.findAll();
    }

    @Override
    public List<Deviation> handle(GetDeviationsByBatchIdQuery query) {
        return deviationRepository.findByBatchId(query.batchId());
    }
}