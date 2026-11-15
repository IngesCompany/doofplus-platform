package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TraceabilityGapCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.TraceabilityGapQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTraceabilityGapsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTraceabilityGapResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TraceabilityGapResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateTraceabilityGapCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.TraceabilityGapResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the traceability gaps, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/traceability-gaps", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Traceability gaps", description = "Traceability gaps of Quality and Compliance")
public class TraceabilityGapsController {

    private final TraceabilityGapCommandService commandService;
    private final TraceabilityGapQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public TraceabilityGapsController(TraceabilityGapCommandService commandService, TraceabilityGapQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a traceability gap.
     *
     * @param resource the data of the traceability gap
     * @return 201 with the traceability gap, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a traceability gap")
    public ResponseEntity<?> createTraceabilityGap(@RequestBody CreateTraceabilityGapResource resource) {
        var command = CreateTraceabilityGapCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                TraceabilityGapResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the traceability gaps, optionally filtered by batch code.
     *
     * @param batchCode the batch code, or none for all
     *
     * @return 200 with the traceability gaps
     */
    @GetMapping
    @Operation(summary = "Get the traceability gaps")
    public ResponseEntity<List<TraceabilityGapResource>> getTraceabilityGaps(@RequestParam(required = false) String batchCode) {
        var resources = queryService.handle(new GetAllTraceabilityGapsQuery(batchCode)).stream()
                .map(TraceabilityGapResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a traceability gap by its identifier.
     *
     * @param traceabilityGapId the identifier
     * @return 200 with the traceability gap, or 404
     */
    @GetMapping("/{traceabilityGapId}")
    @Operation(summary = "Get a traceability gap by its identifier")
    public ResponseEntity<?> getTraceabilityGapById(@PathVariable Long traceabilityGapId) {
        return queryService.handle(new GetTraceabilityGapByIdQuery(traceabilityGapId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(TraceabilityGapResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("TraceabilityGap", String.valueOf(traceabilityGapId))));
    }
}
