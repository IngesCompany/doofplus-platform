package com.ingescompany.doofplus.platform.monitoring.application.queryservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsByBatchCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsBySensorCodeQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllReadingsQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetReadingByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Reading} queries.
 */
public interface ReadingQueryService {
    List<Reading> handle(GetAllReadingsQuery query);
    Optional<Reading> handle(GetReadingByIdQuery query);
    List<Reading> handle(GetAllReadingsBySensorCodeQuery query);
    List<Reading> handle(GetAllReadingsByBatchCodeQuery query);
}
