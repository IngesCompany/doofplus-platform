package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultByIdQuery;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AnalyticalResultQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAnalyticalResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticalResultQueryServiceImpl implements AnalyticalResultQueryService {

    private final IAnalyticalResultRepository analyticalResultRepository;

    public AnalyticalResultQueryServiceImpl(IAnalyticalResultRepository analyticalResultRepository) {
        this.analyticalResultRepository = analyticalResultRepository;
    }

    @Override
    public List<AnalyticalResult> handle(GetAnalyticalResultsByBatchIdQuery query) {
        if (query.batchId() == null) return analyticalResultRepository.findAll();
        return analyticalResultRepository.findByBatchId(query.batchId());
    }

    @Override
    public Optional<AnalyticalResult> handle(GetAnalyticalResultByIdQuery query) {
        return analyticalResultRepository.findById(query.id());
    }
}
