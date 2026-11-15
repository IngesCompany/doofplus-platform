package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AnalyticalResultCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.AnalyticalResultQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAnalyticalResultsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AnalyticalResultResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAnalyticalResultResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateAnalyticalResultResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.AnalyticalResultResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateAnalyticalResultCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.UpdateAnalyticalResultCommandFromResourceAssembler;
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
 * REST controller of the analytical results, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/analytical-results", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Analytical results", description = "Analytical results of Quality and Compliance")
public class AnalyticalResultsController {

    private final AnalyticalResultCommandService commandService;
    private final AnalyticalResultQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public AnalyticalResultsController(AnalyticalResultCommandService commandService, AnalyticalResultQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a analytical result.
     *
     * @param resource the data of the analytical result
     * @return 201 with the analytical result, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a analytical result")
    public ResponseEntity<?> createAnalyticalResult(@RequestBody CreateAnalyticalResultResource resource) {
        var command = CreateAnalyticalResultCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                AnalyticalResultResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the analytical results, optionally filtered by batch code.
     *
     * @param batchCode the batch code, or none for all
     *
     * @return 200 with the analytical results
     */
    @GetMapping
    @Operation(summary = "Get the analytical results")
    public ResponseEntity<List<AnalyticalResultResource>> getAnalyticalResults(@RequestParam(required = false) String batchCode) {
        var resources = queryService.handle(new GetAllAnalyticalResultsQuery(batchCode)).stream()
                .map(AnalyticalResultResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a analytical result by its identifier.
     *
     * @param analyticalResultId the identifier
     * @return 200 with the analytical result, or 404
     */
    @GetMapping("/{analyticalResultId}")
    @Operation(summary = "Get a analytical result by its identifier")
    public ResponseEntity<?> getAnalyticalResultById(@PathVariable Long analyticalResultId) {
        return queryService.handle(new GetAnalyticalResultByIdQuery(analyticalResultId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(AnalyticalResultResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("AnalyticalResult", String.valueOf(analyticalResultId))));
    }

    /**
     * Replaces all the data of a analytical result.
     *
     * @param analyticalResultId the identifier
     * @param resource the new data
     * @return 200 with the analytical result, 400, 404
     */
    @PutMapping(value = "/{analyticalResultId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a analytical result")
    public ResponseEntity<?> updateAnalyticalResult(@PathVariable Long analyticalResultId, @RequestBody UpdateAnalyticalResultResource resource) {
        var command = UpdateAnalyticalResultCommandFromResourceAssembler.toCommandFromResource(analyticalResultId, resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                AnalyticalResultResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
