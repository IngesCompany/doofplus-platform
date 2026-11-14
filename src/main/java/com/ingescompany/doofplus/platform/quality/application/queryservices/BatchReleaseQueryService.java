package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllBatchReleasesQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetBatchReleaseByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetBatchReleaseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface BatchReleaseQueryService {

    Optional<BatchRelease> handle(GetBatchReleaseByIdQuery query);

    Optional<BatchRelease> handle(GetBatchReleaseByBatchIdQuery query);

    List<BatchRelease> handle(GetAllBatchReleasesQuery query);
}