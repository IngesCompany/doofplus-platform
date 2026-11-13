package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.BatchReleaseQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllBatchReleasesQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetBatchReleaseByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetBatchReleaseByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IBatchReleaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchReleaseQueryServiceImpl implements BatchReleaseQueryService {

    private final IBatchReleaseRepository batchReleaseRepository;

    public BatchReleaseQueryServiceImpl(IBatchReleaseRepository batchReleaseRepository) {
        this.batchReleaseRepository = batchReleaseRepository;
    }

    @Override
    public Optional<BatchRelease> handle(GetBatchReleaseByIdQuery query) {
        return batchReleaseRepository.findById(query.releaseId());
    }

    @Override
    public Optional<BatchRelease> handle(GetBatchReleaseByBatchIdQuery query) {
        return batchReleaseRepository.findByBatchId(query.batchId());
    }

    @Override
    public List<BatchRelease> handle(GetAllBatchReleasesQuery query) {
        return batchReleaseRepository.findAll();
    }
}