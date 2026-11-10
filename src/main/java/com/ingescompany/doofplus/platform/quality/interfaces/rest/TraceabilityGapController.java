package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TraceabilityGapCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.TraceabilityGapQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTraceabilityGapsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTraceabilityGapResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TraceabilityGapResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateTraceabilityGapCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.TraceabilityGapResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/traceability-gaps", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Traceability Gaps", description = "Traceability gap management endpoints")
public class TraceabilityGapController {

    private final TraceabilityGapCommandService commandService;
    private final TraceabilityGapQueryService queryService;

    public TraceabilityGapController(
            TraceabilityGapCommandService commandService,
            TraceabilityGapQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a traceability gap")
    public ResponseEntity<?> createGap(
            @RequestBody CreateTraceabilityGapResource resource) {

        var command =
                CreateTraceabilityGapCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                TraceabilityGapResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get all traceability gaps")
    public ResponseEntity<List<TraceabilityGapResource>> getAllGaps() {

        List<TraceabilityGapResource> gaps =
                queryService.handle(new GetAllTraceabilityGapsQuery())
                        .stream()
                        .map(TraceabilityGapResourceFromEntityAssembler::toResourceFromEntity)
                        .toList();

        return ResponseEntity.ok(gaps);
    }

    @GetMapping("/by-batch/{batchId}")
    @Operation(summary = "Get traceability gaps by batch ID")
    public ResponseEntity<List<TraceabilityGapResource>> getGapsByBatchId(
            @PathVariable Long batchId) {

        List<TraceabilityGapResource> gaps =
                queryService.handle(new GetTraceabilityGapsByBatchIdQuery(batchId))
                        .stream()
                        .map(TraceabilityGapResourceFromEntityAssembler::toResourceFromEntity)
                        .toList();

        return ResponseEntity.ok(gaps);
    }
}