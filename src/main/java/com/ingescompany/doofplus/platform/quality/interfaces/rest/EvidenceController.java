package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.EvidenceCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.EvidenceQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByDeviationIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateEvidenceResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.EvidenceResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateEvidenceCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/evidence", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Evidence", description = "Quality evidence management endpoints")
public class EvidenceController {

    private final EvidenceCommandService commandService;
    private final EvidenceQueryService queryService;

    public EvidenceController(
            EvidenceCommandService commandService,
            EvidenceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create evidence")
    public ResponseEntity<?> createEvidence(
            @RequestBody CreateEvidenceResource resource) {

        var command = CreateEvidenceCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EvidenceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/by-deviation/{deviationId}")
    @Operation(summary = "Get evidence by deviation ID")
    public ResponseEntity<List<EvidenceResource>> getEvidenceByDeviationId(
            @PathVariable Long deviationId) {

        List<EvidenceResource> resources = queryService.handle(
                        new GetEvidenceByDeviationIdQuery(deviationId))
                .stream()
                .map(EvidenceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping
    @Operation(summary = "Get all evidence")
    public ResponseEntity<List<EvidenceResource>> getAllEvidence() {

        List<EvidenceResource> resources = queryService.handle(
                        new GetAllEvidenceQuery())
                .stream()
                .map(EvidenceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}