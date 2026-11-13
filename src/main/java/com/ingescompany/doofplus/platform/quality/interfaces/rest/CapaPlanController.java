package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaPlanCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaPlanQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaPlansQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaPlanByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaPlanResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaPlanResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CapaPlanResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateCapaPlanCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/capa-plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CAPA Plans", description = "Corrective and preventive action plan management endpoints")
public class CapaPlanController {

    private final CapaPlanCommandService commandService;
    private final CapaPlanQueryService queryService;

    public CapaPlanController(
            CapaPlanCommandService commandService,
            CapaPlanQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a CAPA plan")
    public ResponseEntity<?> createCapaPlan(
            @RequestBody CreateCapaPlanResource resource) {

        var command = CreateCapaPlanCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CapaPlanResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{capaPlanId}")
    @Operation(summary = "Get CAPA plan by ID")
    public ResponseEntity<CapaPlanResource> getCapaPlanById(
            @PathVariable Long capaPlanId) {

        var planOptional = queryService.handle(
                new GetCapaPlanByIdQuery(capaPlanId));

        return planOptional
                .map(plan -> ResponseEntity.ok(
                        CapaPlanResourceFromEntityAssembler.toResourceFromEntity(plan)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all CAPA plans")
    public ResponseEntity<List<CapaPlanResource>> getAllCapaPlans() {

        List<CapaPlanResource> resources = queryService.handle(
                        new GetAllCapaPlansQuery())
                .stream()
                .map(CapaPlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}