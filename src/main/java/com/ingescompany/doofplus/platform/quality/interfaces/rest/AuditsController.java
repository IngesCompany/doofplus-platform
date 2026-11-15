package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AuditQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAuditsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAuditByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AuditResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAuditCommandFromResourceAssembler;
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
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the audits, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/audits", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Audits", description = "Audits of Quality and Compliance")
public class AuditsController {

    private final AuditCommandService commandService;
    private final AuditQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public AuditsController(AuditCommandService commandService, AuditQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a audit.
     *
     * @param resource the data of the audit
     * @return 201 with the audit, 400 when the data is not valid or 409 when the code is taken
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a audit")
    public ResponseEntity<?> createAudit(@RequestBody CreateAuditResource resource) {
        var command = CreateAuditCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                AuditResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the audits.
     *
     * @return 200 with the audits
     */
    @GetMapping
    @Operation(summary = "Get the audits")
    public ResponseEntity<List<AuditResource>> getAudits() {
        var resources = queryService.handle(new GetAllAuditsQuery()).stream()
                .map(AuditResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a audit by its identifier.
     *
     * @param auditId the identifier
     * @return 200 with the audit, or 404
     */
    @GetMapping("/{auditId}")
    @Operation(summary = "Get a audit by its identifier")
    public ResponseEntity<?> getAuditById(@PathVariable Long auditId) {
        return queryService.handle(new GetAuditByIdQuery(auditId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(AuditResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("Audit", String.valueOf(auditId))));
    }
}
