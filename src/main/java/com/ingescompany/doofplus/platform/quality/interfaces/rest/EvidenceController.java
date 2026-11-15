package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.EvidenceCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.EvidenceQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.DeleteEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllEvidenceQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetEvidenceByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateEvidenceResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.EvidenceResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateEvidenceResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateEvidenceCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.EvidenceResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.UpdateEvidenceCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the evidence, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/evidence", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Evidence", description = "Evidence of Quality and Compliance")
public class EvidenceController {

    private final EvidenceCommandService commandService;
    private final EvidenceQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public EvidenceController(EvidenceCommandService commandService, EvidenceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a evidence.
     *
     * @param resource the data of the evidence
     * @return 201 with the evidence, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a evidence")
    public ResponseEntity<?> createEvidence(@RequestBody CreateEvidenceResource resource) {
        var command = CreateEvidenceCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                EvidenceResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the evidence, optionally filtered by record code.
     *
     * @param recordCode the record code, or none for all
     *
     * @return 200 with the evidence
     */
    @GetMapping
    @Operation(summary = "Get the evidence")
    public ResponseEntity<List<EvidenceResource>> getEvidence(@RequestParam(required = false) String recordCode) {
        var resources = queryService.handle(new GetAllEvidenceQuery(recordCode)).stream()
                .map(EvidenceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a evidence by its identifier.
     *
     * @param evidenceId the identifier
     * @return 200 with the evidence, or 404
     */
    @GetMapping("/{evidenceId}")
    @Operation(summary = "Get a evidence by its identifier")
    public ResponseEntity<?> getEvidenceById(@PathVariable Long evidenceId) {
        return queryService.handle(new GetEvidenceByIdQuery(evidenceId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(EvidenceResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("Evidence", String.valueOf(evidenceId))));
    }

    /**
     * Replaces all the data of a evidence.
     *
     * @param evidenceId the identifier
     * @param resource the new data
     * @return 200 with the evidence, 400, 404
     */
    @PutMapping(value = "/{evidenceId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a evidence")
    public ResponseEntity<?> updateEvidence(@PathVariable Long evidenceId, @RequestBody UpdateEvidenceResource resource) {
        var command = UpdateEvidenceCommandFromResourceAssembler.toCommandFromResource(evidenceId, resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                EvidenceResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Removes a evidence; reviewed evidence cannot be removed.
     *
     * @param evidenceId the identifier
     * @return 204, 404 or 409
     */
    @DeleteMapping("/{evidenceId}")
    @Operation(summary = "Delete a evidence")
    public ResponseEntity<?> deleteEvidence(@PathVariable Long evidenceId) {
        return switch (commandService.handle(new DeleteEvidenceCommand(evidenceId))) {
            case Result.Success<Long, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Long, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(failure.error());
        };
    }
}
