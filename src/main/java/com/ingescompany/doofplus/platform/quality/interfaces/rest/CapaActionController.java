package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaActionCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaActionQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaActionsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionsByCapaPlanIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaActionResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaActionResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CapaActionResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateCapaActionCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/capa-actions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CAPA Actions", description = "Corrective and preventive action management endpoints")
public class CapaActionController {

    private final CapaActionCommandService commandService;
    private final CapaActionQueryService queryService;

    public CapaActionController(
            CapaActionCommandService commandService,
            CapaActionQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a CAPA action")
    public ResponseEntity<?> createCapaAction(
            @RequestBody CreateCapaActionResource resource) {

        var command = CreateCapaActionCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CapaActionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{actionId}")
    @Operation(summary = "Get CAPA action by ID")
    public ResponseEntity<CapaActionResource> getCapaActionById(
            @PathVariable Long actionId) {

        var actionOptional = queryService.handle(
                new GetCapaActionByIdQuery(actionId));

        return actionOptional
                .map(action -> ResponseEntity.ok(
                        CapaActionResourceFromEntityAssembler.toResourceFromEntity(action)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-plan/{capaPlanId}")
    @Operation(summary = "Get CAPA actions by plan ID")
    public ResponseEntity<List<CapaActionResource>> getCapaActionsByCapaPlanId(
            @PathVariable Long capaPlanId) {

        List<CapaActionResource> resources = queryService.handle(
                        new GetCapaActionsByCapaPlanIdQuery(capaPlanId))
                .stream()
                .map(CapaActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping
    @Operation(summary = "Get all CAPA actions")
    public ResponseEntity<List<CapaActionResource>> getAllCapaActions() {

        List<CapaActionResource> resources = queryService.handle(
                        new GetAllCapaActionsQuery())
                .stream()
                .map(CapaActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}