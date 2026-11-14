package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditFindingCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditFindingQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingsByAuditIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditFindingResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditFindingResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AuditFindingResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAuditFindingCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/audit-findings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Audit Findings", description = "Quality audit finding management endpoints")
public class AuditFindingController {

    private final AuditFindingCommandService commandService;
    private final AuditFindingQueryService queryService;

    public AuditFindingController(
            AuditFindingCommandService commandService,
            AuditFindingQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a new audit finding")
    public ResponseEntity<?> createAuditFinding(
            @RequestBody CreateAuditFindingResource resource) {

        var command = CreateAuditFindingCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuditFindingResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get audit findings by audit ID")
    public ResponseEntity<List<AuditFindingResource>> getAuditFindingsByAuditId(
            @RequestParam Long auditId) {

        var query = new GetAuditFindingsByAuditIdQuery(auditId);

        List<AuditFindingResource> resources = queryService.handle(query).stream()
                .map(AuditFindingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}