package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditEventQueryService;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditEventResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AuditEventResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/audit-events", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Audit Events", description = "Quality audit event endpoints")
public class AuditEventController {

    private final AuditEventQueryService queryService;

    public AuditEventController(AuditEventQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get audit events")
    public ResponseEntity<List<AuditEventResource>> getAuditEvents(
            @RequestParam(required = false) String aggregateType,
            @RequestParam(required = false) Long aggregateId) {

        if ((aggregateType == null) != (aggregateId == null)) {
            return ResponseEntity.badRequest().build();
        }

        var events = aggregateType == null
                ? queryService.getAll()
                : queryService.getByAggregate(aggregateType, aggregateId);

        List<AuditEventResource> resources = events.stream()
                .map(AuditEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}