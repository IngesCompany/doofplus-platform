package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditEventCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditEventQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditEventsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditEventByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditEventResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditEventResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AuditEventResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAuditEventCommandFromResourceAssembler;
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
 * REST controller of the audit events, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/audit-events", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Audit events", description = "Audit events of Quality and Compliance")
public class AuditEventsController {

    private final AuditEventCommandService commandService;
    private final AuditEventQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public AuditEventsController(AuditEventCommandService commandService, AuditEventQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a audit event.
     *
     * @param resource the data of the audit event
     * @return 201 with the audit event, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a audit event")
    public ResponseEntity<?> createAuditEvent(@RequestBody CreateAuditEventResource resource) {
        var command = CreateAuditEventCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                AuditEventResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the audit events, optionally filtered by record.
     *
     * @param record the record, or none for all
     *
     * @return 200 with the audit events
     */
    @GetMapping
    @Operation(summary = "Get the audit events")
    public ResponseEntity<List<AuditEventResource>> getAuditEvents(@RequestParam(required = false) String record) {
        var resources = queryService.handle(new GetAllAuditEventsQuery(record)).stream()
                .map(AuditEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a audit event by its identifier.
     *
     * @param auditEventId the identifier
     * @return 200 with the audit event, or 404
     */
    @GetMapping("/{auditEventId}")
    @Operation(summary = "Get a audit event by its identifier")
    public ResponseEntity<?> getAuditEventById(@PathVariable Long auditEventId) {
        return queryService.handle(new GetAuditEventByIdQuery(auditEventId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(AuditEventResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("AuditEvent", String.valueOf(auditEventId))));
    }
}
