package com.ingescompany.doofplus.platform.monitoring.application.internal.queryservices;

import com.ingescompany.doofplus.platform.monitoring.application.queryservices.ReadingQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsByBatchCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsBySensorCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetReadingByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.ReadingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ReadingQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class ReadingQueryServiceImpl implements ReadingQueryService {

    private final ReadingRepository readingRepository;

    public ReadingQueryServiceImpl(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
    }

    @Override
    public List<Reading> handle(GetAllReadingsQuery query) {
        return readingRepository.findAll();
    }

    @Override
    public Optional<Reading> handle(GetReadingByIdQuery query) {
        return readingRepository.findById(query.id());
    }

    @Override
    public List<Reading> handle(GetAllReadingsBySensorCodeQuery query) {
        return readingRepository.findAllBySensorCode(query.sensorCode());
    }

    @Override
    public List<Reading> handle(GetAllReadingsByBatchCodeQuery query) {
        return readingRepository.findAllByBatchCode(query.batchCode());
    }
}
