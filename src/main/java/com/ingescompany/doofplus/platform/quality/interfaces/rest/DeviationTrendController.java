package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationTrendQueryService;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationTrendResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.DeviationTrendResourceFromDataAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/deviation-trends", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Deviation Trends", description = "Quality deviation trend endpoints")
public class DeviationTrendController {

    private final DeviationTrendQueryService queryService;

    public DeviationTrendController(DeviationTrendQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get deviation trends")
    public ResponseEntity<List<DeviationTrendResource>> getDeviationTrends() {

        var trends = queryService.getTrends();

        List<DeviationTrendResource> resources =
                DeviationTrendResourceFromDataAssembler.toResourcesFromData(trends);

        return ResponseEntity.ok(resources);
    }
}