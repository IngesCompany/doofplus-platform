package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.DeviationCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllDeviationsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateDeviationResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateDeviationCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.DeviationResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/deviations", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Deviations", description = "Quality deviation management endpoints")
public class DeviationController {

    private final DeviationCommandService commandService;
    private final DeviationQueryService queryService;

    public DeviationController(
            DeviationCommandService commandService,
            DeviationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a deviation")
    public ResponseEntity<?> createDeviation(
            @RequestBody CreateDeviationResource resource) {

        var command = CreateDeviationCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DeviationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{deviationId}")
    @Operation(summary = "Get deviation by ID")
    public ResponseEntity<DeviationResource> getDeviationById(
            @PathVariable Long deviationId) {

        var deviationOptional = queryService.handle(
                new GetDeviationByIdQuery(deviationId));

        return deviationOptional
                .map(deviation -> ResponseEntity.ok(
                        DeviationResourceFromEntityAssembler.toResourceFromEntity(deviation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-batch/{batchId}")
    @Operation(summary = "Get deviations by batch ID")
    public ResponseEntity<List<DeviationResource>> getDeviationsByBatchId(
            @PathVariable Long batchId) {

        List<DeviationResource> resources = queryService.handle(
                        new GetDeviationsByBatchIdQuery(batchId))
                .stream()
                .map(DeviationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping
    @Operation(summary = "Get all deviations")
    public ResponseEntity<List<DeviationResource>> getAllDeviations() {

        List<DeviationResource> resources = queryService.handle(
                        new GetAllDeviationsQuery())
                .stream()
                .map(DeviationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}