package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

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
        return analyticalResultRepository.findByBatchId(query.batchId());
    }
}