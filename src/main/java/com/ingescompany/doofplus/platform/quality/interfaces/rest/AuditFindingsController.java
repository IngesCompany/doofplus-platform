package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditFindingCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditFindingQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditFindingsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditFindingByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditFindingResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditFindingResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AuditFindingResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAuditFindingCommandFromResourceAssembler;
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
 * REST controller of the audit findings, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/audit-findings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Audit findings", description = "Audit findings of Quality and Compliance")
public class AuditFindingsController {

    private final AuditFindingCommandService commandService;
    private final AuditFindingQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public AuditFindingsController(AuditFindingCommandService commandService, AuditFindingQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a audit finding.
     *
     * @param resource the data of the audit finding
     * @return 201 with the audit finding, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a audit finding")
    public ResponseEntity<?> createAuditFinding(@RequestBody CreateAuditFindingResource resource) {
        var command = CreateAuditFindingCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                AuditFindingResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the audit findings, optionally filtered by audit code.
     *
     * @param auditCode the audit code, or none for all
     *
     * @return 200 with the audit findings
     */
    @GetMapping
    @Operation(summary = "Get the audit findings")
    public ResponseEntity<List<AuditFindingResource>> getAuditFindings(@RequestParam(required = false) String auditCode) {
        var resources = queryService.handle(new GetAllAuditFindingsQuery(auditCode)).stream()
                .map(AuditFindingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a audit finding by its identifier.
     *
     * @param auditFindingId the identifier
     * @return 200 with the audit finding, or 404
     */
    @GetMapping("/{auditFindingId}")
    @Operation(summary = "Get a audit finding by its identifier")
    public ResponseEntity<?> getAuditFindingById(@PathVariable Long auditFindingId) {
        return queryService.handle(new GetAuditFindingByIdQuery(auditFindingId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(AuditFindingResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("AuditFinding", String.valueOf(auditFindingId))));
    }
}
