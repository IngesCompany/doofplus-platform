package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationTrendQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DeviationTrendQueryServiceImpl implements DeviationTrendQueryService {

    private final IDeviationRepository deviationRepository;

    public DeviationTrendQueryServiceImpl(IDeviationRepository deviationRepository) {
        this.deviationRepository = deviationRepository;
    }

    @Override
    public Map<YearMonth, Long> getTrends() {
        Map<YearMonth, Long> trends = new TreeMap<>();

        for (Deviation deviation : deviationRepository.findAll()) {
            YearMonth month = YearMonth.from(deviation.getDetectedAt());
            trends.merge(month, 1L, Long::sum);
        }

        return trends;
    }
}