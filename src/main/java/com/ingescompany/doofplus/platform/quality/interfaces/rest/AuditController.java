package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AuditResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAuditCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/audits", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Audits", description = "Quality audit management endpoints")
public class AuditController {

    private final AuditCommandService commandService;
    private final AuditQueryService queryService;

    public AuditController(
            AuditCommandService commandService,
            AuditQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a new audit")
    public ResponseEntity<?> createAudit(@RequestBody CreateAuditResource resource) {
        var command = CreateAuditCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuditResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{auditId}")
    @Operation(summary = "Get audit by ID")
    public ResponseEntity<AuditResource> getAuditById(@PathVariable Long auditId) {
        var auditOptional = queryService.handle(new GetAuditByIdQuery(auditId));

        return auditOptional
                .map(audit -> ResponseEntity.ok(
                        AuditResourceFromEntityAssembler.toResourceFromEntity(audit)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all audits")
    public ResponseEntity<List<AuditResource>> getAllAudits() {
        List<AuditResource> resources = queryService.handle(new GetAllAuditsQuery())
                .stream()
                .map(AuditResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}