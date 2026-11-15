package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationTrendQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationTrendsQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationTrendResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the monthly trend of deviations by root cause.
 */
@RestController
@RequestMapping(value = "/api/v1/deviation-trends", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Deviation trends", description = "Monthly trend of deviations by root cause")
public class DeviationTrendsController {

    private final DeviationTrendQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param queryService the query service of the trend
     */
    public DeviationTrendsController(DeviationTrendQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Gets the deviation trend, oldest month first.
     *
     * @return 200 with the trend
     */
    @GetMapping
    @Operation(summary = "Get the deviation trend")
    public ResponseEntity<List<DeviationTrendResource>> getDeviationTrends() {
        var trends = queryService.handle(new GetDeviationTrendsQuery());
        return ResponseEntity.ok(IntStream.range(0, trends.size())
                .mapToObj(i -> new DeviationTrendResource(i + 1, trends.get(i).rootCause(), trends.get(i).month(),
                        trends.get(i).count()))
                .toList());
    }
}
