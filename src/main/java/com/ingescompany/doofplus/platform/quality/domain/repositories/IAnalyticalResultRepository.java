package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;

import java.util.List;
import java.util.Optional;

public interface IAnalyticalResultRepository {

    AnalyticalResult save(AnalyticalResult analyticalResult);

    Optional<AnalyticalResult> findById(Long id);

    List<AnalyticalResult> findAll();

    List<AnalyticalResult> findByBatchId(Long batchId);
}