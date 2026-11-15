package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationTrendQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationTrendsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationTrend;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Builds the deviation trend from the deviations. Deviations without report date or root cause are not counted.
 */
@Service
public class DeviationTrendQueryServiceImpl implements DeviationTrendQueryService {

    private static final DateTimeFormatter MONTH = DateTimeFormatter.ofPattern("yyyy-MM");

    private final IDeviationRepository deviationRepository;

    /**
     * Creates the service.
     *
     * @param deviationRepository the repository of the deviations
     */
    public DeviationTrendQueryServiceImpl(IDeviationRepository deviationRepository) {
        this.deviationRepository = deviationRepository;
    }

    @Override
    public List<DeviationTrend> handle(GetDeviationTrendsQuery query) {
        Map<String, Map<String, Long>> byMonth = deviationRepository.findAll().stream()
                .filter(deviation -> deviation.getReportedAt() != null
                        && deviation.getRootCause() != null && !deviation.getRootCause().isBlank())
                .collect(Collectors.groupingBy(deviation -> deviation.getReportedAt().format(MONTH), TreeMap::new,
                        Collectors.groupingBy(Deviation::getRootCause, TreeMap::new, Collectors.counting())));
        return byMonth.entrySet().stream()
                .flatMap(month -> month.getValue().entrySet().stream()
                        .map(cause -> new DeviationTrend(month.getKey(), cause.getKey(), cause.getValue().intValue())))
                .sorted(Comparator.comparing(DeviationTrend::month).thenComparing(DeviationTrend::rootCause))
                .toList();
    }
}
