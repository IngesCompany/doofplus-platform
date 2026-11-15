package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.DeviationCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllDeviationsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateDeviationResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateDeviationResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateDeviationCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.DeviationResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.UpdateDeviationCommandFromResourceAssembler;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the deviations, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/deviations", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Deviations", description = "Deviations of Quality and Compliance")
public class DeviationsController {

    private final DeviationCommandService commandService;
    private final DeviationQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public DeviationsController(DeviationCommandService commandService, DeviationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a deviation.
     *
     * @param resource the data of the deviation
     * @return 201 with the deviation, 400 when the data is not valid or 409 when the code is taken
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a deviation")
    public ResponseEntity<?> createDeviation(@RequestBody CreateDeviationResource resource) {
        var command = CreateDeviationCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                DeviationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the deviations, optionally filtered by batch code.
     *
     * @param batchCode the batch code, or none for all
     *
     * @return 200 with the deviations
     */
    @GetMapping
    @Operation(summary = "Get the deviations")
    public ResponseEntity<List<DeviationResource>> getDeviations(@RequestParam(required = false) String batchCode) {
        var resources = queryService.handle(new GetAllDeviationsQuery(batchCode)).stream()
                .map(DeviationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a deviation by its identifier.
     *
     * @param deviationId the identifier
     * @return 200 with the deviation, or 404
     */
    @GetMapping("/{deviationId}")
    @Operation(summary = "Get a deviation by its identifier")
    public ResponseEntity<?> getDeviationById(@PathVariable Long deviationId) {
        return queryService.handle(new GetDeviationByIdQuery(deviationId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(DeviationResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("Deviation", String.valueOf(deviationId))));
    }

    /**
     * Replaces all the data of a deviation.
     *
     * @param deviationId the identifier
     * @param resource the new data
     * @return 200 with the deviation, 400, 404 or 409
     */
    @PutMapping(value = "/{deviationId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a deviation")
    public ResponseEntity<?> updateDeviation(@PathVariable Long deviationId, @RequestBody UpdateDeviationResource resource) {
        var command = UpdateDeviationCommandFromResourceAssembler.toCommandFromResource(deviationId, resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                DeviationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
