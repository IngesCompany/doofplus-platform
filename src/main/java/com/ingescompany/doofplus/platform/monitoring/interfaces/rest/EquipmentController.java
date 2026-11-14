package com.ingescompany.doofplus.platform.monitoring.interfaces.rest;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.EquipmentCommandService;
import com.ingescompany.doofplus.platform.monitoring.application.queryservices.EquipmentQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.DeleteEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetAllEquipmentQuery;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.GetEquipmentByIdQuery;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateEquipmentResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.EquipmentResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.UpdateEquipmentResource;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.CreateEquipmentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform.UpdateEquipmentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes equipment resources and administration endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/equipment", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Equipment", description = "Equipment inventory and status management endpoints")
public class EquipmentController {

    private final EquipmentCommandService equipmentCommandService;
    private final EquipmentQueryService equipmentQueryService;

    public EquipmentController(EquipmentCommandService equipmentCommandService, EquipmentQueryService equipmentQueryService) {
        this.equipmentCommandService = equipmentCommandService;
        this.equipmentQueryService = equipmentQueryService;
    }

    /**
     * Retrieves the equipment inventory.
     *
     * @return List of {@link EquipmentResource}
     */
    @GetMapping
    @Operation(summary = "Get all equipment", description = "Retrieves all equipment items in the inventory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment inventory retrieved successfully")
    })
    public ResponseEntity<List<EquipmentResource>> getAllEquipment() {
        var query = new GetAllEquipmentQuery();
        var equipmentList = equipmentQueryService.handle(query);
        var resources = equipmentList.stream()
                .map(EquipmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves equipment by persistent identifier.
     *
     * @param id equipment persistent ID
     * @return {@link EquipmentResource} if found, 404 otherwise
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get equipment by ID", description = "Retrieves an equipment item by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment found",
                    content = @Content(schema = @Schema(implementation = EquipmentResource.class))),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    public ResponseEntity<?> getEquipmentById(
            @PathVariable @Parameter(description = "Equipment unique identifier", example = "1", required = true) Long id
    ) {
        var query = new GetEquipmentByIdQuery(id);
        var equipmentOpt = equipmentQueryService.handle(query);
        if (equipmentOpt.isEmpty()) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                    ApplicationError.notFound("Equipment", id.toString())
            );
        }
        var resource = EquipmentResourceFromEntityAssembler.toResourceFromEntity(equipmentOpt.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Registers a new equipment item.
     *
     * @param resource creation payload
     * @return created {@link EquipmentResource}
     */
    @PostMapping
    @Operation(summary = "Create equipment", description = "Registers a new equipment item with calibration details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipment registered successfully",
                    content = @Content(schema = @Schema(implementation = EquipmentResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error"),
            @ApiResponse(responseCode = "409", description = "Conflict - equipment code already exists")
    })
    public ResponseEntity<?> createEquipment(@RequestBody CreateEquipmentResource resource) {
        var command = CreateEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = equipmentCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EquipmentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Updates an existing piece of equipment.
     *
     * @param id equipment persistent ID
     * @param resource update payload
     * @return updated {@link EquipmentResource}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update equipment", description = "Updates calibration, maintenance, or details of an existing equipment item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment updated successfully",
                    content = @Content(schema = @Schema(implementation = EquipmentResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    public ResponseEntity<?> updateEquipment(
            @PathVariable @Parameter(description = "Equipment unique identifier", example = "1", required = true) Long id,
            @RequestBody UpdateEquipmentResource resource
    ) {
        var command = UpdateEquipmentCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = equipmentCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EquipmentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Removes a piece of equipment.
     *
     * @param id equipment persistent ID
     * @return 204 when removed, 404 when it does not exist, 409 when one of its sensors is monitoring a batch
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete equipment", description = "Removes an equipment item. It is rejected while one of its sensors is monitoring a production batch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipment removed successfully"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - one of its sensors is monitoring a production batch")
    })
    public ResponseEntity<?> deleteEquipment(
            @PathVariable @Parameter(description = "Equipment unique identifier", example = "1", required = true) Long id
    ) {
        var result = equipmentCommandService.handle(new DeleteEquipmentCommand(id));
        if (result instanceof Result.Failure<Equipment, ApplicationError> failure) {
            return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(failure.error());
        }
        return ResponseEntity.noContent().build();
    }
}
