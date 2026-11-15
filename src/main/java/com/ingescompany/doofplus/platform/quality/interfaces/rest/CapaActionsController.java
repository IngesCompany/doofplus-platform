package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaActionCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaActionQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaActionsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaActionResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaActionResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateCapaActionResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CapaActionResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateCapaActionCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.UpdateCapaActionCommandFromResourceAssembler;
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
 * REST controller of the capa actions, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/capa-actions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Capa actions", description = "Capa actions of Quality and Compliance")
public class CapaActionsController {

    private final CapaActionCommandService commandService;
    private final CapaActionQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public CapaActionsController(CapaActionCommandService commandService, CapaActionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a capa action.
     *
     * @param resource the data of the capa action
     * @return 201 with the capa action, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a capa action")
    public ResponseEntity<?> createCapaAction(@RequestBody CreateCapaActionResource resource) {
        var command = CreateCapaActionCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                CapaActionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the capa actions, optionally filtered by capa code.
     *
     * @param capaCode the capa code, or none for all
     *
     * @return 200 with the capa actions
     */
    @GetMapping
    @Operation(summary = "Get the capa actions")
    public ResponseEntity<List<CapaActionResource>> getCapaActions(@RequestParam(required = false) String capaCode) {
        var resources = queryService.handle(new GetAllCapaActionsQuery(capaCode)).stream()
                .map(CapaActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a capa action by its identifier.
     *
     * @param capaActionId the identifier
     * @return 200 with the capa action, or 404
     */
    @GetMapping("/{capaActionId}")
    @Operation(summary = "Get a capa action by its identifier")
    public ResponseEntity<?> getCapaActionById(@PathVariable Long capaActionId) {
        return queryService.handle(new GetCapaActionByIdQuery(capaActionId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(CapaActionResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("CapaAction", String.valueOf(capaActionId))));
    }

    /**
     * Replaces all the data of a capa action.
     *
     * @param capaActionId the identifier
     * @param resource the new data
     * @return 200 with the capa action, 400, 404
     */
    @PutMapping(value = "/{capaActionId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a capa action")
    public ResponseEntity<?> updateCapaAction(@PathVariable Long capaActionId, @RequestBody UpdateCapaActionResource resource) {
        var command = UpdateCapaActionCommandFromResourceAssembler.toCommandFromResource(capaActionId, resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                CapaActionResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
