package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaPlanCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaPlanQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaPlansQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaPlanByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaPlanResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaPlanResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateCapaPlanResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CapaPlanResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateCapaPlanCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.UpdateCapaPlanCommandFromResourceAssembler;
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
 * REST controller of the capa plans, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/capa-plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Capa plans", description = "Capa plans of Quality and Compliance")
public class CapaPlansController {

    private final CapaPlanCommandService commandService;
    private final CapaPlanQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public CapaPlansController(CapaPlanCommandService commandService, CapaPlanQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a capa plan.
     *
     * @param resource the data of the capa plan
     * @return 201 with the capa plan, 400 when the data is not valid or 409 when the code is taken
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a capa plan")
    public ResponseEntity<?> createCapaPlan(@RequestBody CreateCapaPlanResource resource) {
        var command = CreateCapaPlanCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                CapaPlanResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the capa plans, optionally filtered by source deviation.
     *
     * @param sourceDeviation the source deviation, or none for all
     *
     * @return 200 with the capa plans
     */
    @GetMapping
    @Operation(summary = "Get the capa plans")
    public ResponseEntity<List<CapaPlanResource>> getCapaPlans(@RequestParam(required = false) String sourceDeviation) {
        var resources = queryService.handle(new GetAllCapaPlansQuery(sourceDeviation)).stream()
                .map(CapaPlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a capa plan by its identifier.
     *
     * @param capaPlanId the identifier
     * @return 200 with the capa plan, or 404
     */
    @GetMapping("/{capaPlanId}")
    @Operation(summary = "Get a capa plan by its identifier")
    public ResponseEntity<?> getCapaPlanById(@PathVariable Long capaPlanId) {
        return queryService.handle(new GetCapaPlanByIdQuery(capaPlanId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(CapaPlanResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("CapaPlan", String.valueOf(capaPlanId))));
    }

    /**
     * Replaces all the data of a capa plan.
     *
     * @param capaPlanId the identifier
     * @param resource the new data
     * @return 200 with the capa plan, 400, 404 or 409
     */
    @PutMapping(value = "/{capaPlanId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a capa plan")
    public ResponseEntity<?> updateCapaPlan(@PathVariable Long capaPlanId, @RequestBody UpdateCapaPlanResource resource) {
        var command = UpdateCapaPlanCommandFromResourceAssembler.toCommandFromResource(capaPlanId, resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                CapaPlanResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
