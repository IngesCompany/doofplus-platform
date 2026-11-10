package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AnalyticalResultCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AnalyticalResultQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AnalyticalResultResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAnalyticalResultResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AnalyticalResultResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAnalyticalResultCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/analytical-results", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Analytical Results", description = "Analytical result management endpoints")
public class AnalyticalResultController {

    private final AnalyticalResultCommandService commandService;
    private final AnalyticalResultQueryService queryService;

    public AnalyticalResultController(
            AnalyticalResultCommandService commandService,
            AnalyticalResultQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create an analytical result")
    public ResponseEntity<?> createAnalyticalResult(
            @RequestBody CreateAnalyticalResultResource resource) {

        var command = CreateAnalyticalResultCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AnalyticalResultResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get analytical results by batch ID")
    public ResponseEntity<List<AnalyticalResultResource>> getByBatchId(
            @RequestParam Long batchId) {

        var results = queryService.handle(
                new GetAnalyticalResultsByBatchIdQuery(batchId)
        );

        List<AnalyticalResultResource> resources = results.stream()
                .map(AnalyticalResultResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}